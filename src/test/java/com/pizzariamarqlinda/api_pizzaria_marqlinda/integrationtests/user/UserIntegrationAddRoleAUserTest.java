package com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.user;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.TestConfigs;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.AbstractIntegrationTest;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.order.MockOrdersCommonUserAll;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.util.MockUser;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class UserIntegrationAddRoleAUserTest extends AbstractIntegrationTest {

    @BeforeEach
    public void setUp(){
        port = TestConfigs.SERVER_PORT;
        defaultParser = Parser.JSON;
    }

    @Test
    public void shouldReturn201_WhenRegisteringUserWithValidAndFilledFields() throws Exception {
        String tokenCommonExample = MockOrdersCommonUserAll.saveUserCommon1AndReturnToken();
        String tokenAdmin = MockOrdersCommonUserAll.login(MockUser.reqUserAdminLogin());
        given()
            .contentType(ContentType.JSON)
           // .body()
           //     .param()
        .when()
            .put("/api/users/roles")
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

}
