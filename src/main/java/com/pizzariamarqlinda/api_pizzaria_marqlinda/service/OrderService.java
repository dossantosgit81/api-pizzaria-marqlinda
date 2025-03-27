package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.BusinessLogicException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.User;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.OrderReqDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Setter
    private JwtAuthenticationToken token;

    private final LoggedUserService loggedUserService;
    private final AddressService addressService;
    private final PaymentMethodService paymentMethodService;

    /*
    * private Long id;
private StatusEnum status;
private LocalDateTime dateTime;
private BigDecimal total;
private String observations;
private Boolean delivery;
private LocalDateTime deliveryForecast;
private BigDecimal rateDelivery;
private List<ItemProduct> items;

private User user;
private PaymentMethod paymentMethod;
private Address address;
    * */

    private final ConfigurationsService configuration;

    @Transactional
    public Long save(OrderReqDto orderReqDto){
        User loggedUser = loggedUserService.loggedUser(token);
        if(loggedUser.getCart().getItems().isEmpty())
            throw new BusinessLogicException("Não há itens de compra no carrinho.");
        LocalTime officeStartTime = LocalTime.of(configuration.getOpeningHour(), configuration.getOpeningMinute());
        LocalTime endOfficeBusinessHours = LocalTime.of(configuration.getClosingHour(), configuration.getClosingMinute());
        if(LocalTime.now().isBefore(officeStartTime) || LocalTime.now().isAfter(endOfficeBusinessHours))
            throw new BusinessLogicException("Fora do horário de expediente. Atendemos das "+ configuration.getOpeningHour() + "as" + configuration.getClosingHour());

        //StatusEnum status
        return null;
    }
}
