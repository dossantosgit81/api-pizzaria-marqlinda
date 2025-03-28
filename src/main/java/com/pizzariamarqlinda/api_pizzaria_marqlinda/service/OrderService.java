package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.BusinessLogicException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.OrderMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Address;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Order;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.PaymentMethod;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.OrderReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.StatusEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Setter
    private JwtAuthenticationToken token;

    private final OrderMapper mapper = OrderMapper.INSTANCE;
    private final LoggedUserService loggedUserService;
    private final AddressService addressService;
    private final PaymentMethodService paymentMethodService;
    private final ConfigurationsService configuration;

    @Transactional
    public Long save(OrderReqDto orderReqDto){
        Order orderConverted = mapper.orderReqDtoToEntity(orderReqDto);
        User loggedUser = loggedUserService.loggedUser(token);
        this.verifyQuantityItemsInCart(loggedUser);
        this.checkOfficeHours();
        this.checkBusinessDay();
        Order savedOrder = saveOrder(orderReqDto, orderConverted, loggedUser);
        //TODO anular items do carrinho
        return savedOrder.getId();
    }

    public Order saveOrder(OrderReqDto orderReqDto, Order orderConverted, User loggedUser){
        Order order = this.orderWithTotal(orderReqDto, orderConverted, loggedUser);
        order.setStatus(StatusEnum.ORDER_IN_PROGRESS);
        order.setDateTimeOrder(LocalDateTime.now());
        order.setDeliveryForecast(order.getDateTimeOrder().plusMinutes(configuration.getDeliveryForecast()));
        order.setItems(loggedUser.getCart().getItems());
        order.setUser(loggedUser);
        PaymentMethod paymentMethod = paymentMethodService.findById(orderReqDto.paymentMethod().id());
        order.setPaymentMethod(paymentMethod);
        Address address = addressService.findById(orderReqDto.address().id());
        order.setAddress(address);
        return order;
    }

    public Order orderWithTotal(OrderReqDto orderReqDto, Order order, User loggedUser){
        if(orderReqDto.delivery()){
            BigDecimal valueWithRateDelivery = loggedUser.getCart().getTotal().add(configuration.getRateDelivery());
            order.setTotal(valueWithRateDelivery);
            order.setRateDelivery(configuration.getRateDelivery());
        }else {
            order.setTotal(loggedUser.getCart().getTotal());
            order.setDelivery(null);
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
        if(LocalTime.now().isBefore(officeStartTime) || LocalTime.now().isAfter(endOfficeBusinessHours))
            throw new BusinessLogicException("Fora do horário de expediente. Atendemos das "+ configuration.getOpeningHour() + "as" + configuration.getClosingHour());
    }

    private void checkBusinessDay(){
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        String dayOfWeekInPortuguese = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.of("pt", "BR"));
        if(!configuration.getDaysOfWeek().contains(dayOfWeekInPortuguese)){
            String sb = "Lamentamos, mas estamos fechados. Estamos fechado. Abrimos: " +
                    configuration.getDaysOfWeek().getFirst() +
                    ", " +
                    configuration.getDaysOfWeek().get(1) +
                    ", " +
                    configuration.getDaysOfWeek().get(2) +
                    ", " +
                    configuration.getDaysOfWeek().get(3) +
                    ", " +
                    configuration.getDaysOfWeek().get(4);
            throw new BusinessLogicException(sb);
        }
    }
}
