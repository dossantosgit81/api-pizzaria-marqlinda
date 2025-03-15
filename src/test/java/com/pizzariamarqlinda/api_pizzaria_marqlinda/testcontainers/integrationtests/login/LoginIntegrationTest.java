package com.pizzariamarqlinda.api_pizzaria_marqlinda.testcontainers.integrationtests.login;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.TestConfigs;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.testcontainers.integrationtests.AbstractIntegrationTest;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.testcontainers.integrationtests.util.MockUser;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoginIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    public void setUp(){
        port = TestConfigs.SERVER_PORT;
        defaultParser = Parser.JSON;
    }

    @Test
    public void shouldLogInSuccessfully(){
        var user = MockUser.reqValidPost();
        given()
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("/api/users");

        given()
                .contentType(ContentType.JSON)
                .body(Map.of("email", user.get("email"), "password", user.get("password")))
        .when()
                .post("/api/login")
        .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturn403WhenEmailIsInvalid(){
        var user = MockUser.reqValidPost2();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/users");

        given()
                .contentType(ContentType.JSON)
                .body(Map.of("email", "incorret@email.com", "password", user.get("password")))
        .when()
                .post("/api/login")
        .then()
                .log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void shouldReturn403WhenPasswordIsInvalid(){
        var user = MockUser.reqValidPost3();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/users");

        given()
                .contentType(ContentType.JSON)
                .body(Map.of("email", user.get("email"), "password", "invalidpassword"))
                .when()
                .post("/api/login")
                .then()
                .log().all()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

}
