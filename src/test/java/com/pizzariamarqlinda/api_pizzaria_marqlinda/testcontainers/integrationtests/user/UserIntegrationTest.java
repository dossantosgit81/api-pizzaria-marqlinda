package com.pizzariamarqlinda.api_pizzaria_marqlinda.testcontainers.integrationtests.user;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.TestConfigs;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.testcontainers.integrationtests.AbstractIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserIntegrationTest extends AbstractIntegrationTest {

    private static final String name = "Cal";
    private static final String lastName = "John";
    private static final String email = "cj@test.com";
    private static final String password = "12345";
    private static final String phone = "4002-89922";

    private Map<String, Object> req;
    private static String tokenCommonUser = "";

    @Test
    public void shouldLogInSuccessfully() throws Exception {
        basePath = "/api/users";
        port = TestConfigs.SERVER_PORT;

        req = new HashMap<>();
        req.put("name", name);
        req.put("lastName", lastName);
        req.put("email", email);
        req.put("password", password);
        req.put("phone", phone);

        given()
                .contentType(ContentType.JSON)
                .body(req)
                .when()
                .post();

        System.out.println(tokenCommonUser);

    }



}
