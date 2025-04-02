package com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.order;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.TestConfigs;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.exception.ObjectNotFoundException;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.service.OrderService;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.util.MockUser;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.order.MockOrdersCommonUserAll.*;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class OrdersFindByIdByUserIntegrationsTest {

    @Autowired
    private OrderService orderService;

    @BeforeEach
    public void setUp(){
        port = TestConfigs.SERVER_PORT;
        Clock fixedClock = Clock.fixed(Instant.parse("2025-03-29T17:15:30-03:00"), ZoneId.of("America/Sao_Paulo"));
        orderService.setClock(fixedClock);
    }

    @Test
    public void shouldReturnOrderByIdUserAndId_WhenSearchForDeliveryMan1(){
        String tokenUser1 = MockOrdersCommonUserAll.saveUserCommon1AndReturnToken();
        String tokenUser2 = MockOrdersCommonUserAll.saveUserCommon2AndReturnToken();

        commonsMock();
        saveItemsUserCommon1();
        saveItemsUserCommon2();

        saveOrderUserOne(tokenUser1);
        saveOrderUserTwo(tokenUser2);

       Response response = given()
               .contentType(ContentType.JSON)
               .header("Authorization", "Bearer "+tokenUser1)
       .when()
               .get("/api/orders/1")
       .then()
               .log().all()
               .extract().response();
        int id = response.jsonPath().getInt("id");
        assertEquals(1, id);
    }

    @Test
    public void shouldReturnOnlyOrdersForDeliveryAndOutForDelivery_WhenSearchForDeliveryMan2(){
        String tokenUser1 = MockOrdersCommonUserAll.saveUserCommon1AndReturnToken();
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+tokenUser1)
                .when()
                .get("/api/orders/100")
                .then()
                .log().all()
                .extract().response();;

        int status = response.jsonPath().getInt("status");
        assertEquals(404, status);
    }

    @Test
    public void shouldReturnOn403_WhenSearchForDelivery(){
        String tokenUser1 = MockOrdersCommonUserAll.saveUserDeliveryManAndReturnToken2();
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+tokenUser1)
                .when()
                .get("/api/orders/1")
                .then()
                .log().all()
                .extract().response();;

        int status = response.jsonPath().getInt("status");
        assertEquals(403, status);

    }

    public static void commonsMock(){
        String tokenAdmin = MockOrdersCommonUserAll.login(MockUser.reqUserAdminLogin());
        registerCategoryOne(tokenAdmin);
        registerCategoryTwo(tokenAdmin);
        registerProductTwo(tokenAdmin);
        registerProductOne(tokenAdmin);
        registerPaymentMethod(tokenAdmin);
    }

    public static void saveItemsUserCommon1(){
        String token = MockOrdersCommonUserAll.saveUserCommon1AndReturnToken();
        registerAddressOne(token);
        registerItemProductOne(token);
        registerItemProductTwo(token);

    }

    public static void saveItemsUserCommon2(){
        String token = MockOrdersCommonUserAll.saveUserCommon2AndReturnToken();
        registerAddressOne(token);
        registerItemProductOne(token);
        registerItemProductTwo(token);
    }

    public static void saveOrderUserOne(String token){
        String req = """
                            {
                                "observations": "Entregar rápido",
                                "delivery": true,
                                "paymentMethod": { "id": 1 },
                                "address": { "id": 1 }
                            }
                            """;
         given()
                .contentType(ContentType.JSON)
                .body(req)
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/orders")
                .then()
                .log().all();
    }

    public static void saveOrderUserTwo(String token){
        String req = """
                            {
                                "observations": "Entregar rápido",
                                "delivery": true,
                                "paymentMethod": { "id": 1 },
                                "address": { "id": 2 }
                            }
                            """;
        given()
                .contentType(ContentType.JSON)
                .body(req)
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/orders")
                .then()
                .log().all();
    }

}
