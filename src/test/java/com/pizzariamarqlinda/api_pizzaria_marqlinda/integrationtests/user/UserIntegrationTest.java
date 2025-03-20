package com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.user;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.TestConfigs;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.AbstractIntegrationTest;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.PageResponseDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.dto.UserResDto;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.util.MockUser;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    public void setUp(){
        port = TestConfigs.SERVER_PORT;
        defaultParser = Parser.JSON;
    }

    @Test
    public void shouldReturn201_WhenRegisteringUserWithValidAndFilledFields() throws Exception {
        given()
            .contentType(ContentType.JSON)
            .body(MockUser.reqCommonUserValidPost())
        .when()
            .post("/api/users")
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturn409_WhenUserWithSameEmailAlreadyExists() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(MockUser.reqInvalidPost())
        .when()
                .post("/api/users");
        given()
                .contentType(ContentType.JSON)
                .body(MockUser.reqInvalidPost())
        .when()
                .post("/api/users")
        .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void shouldReturn422_WhenThereAreInvalidFields() throws Exception {
        given()
                .contentType(ContentType.JSON)
                .body(MockUser.reqInvalidFields())
        .when()
                .post("/api/users")
        .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    public void shouldReturn403_WhenCommonUserTriesToAccessUserList() throws Exception {
        var user = MockUser.reqCommonUserValidPost2();
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
                .param("pageNumber", "0")
                .param("pageSize", "1")
                .when()
                    .get("/api/users")
                .then()
                    .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void shouldReturn200_WhenAdminUserAccessesUserList() throws Exception {
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
        //Assert
        given()
                .header("Authorization", "Bearer "+token)
                .param("pageNumber", "0")
                .param("pageSize", "1")
        .when()
                .get("/api/users")
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturn200_WhenCommonUserRequestsOwnUserDetails() throws Exception {
        var user = MockUser.reqCommonUserValidPost3();
        var response = given()
                .contentType(ContentType.JSON)
                .body(MockUser.reqCommonUserValidPost3())
        .when()
                .post("/api/users")
        .then()
                .extract()
                .response();

        Pattern pattern = Pattern.compile( "/users/(\\d+)");
        Matcher matcher = pattern.matcher(response.header("Location"));
        String id = "";
        if(matcher.find()){
            id = matcher.group(1);
        }

        String token =
                given()
                        .contentType(ContentType.JSON)
                        .body(Map.of("email", user.get("email"), "password", user.get("password")))
                .when()
                        .post("/api/login")
                .then()
                        .extract()
                        .path("token");

        given()
                .header("Authorization", "Bearer "+token)
        .when()
                .get("/api/users/"+id)
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturn403WhenCommonUserIsNotTheRequestedUser() throws Exception {
        var user = MockUser.reqCommonUserValidPost3();
        given()
                .contentType(ContentType.JSON)
                .body(MockUser.reqCommonUserValidPost3())
                .when()
                .post("/api/users");

        given()
                .contentType(ContentType.JSON)
                .body(MockUser.reqValidPost4())
        .when()
                .post("/api/users");


        String token =
                given()
                        .contentType(ContentType.JSON)
                        .body(Map.of("email", user.get("email"), "password", user.get("password")))
                .when()
                        .post("/api/login")
                .then()
                        .extract()
                        .path("token");


        given()
                .header("Authorization", "Bearer "+token)
                .when()
                .get("/api/users/2")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    public void shouldReturn200_WhenAdminUserAccessesSingleUserOtherThanThemselves() throws Exception {
        //O Cadastro está acontecendo no import.sql por ele ser um usuário ADMIN
        var user = MockUser.reqUserAdminLogin();
        given()
                .contentType(ContentType.JSON)
                .body(MockUser.reqCommonUserValidPost3())
                .when()
                .post("/api/users");

        given()
                .contentType(ContentType.JSON)
                .body(MockUser.reqCommonUserValidPost5())
                .when()
                .post("/api/users");

        //Get token admin
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
                .get("/api/users/1")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
