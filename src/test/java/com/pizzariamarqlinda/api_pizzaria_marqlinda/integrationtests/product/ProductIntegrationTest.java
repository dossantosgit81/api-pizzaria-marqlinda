package com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.product;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.TestConfigs;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.AbstractIntegrationTest;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.util.MockUser;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class ProductIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    public void setUp(){
        port = TestConfigs.SERVER_PORT;
    }

    @Test
    public void shouldReturn422_WhenThereAreInvalidFields() throws Exception {
        var user = MockUser.reqCommonUserValidPost();
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

        //Req
        given()
                .header("Authorization", "Bearer "+token)
                .multiPart("description", "")
                .multiPart("details", "")
                .multiPart("price", "")
                .multiPart("file", new File("/home/rafael/Documents/storage/test-image.jpeg"))
                .contentType("multipart/form-data")
        .when()
                .post("/api/products")
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

}
