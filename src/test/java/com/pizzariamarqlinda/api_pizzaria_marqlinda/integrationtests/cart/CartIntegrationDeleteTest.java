package com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.cart;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.TestConfigs;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.AbstractIntegrationTest;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.util.MockUser;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CartIntegrationDeleteTest extends AbstractIntegrationTest {

    @BeforeEach
    public void setUp(){
        port = TestConfigs.SERVER_PORT;
    }

    @Test
    public void shouldRegisterItemsInCart_WhenValidFields(){
        var user = MockUser.reqUserAdminLogin();
        //Get token
        String token =
                given()
                        .contentType(ContentType.JSON)
                        .body(Map.of("email", user.get("email"), "password", user.get("password")))
                .when()
                        .post("/api/login")
                .then()
                        .extract()
                        .path("token");

        //Register category
        given()
                .contentType(ContentType.JSON)
                .body(Map.of("name", "Drinks"))
                .header("Authorization", "Bearer "+token)
        .when()
                .post("/api/categories");

        //Register a product
        given()
                .header("Authorization", "Bearer "+token)
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

        given()
                .header("Authorization", "Bearer "+token)
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

        //Add item 1
       given()
                .contentType(ContentType.JSON)
                .body(Map.of("quantity", "5"))
                .header("Authorization", "Bearer "+token)
        .when()
                .post("/api/carts/products/1");

        //Add item 2
       given()
                .contentType(ContentType.JSON)
                .body(Map.of("quantity", "5"))
                .header("Authorization", "Bearer "+token)
        .when()
                .post("/api/carts/products/2");

       //Delete item 1
       Float total = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+token)
        .when()
                .delete("/api/carts/products/1")
        .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .path("total");

        BigDecimal valueExpected = new BigDecimal("50.00");
        assertEquals(0, valueExpected.compareTo(new BigDecimal(total)));

    }


}
