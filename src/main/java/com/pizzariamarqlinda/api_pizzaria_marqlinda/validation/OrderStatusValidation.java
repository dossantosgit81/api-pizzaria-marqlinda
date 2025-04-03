package com.pizzariamarqlinda.api_pizzaria_marqlinda.validation;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Order;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.OrderReqUpdateDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.StatusOrderEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusValidation {

    public boolean isChefUserCanChange(Order order, OrderReqUpdateDto orderReqUpdateDto){
        boolean isOrderAwaiting = order.getStatus().getName().equals(StatusOrderEnum.ORDER_AWAITING_SERVICE.getName());
        boolean isOrderReqUpdateDtoProgress = orderReqUpdateDto.status().getName().equals(StatusOrderEnum.ORDER_IN_PROGRESS.getName());

        boolean isOrderProgress = order.getStatus().getName().equals(StatusOrderEnum.ORDER_IN_PROGRESS.getName());
        boolean isOrderReqUpdateDtoForDelivery = orderReqUpdateDto.status().getName().equals(StatusOrderEnum.ORDER_FOR_DELIVERY.getName());

        return (isOrderAwaiting && isOrderReqUpdateDtoProgress) || (isOrderProgress && isOrderReqUpdateDtoForDelivery);
    }

    public boolean isDeliveryManUserCanChange(Order order, OrderReqUpdateDto orderReqUpdateDto){
        boolean isOrderAwaiting = order.getStatus().getName().equals(StatusOrderEnum.ORDER_AWAITING_SERVICE.getName());
        boolean isOrderReqUpdateDtoProgress = orderReqUpdateDto.status().getName().equals(StatusOrderEnum.ORDER_IN_PROGRESS.getName());

        boolean isOrderProgress = order.getStatus().getName().equals(StatusOrderEnum.ORDER_IN_PROGRESS.getName());
        boolean isOrderReqUpdateDtoForDelivery = orderReqUpdateDto.status().getName().equals(StatusOrderEnum.ORDER_FOR_DELIVERY.getName());

        return (isOrderAwaiting && isOrderReqUpdateDtoProgress) || (isOrderProgress && isOrderReqUpdateDtoForDelivery);
    }

    public boolean isStatusOrderAwaitingOrProgress(Order order){
        return StatusOrderEnum.ORDER_AWAITING_SERVICE.getName().equals(order.getStatus().getName())
               || StatusOrderEnum.ORDER_IN_PROGRESS.getName().equals(order.getStatus().getName());
    }

    public boolean isStatusOrderAwaiting(Order order){
        return StatusOrderEnum.ORDER_AWAITING_SERVICE.getName().equals(order.getStatus().getName());
    }

    public boolean isOrderForDeliveryOrOrOrderOutForDelivery(Order order){
        return StatusOrderEnum.ORDER_FOR_DELIVERY.getName().equals(order.getStatus().getName())
                || StatusOrderEnum.ORDER_OUT_FOR_DELIVERY.getName().equals(order.getStatus().getName());
    }

}
