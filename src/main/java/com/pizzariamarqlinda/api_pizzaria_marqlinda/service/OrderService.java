package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.BusinessLogicException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.OrderMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.*;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.*;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.StatusOrderEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.OrderRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.validation.OrderStatusValidation;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.validation.UserProfileValidation;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final String OPERATION_NOT_PERMITTED = "Operação não permitida.";

    @Setter
    private JwtAuthenticationToken token;

    @Setter
    @Autowired
    private Clock clock;

    private final OrderMapper mapper = OrderMapper.INSTANCE;
    private final LoggedUserService loggedUserService;
    private final AddressService addressService;
    private final PaymentMethodService paymentMethodService;
    private final ConfigurationsService configuration;
    private final OrderRepository orderRepository;
    private final ItemProductService itemProductService;
    private final UserProfileValidation userProfileValidation;
    private final OrderStatusValidation orderStatusValidation;

    @Transactional
    public OrderResDto save(OrderReqDto orderReqDto){
        Order orderConverted = mapper.orderReqDtoToEntity(orderReqDto);
        User loggedUser = loggedUserService.loggedUser(token);
        this.verifyQuantityItemsInCart(loggedUser);
        this.checkOfficeHours();
        this.checkBusinessDay();
        Order objOrder = this.fillObjectOrder(orderReqDto, orderConverted, loggedUser);
        return mapper.entityToOrderResDto(orderRepository.save(objOrder));
    }

    private Order fillObjectOrder(OrderReqDto orderReqDto, Order orderConverted, User loggedUser){
        Order order = this.orderWithTotal(orderReqDto, orderConverted, loggedUser);
        order.setStatus(StatusOrderEnum.ORDER_AWAITING_SERVICE);
        order.setDateTimeOrder(LocalDateTime.now());
        order.setDeliveryForecast(order.getDateTimeOrder().plusMinutes(configuration.getDeliveryForecast()));
        List<ItemProduct> items = new ArrayList<>(loggedUser.getCart().getItems());
        for (ItemProduct itemProduct : items) {
            itemProduct.setCart(null);
            itemProduct.setOrder(order);
        }
        order.setItems(items);
        itemProductService.deleteAll(loggedUser.getCart().getItems());
        order.setUser(loggedUser);
        PaymentMethod paymentMethod = paymentMethodService.findById(orderReqDto.paymentMethod().id());
        order.setPaymentMethod(paymentMethod);
        Address address = addressService.findById(orderReqDto.address().id());
        order.setAddress(address);
        return order;
    }

    private Order orderWithTotal(OrderReqDto orderReqDto, Order order, User loggedUser){
        if(orderReqDto.delivery()){
            BigDecimal valueWithRateDelivery = loggedUser.getCart().getTotal().add(configuration.getRateDelivery());
            order.setTotal(valueWithRateDelivery);
            order.setRateDelivery(configuration.getRateDelivery());
        }else {
            order.setTotal(loggedUser.getCart().getTotal());
            order.setDelivery(false);
        }
        return order;
    }

    private void verifyQuantityItemsInCart(User loggedUser) {
        if(loggedUser.getCart().getItems().isEmpty())
            throw new BusinessLogicException("Não há itens de compra no carrinho.");
    }

    private void checkOfficeHours() {
        LocalTime officeStartTime = LocalTime.of(configuration.getOpeningHour(), configuration.getOpeningMinute());
        LocalTime endOfficeBusinessHours = LocalTime.of(configuration.getClosingHour(), configuration.getClosingMinute());
        if(LocalTime.now(clock).isBefore(officeStartTime) || LocalTime.now(clock).isAfter(endOfficeBusinessHours))
            throw new BusinessLogicException("Fora do horário de expediente. Atendemos das "+ configuration.getOpeningHour() + " as " + configuration.getClosingHour());
    }

    private void checkBusinessDay(){
        DayOfWeek dayOfWeek = LocalDate.now(clock).getDayOfWeek();
        String dayOfWeekInPortuguese = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.of("pt", "BR"));
        if(!configuration.getDaysOfWeek().contains(dayOfWeekInPortuguese)){
            String sb = "Lamentamos, mas estamos fechados. Abrimos: " +
                    configuration.getDaysOfWeek().getFirst() +
                    ", " +
                    configuration.getDaysOfWeek().get(1) +
                    ", " +
                    configuration.getDaysOfWeek().get(2) +
                    ", " +
                    configuration.getDaysOfWeek().get(3) +
                    ", " +
                    configuration.getDaysOfWeek().get(4) +
                    ", " +
                    configuration.getDaysOfWeek().get(5);
            throw new BusinessLogicException(sb);
        }
    }

    public Page<OrderResDto> all(Pageable pageable){
        User user = loggedUserService.loggedUser(token);
        List<String> rolesLoggedUser = user.getRoles().stream().map(r -> r.getName().getName()).toList();
        Page<Order> orders = orderRepository.findOrderByLoggedUser(user.getId(), rolesLoggedUser, pageable);
        if (rolesLoggedUser.contains("ADMIN_USER"))
            return orderRepository.findAll(pageable).map(mapper::entityToOrderResDto);
        return orders.map(mapper::entityToOrderResDto);
    }

    public OrderResDto findById(Long idOrder){
        User user = loggedUserService.loggedUser(token);
        Optional<Order> order = orderRepository.findByIdUser(user.getId(), idOrder);
        if(order.isPresent())
           return mapper.entityToOrderResDto(order.get());
        throw new ObjectNotFoundException("Pedido não encontrado. "+idOrder);
    }

    //TODO refactor abstract factory
    @Transactional
    public OrderResUpdateDto updateOrder(OrderReqUpdateDto orderReqUpdateDto, Long idOrder){
        User user = loggedUserService.loggedUser(token);
        Order order = orderRepository.findById(idOrder).orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado."));
        if(userProfileValidation.isAdminUser(user)){
            order.setStatus(orderReqUpdateDto.status());
            return mapper.entityToOrderResUpdateDto(order);
        }
        if(userProfileValidation.isChefUser(user))
            return this.updateOrderByChef(order, orderReqUpdateDto, user);
        if(userProfileValidation.isDeliveryManUser(user))
            return this.updateOrderByDeliveryMan(order, orderReqUpdateDto, user);

        throw new BusinessLogicException(OPERATION_NOT_PERMITTED);
    }

    private OrderResUpdateDto updateOrderByDeliveryMan(Order order, OrderReqUpdateDto orderReqUpdateDto, User user){
        if(orderStatusValidation.isDeliveryManUserCanChange(order, orderReqUpdateDto)){
            this.updateDeliveryMan(order, user);
            order.setStatus(orderReqUpdateDto.status());
            return mapper.entityToOrderResUpdateDto(orderRepository.save(order));
        }
        throw new BusinessLogicException(OPERATION_NOT_PERMITTED);
    }

    private void updateDeliveryMan(Order order, User user) {
        if(orderStatusValidation.isStatusOrderForDelivery(order))
            order.setDeliveryMan(user);
    }

    private OrderResUpdateDto updateOrderByChef(Order order, OrderReqUpdateDto orderReqUpdateDto, User user){
        if(orderStatusValidation.isChefUserCanChange(order, orderReqUpdateDto)){
            this.updateAttendant(order, user);
            order.setStatus(orderReqUpdateDto.status());
            return mapper.entityToOrderResUpdateDto(orderRepository.save(order));
        }
        throw new BusinessLogicException(OPERATION_NOT_PERMITTED);
    }

    private void updateAttendant(Order order, User user) {
        if(orderStatusValidation.isStatusOrderAwaiting(order))
            order.setAttendant(user);
    }
}
