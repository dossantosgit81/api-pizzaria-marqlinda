package com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.login;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.TestConfigs;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.AbstractIntegrationTest;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.util.MockUser;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;
import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class LoginIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    public void setUp(){
        port = TestConfigs.SERVER_PORT;
        defaultParser = Parser.JSON;
    }

    @Test
    public void shouldReturn422_WhenInvalidFields(){
        var user = MockUserLogin.reqInvalidFields();
        given()
                .contentType(ContentType.JSON)
                .body(Map.of("email", user.get("email"), "password", user.get("password")))
        .when()
                .post("/api/login")
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    public void shouldReturn200_WhenValidCredentials(){
        var user = MockUser.reqCommonUserValidPost();
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
        var user = MockUser.reqCommonUserValidPost2();
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
        var user = MockUser.reqCommonUserValidPost3();
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
