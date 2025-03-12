package com.pizzariamarqlinda.api_pizzaria_marqlinda.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class UserControllerTest {

    private static final String name = "Cal";
    private static final String lastName = "John";
    private static final String email = "cj@test.com";
    private static final String password = "12345";
    private static final String phone = "4002-89922";

    private static Map<String, Object> req;
    private static String tokenCommonUser = "";

    @BeforeAll
    public static void setUp(){
        baseURI = "http://localhost";
        port = 8080;

        req = new HashMap<>();
        req.put("name", name);
        req.put("lastName", lastName);
        req.put("email", email);
        req.put("password", password);
        req.put("phone", phone);

        //post a user
        postAUser();

        //Get token
        tokenValidCommonUser();
    }

    private static void tokenValidCommonUser() {
        tokenCommonUser = given()
                .contentType(ContentType.JSON)
                .body(req)
                .when()
                .post("/api/login")
                .then()
                .extract()
                .path("token");

    }

    private static void postAUser() {
        given()
                .contentType(ContentType.JSON)
                .body(req)
                .when()
                .post("/api/users");
    }

    @Test
    public void shouldLogInSuccessfully() throws Exception {

        System.out.println(tokenCommonUser);

    }



}
