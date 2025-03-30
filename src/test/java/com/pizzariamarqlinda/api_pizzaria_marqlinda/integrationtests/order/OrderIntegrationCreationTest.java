package com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.order;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.TestConfigs;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.BusinessLogicException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.AbstractIntegrationTest;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.AddressReqIdDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.OrderReqDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.PaymentMethodReqIdDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.ConfigurationsService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.OrderService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.util.MockUser;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class OrderIntegrationCreationTest extends AbstractIntegrationTest {

    @Autowired
    private OrderService orderService;

    @BeforeEach
    public void setUp(){
        port = TestConfigs.SERVER_PORT;
        Clock fixedClock = Clock.fixed(Instant.parse("2025-03-29T17:15:30-03:00"), ZoneId.of("America/Sao_Paulo"));
        orderService.setClock(fixedClock);
    }

    @Test
    @Order(1)
    public void shouldRegisterOrderWithSuccessWithRateDeliveryTrue_WhenValidFields() throws JsonProcessingException {
        var user = MockUser.reqUserAdminLogin();
        String token = login(user);

        registerCategoryOne(token);
        registerCategoryTwo(token);

        registerProductOne(token);
        registerProductTwo(token);

        registerItemProductOne(token);
        registerItemProductTwo(token);

        registerAddress(token);

        String req = """
                            {
                                "observations": "Entregar rápido",
                                "delivery": true,
                                "paymentMethod": { "id": 1 },
                                "address": { "id": 1 }
                            }
                            """;
       Response response = given()
                .contentType(ContentType.JSON)
                .body(req)
                .header("Authorization", "Bearer "+ token)
        .when()
                .post("/api/orders")
        .then()
                .log().all()
                .extract()
                .response();

        Float total = response.body().path("total");
        BigDecimal valueExpected = new BigDecimal("75.99");
        BigDecimal totalBigDecimal = new BigDecimal(String.valueOf(total));
        assertEquals(0, valueExpected.compareTo(totalBigDecimal));
    }

    @Test
    @Order(2)
    public void shouldRegisterOrderWithSuccess_WhenValidFields() throws JsonProcessingException {
        var user = MockUser.reqUserAdminLogin();
        String token = login(user);

        registerCategoryOne(token);
        registerCategoryTwo(token);

        registerProductOne(token);
        registerProductTwo(token);

        registerItemProductOne(token);
        registerItemProductTwo(token);

        registerAddress(token);

        String req = """
                            {
                                "observations": "Entregar rápido",
                                "delivery": false,
                                "paymentMethod": { "id": 1 },
                                "address": { "id": 1 }
                            }
                            """;
        Response response = given()
                .contentType(ContentType.JSON)
                .body(req)
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/orders")
                .then()
                .log().all()
                .extract()
                .response();

        Float total = response.body().path("total");
        BigDecimal valueExpected = new BigDecimal("70.00");
        BigDecimal totalBigDecimal = new BigDecimal(String.valueOf(total));
        assertEquals(0, valueExpected.compareTo(totalBigDecimal));
    }

    @Test
    @Order(3)
    public void shouldReturnBusinessLogicException_WhenOutHourOffice() throws JsonProcessingException {
        Clock fixedClock = Clock.fixed(Instant.parse("2025-03-29T18:15:30-03:00"), ZoneId.of("America/Sao_Paulo"));
        orderService.setClock(fixedClock);
        var user = MockUser.reqUserAdminLogin();
        String token = login(user);

        String req = """
                            {
                                "observations": "Entregar rápido",
                                "delivery": true,
                                "paymentMethod": { "id": 1 },
                                "address": { "id": 1 }
                            }
                            """;
        try{
            given()
                    .contentType(ContentType.JSON)
                    .body(req)
                    .header("Authorization", "Bearer "+ token)
                    .when()
                    .post("/api/orders")
                    .then()
                    .log().all()
                    .extract()
                    .response();
        }catch (Exception ex){
            Assertions.assertEquals(BusinessLogicException.class, ex.getClass());
        }
    }

    @Test
    @Order(4)
    public void shouldBusinessLogicException_WhenNotHasItem() throws JsonProcessingException {
        var user = MockUser.reqUserAdminLogin();
        String token = login(user);

        registerCategoryOne(token);
        registerCategoryTwo(token);

        registerProductOne(token);
        registerProductTwo(token);

        registerAddress(token);

        String req = """
                            {
                                "observations": "Entregar rápido",
                                "delivery": false,
                                "paymentMethod": { "id": 1 },
                                "address": { "id": 1 }
                            }
                            """;
        try {
             given()
                    .contentType(ContentType.JSON)
                    .body(req)
                    .header("Authorization", "Bearer "+ token)
                    .when()
                    .post("/api/orders")
                    .then()
                    .log().all()
                    .extract()
                    .response();
        }catch (Exception ex){
            Assertions.assertEquals(BusinessLogicException.class, ex.getClass());
        }


    }

    private static void registerAddress(String token){
        String jsonBody = """
                {
                    "city": "São Paulo",
                    "state": "SP",
                    "street": "Avenida Paulista",
                    "number": 123,
                    "zipcode": "01311-000",
                    "neighborhood": "Bela Vista",
                    "title": "Casa"
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .header("Authorization", "Bearer "+ token)
        .when()
                .post("/api/addresses");
    }

    private static void registerPaymentMethod(String token){
        String jsonBody = """
            {
                "id": 1,
                "typePayment": "Cartão de Crédito"
            }
            """;
        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/payments-method");
    }

    private static void registerItemProductTwo(String token) {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of("quantity", "1"))
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/carts/products/2");
    }

    private static void registerItemProductOne(String token) {
        given()
                 .contentType(ContentType.JSON)
                 .body(Map.of("quantity", "5"))
                 .header("Authorization", "Bearer "+ token)
         .when()
                 .post("/api/carts/products/1");
    }

    private static void registerProductTwo(String token) {
        given()
                .header("Authorization", "Bearer "+ token)
                .param("idCategory", "2")
                .multiPart("description", "Pizza")
                .multiPart("details", "Pizza")
                .multiPart("price", "20.00")
                .multiPart("available", true)
                .multiPart("highlight", false)
                .multiPart("file", new File("./src/test/resources/storage/test-controller.jpg"))
                .contentType("multipart/form-data")
                .when()
                .post("/api/products");
    }

    private static void registerProductOne(String token) {
        given()
                .header("Authorization", "Bearer "+ token)
                .param("idCategory", "1")
                .multiPart("description", "Lemon soda")
                .multiPart("details", "Lemon soda")
                .multiPart("price", "10.00")
                .multiPart("available", true)
                .multiPart("highlight", false)
                .multiPart("file", new File("./src/test/resources/storage/test-controller.jpg"))
                .contentType("multipart/form-data")
        .when()
                .post("/api/products");
    }

    private static void registerCategoryTwo(String token) {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of("name", "Food"))
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/categories");
    }

    private static void registerCategoryOne(String token) {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of("name", "Drinks"))
                .header("Authorization", "Bearer "+ token)
        .when()
                .post("/api/categories");
    }

    private static String login(Map<String, Object> user) {
        return given()
                        .contentType(ContentType.JSON)
                        .body(Map.of("email", user.get("email"), "password", user.get("password")))
                .when()
                        .post("/api/login")
                .then()
                        .extract()
                        .path("token");

    }


}
