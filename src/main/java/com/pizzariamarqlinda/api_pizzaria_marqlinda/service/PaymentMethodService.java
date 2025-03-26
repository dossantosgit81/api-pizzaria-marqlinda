package com.pizzariamarqlinda.api_pizzaria_marqlinda.service;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.mapper.PaymentMethodMapper;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.PaymentMethod;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.PaymentMethodDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository repository;
    private final PaymentMethodMapper mapper = PaymentMethodMapper.INSTANCE;

    public Long save(PaymentMethodDto req ){
       return repository.save(mapper.paymentMethodDtoToEntity(req)).getId();
    }

    public List<PaymentMethodDto> all() {
        List<PaymentMethod> payments = repository.findAll();
        return payments.stream()
                .map(mapper::entityToPaymentMethodDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id){
        PaymentMethod paymentMethod = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Methodo de pagamento não encontrado"));
        repository.delete(paymentMethod);
    }


}
