package com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.user;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.config.TestConfigs;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.AbstractIntegrationTest;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.order.MockOrdersCommonUserAll;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.Role;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.model.enums.ProfilesUserEnum;
import com.pizzariamarqlinda.api_pizzaria_marqlinda.util.MockUser;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class UserIntegrationAddRoleAUserTest extends AbstractIntegrationTest {

    @BeforeEach
    public void setUp(){
        port = TestConfigs.SERVER_PORT;
        defaultParser = Parser.JSON;
    }

    @Test
    public void shouldReturnError_WhenUserAdminTryUpdatePermissionAUser() throws Exception {
        String tokenCommonExample = MockOrdersCommonUserAll.saveUserCommon1AndReturnToken();
        var response = given()
            .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+tokenCommonExample)
            .body(body())
            .param("email", "")
        .when()
            .put("/api/users/roles")
        .then()
            .statusCode(HttpStatus.FORBIDDEN.value())
                .extract()
                .response();
        String message = response.jsonPath().getString("message");
        int status = response.jsonPath().getInt("status");
        assertEquals("Acesso negado.", message);
        assertEquals(403, status);
    }

    @Test
    public void shouldReturnError_WhenUserAdminAssignRolesToYourself(){
        String tokenAdmin = MockOrdersCommonUserAll.login(MockUser.reqUserAdminLogin());
        var response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+tokenAdmin)
                .body(body())
                .param("email", MockUser.reqUserAdminLogin().get("email"))
                .when()
                .put("/api/users/roles")
                .then()
                .extract()
                .response();
        String message = response.jsonPath().getString("message");
        int status = response.jsonPath().getInt("status");
        assertEquals("Operação não permitida.", message);
        assertEquals(409, status);
    }

    @Test
    public void shouldReturnError_WhenUserHasRoles(){
        String tokenAdmin = MockOrdersCommonUserAll.login(MockUser.reqUserAdminLogin());
        var response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+tokenAdmin)
                .body(bodyRolesRepeated())
                .param("email", "    deliveryMan@gmail.com")
                .when()
                .put("/api/users/roles")
                .then()
                .extract()
                .response();
        String message = response.jsonPath().getString("message");
        int status = response.jsonPath().getInt("status");
        assertEquals("Usuário já possui todas as roles enviadas.", message);
        assertEquals(409, status);
    }

    @Test
    public void shouldReturnError_WhenUserRolesNotExists(){
        String tokenAdmin = MockOrdersCommonUserAll.login(MockUser.reqUserAdminLogin());
        var response =
            given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+tokenAdmin)
                .body(invalidEnumProfile())
                .param("email", "deliveryMan@gmail.com")
            .when()
                .put("/api/users/roles")
            .then()
                    .log().all()
                .extract()
                .response();
        int status = response.jsonPath().getInt("status");
        assertEquals(400, status);
    }

    @Test
    public void shouldReturn200_WhenUpdateProfilesUser(){
        String tokenAdmin = MockOrdersCommonUserAll.login(MockUser.reqUserAdminLogin());
        String tokenCommonExample = MockOrdersCommonUserAll.saveUserCommon2AndReturnToken();
        var response =
            given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer "+tokenAdmin)
                .body(body())
                .param("email", MockUser.reqCommonUserValidPost2().get("email"))
            .when()
                .put("/api/users/roles")
            .then()
                .log().all()
                .extract()
                .response();
        String roleCommonUserExpected = response.jsonPath().getString("roles[0].name");
        String roleAdminExpected = response.jsonPath().getString("roles[1].name");
        List<Role> roles = response.jsonPath().getList("roles");

        assertEquals(2, roles.size());
        assertEquals(ProfilesUserEnum.COMMON_USER.getName(), roleCommonUserExpected);
        assertEquals(ProfilesUserEnum.ADMIN_USER.getName(), roleAdminExpected);
    }

    public Map<String, Object> body(){
        Map<String, Object> role1 = new HashMap<>();
        role1.put("name", "ADMIN_USER");

        Map<String, Object> role2 = new HashMap<>();
        role2.put("name", "COMMON_USER");

        List<Map<String, Object>> roles = Arrays.asList(role1, role2);

        Map<String, Object> body = new HashMap<>();
        body.put("roles", roles);
        return body;
    }

    public Map<String, Object> bodyRolesRepeated(){
        Map<String, Object> role1 = new HashMap<>();
        role1.put("name", "DELIVERY_MAN_USER");

        Map<String, Object> role2 = new HashMap<>();
        role2.put("name", "COMMON_USER");

        List<Map<String, Object>> roles = Arrays.asList(role1, role2);

        Map<String, Object> body = new HashMap<>();
        body.put("roles", roles);
        return body;
    }

    public Map<String, Object> invalidEnumProfile(){
        Map<String, Object> role1 = new HashMap<>();
        role1.put("name", "DELIVERY");

        Map<String, Object> role2 = new HashMap<>();
        role2.put("name", "COMMON");

        List<Map<String, Object>> roles = Arrays.asList(role1, role2);

        Map<String, Object> body = new HashMap<>();
        body.put("roles", roles);
        return body;
    }

}
