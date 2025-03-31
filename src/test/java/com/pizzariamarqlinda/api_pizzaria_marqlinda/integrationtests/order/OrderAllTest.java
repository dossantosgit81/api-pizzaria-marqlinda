package com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.order;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.TestConfigs;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.BusinessLogicException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.AbstractIntegrationTest;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.repository.OrderRepository;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.OrderService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.util.MockUser;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import java.io.File;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderAllTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void test(){
        orderRepository.findOrderByLoggedUser(1L);
    }

}
