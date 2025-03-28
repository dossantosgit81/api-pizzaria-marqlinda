package com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.order;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.TestConfigs;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.AbstractIntegrationTest;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.util.MockUser;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class CartIntegrationCreationTest extends AbstractIntegrationTest {

    @BeforeEach
    public void setUp(){
        port = TestConfigs.SERVER_PORT;
    }

    @Test
    public void shouldRegisterOrderWithSuccess_WhenValidFields(){
        var user = MockUser.reqUserAdminLogin();
        String token = login(user);
        
        this.registerCategoryOne(token);
        this.registerCategoryTwo(token);

        this.registerProductOne(token);
        this.registerProductTwo(token);

        this.registerItemProductOne(token);
        this.registerItemProductTwo(token);


    }

    private void registerItemProductTwo(String token) {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of("quantity", "1"))
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/carts/products/2");
    }

    private void registerItemProductOne(String token) {
        given()
                 .contentType(ContentType.JSON)
                 .body(Map.of("quantity", "5"))
                 .header("Authorization", "Bearer "+ token)
         .when()
                 .post("/api/carts/products/1");
    }

    private void registerProductTwo(String token) {
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

    private void registerProductOne(String token) {
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

    private void registerCategoryTwo(String token) {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of("name", "Food"))
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/categories");
    }

    private void registerCategoryOne(String token) {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of("name", "Drinks"))
                .header("Authorization", "Bearer "+ token)
        .when()
                .post("/api/categories");
    }

    private String login(Map<String, Object> user) {
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
