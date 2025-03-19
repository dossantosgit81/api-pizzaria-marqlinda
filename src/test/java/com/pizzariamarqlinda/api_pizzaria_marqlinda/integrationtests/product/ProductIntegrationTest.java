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
import org.springframework.web.multipart.MultipartFile;

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

    private final MockMultipartFile mockMultipartFile = new MockMultipartFile("perfil", " perfil.jpeg", "image/jpeg", "content".getBytes());

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
                .multiPart("file", new File("./src/test/resources/storage/test-image.jpg"))
                .contentType("multipart/form-data")
        .when()
                .post("/api/products")
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    public void shouldReturn200_WhenImageExists() throws Exception {
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
        var response =
                given()
                    .header("Authorization", "Bearer "+token)
                    .multiPart("description", "Product")
                    .multiPart("details", "Product test")
                    .multiPart("price", "37.90")
                    .multiPart("file", new File("./src/test/resources/storage/test-image.jpg"))
                    .contentType("multipart/form-data")
                .when()
                    .post("/api/products")
                .then()
                    .extract()
                    .response();

        given()
                .header("Authorization", "Bearer "+token)
        .when()
                .get("/api/products/"+1)
        .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());

    }

}
