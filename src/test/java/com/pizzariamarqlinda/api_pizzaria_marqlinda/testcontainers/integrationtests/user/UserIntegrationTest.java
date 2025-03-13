package com.pizzariamarqlinda.api_pizzaria_marqlinda.testcontainers.integrationtests.user;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.TestConfigs;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.testcontainers.integrationtests.AbstractIntegrationTest;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    public void setUp(){
        port = TestConfigs.SERVER_PORT;
        defaultParser = Parser.JSON;
    }


    @Test
    public void mustSuccessfullyRegisterAUser() throws Exception {
        given()
            .contentType(ContentType.JSON)
            .body(Mock.reqValidPost())
        .when()
            .post("/api/users")
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturn409WhenRegisteringAUserWithADuplicateEmail() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(Mock.reqInvalidPost())
        .when()
                .post("/api/users");
        given()
                .contentType(ContentType.JSON)
                .body(Mock.reqInvalidPost())
        .when()
                .post("/api/users")
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void shouldReturn422WhenInvalidFields() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(Mock.reqInvalidFields())
        .when()
                .post("/api/users")
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    public void shouldReturn403WhenAccessedByACommonUser() throws Exception {
        var user = Mock.reqValidPost2();
        //Post a user
        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("/api/users");

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

        //Assert
        given()
                .header("Authorization", "Bearer "+token)
                .when()
                    .get("/api/users")
                .then()
                    .statusCode(HttpStatus.FORBIDDEN.value());
    }

}
