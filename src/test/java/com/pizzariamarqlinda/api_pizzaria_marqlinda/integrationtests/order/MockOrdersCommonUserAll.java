package com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.order;

import com.pizzariamarqlinda.api_pizzaria_marqlinda.util.MockUser;
import io.restassured.http.ContentType;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class MockOrdersCommonUserAll {

    public static void registerAddressOne(String token){
        String jsonBody = """
                {
                    "city": "São Paulo",
                    "state": "SP",
                    "street": "Avenida Paulista",
                    "number": 123,
                    "zipcode": "01311-000",
                    "neighborhood": "Bela Vista",
                    "title": "Casa"
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/addresses");
    }

    public static void registerPaymentMethod(String token){
        String jsonBody = """
            {
                "id": 1,
                "typePayment": "Cartão de Crédito"
            }
            """;
        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/payments-method");
    }

    public static void registerItemProductTwo(String token) {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of("quantity", "1"))
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/carts/products/2");
    }

    public static void registerItemProductOne(String token) {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of("quantity", "5"))
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/carts/products/1");
    }

    public static void registerProductTwo(String token) {
        given()
                .header("Authorization", "Bearer "+ token)
                .param("idCategory", "2")
                .multiPart("description", "Pizza")
                .multiPart("details", "Pizza")
                .multiPart("price", "20.00")
                .multiPart("available", true)
                .multiPart("highlight", false)
                .multiPart("file", new File("./src/test/resources/storage/test-controller.jpg"))
                .contentType("multipart/form-data")
                .when()
                .post("/api/products");
    }

    public static void registerProductOne(String token) {
        given()
                .header("Authorization", "Bearer "+ token)
                .param("idCategory", "1")
                .multiPart("description", "Lemon soda")
                .multiPart("details", "Lemon soda")
                .multiPart("price", "10.00")
                .multiPart("available", true)
                .multiPart("highlight", false)
                .multiPart("file", new File("./src/test/resources/storage/test-controller.jpg"))
                .contentType("multipart/form-data")
                .when()
                .post("/api/products");
    }

    public static void registerCategoryTwo(String token) {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of("name", "Food"))
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/categories");
    }

    public static void registerCategoryOne(String token) {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of("name", "Drinks"))
                .header("Authorization", "Bearer "+ token)
                .when()
                .post("/api/categories");
    }

    public static String login(Map<String, Object> user) {
        return given()
                .contentType(ContentType.JSON)
                .body(Map.of("email", user.get("email"), "password", user.get("password")))
                .when()
                .post("/api/login")
                .then()
                .extract()
                .path("token");

    }

    public static String saveUserDeliveryManAndReturnToken1(){
        var user = MockUser.reqUserDeliveryLogin1();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/users");

        return login(Map.of("email", user.get("email"), "password", user.get("password")));
    }

    public static String saveUserChefAndReturnToken1(){
        var user = MockUser.reqUserChef();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/users");

        return login(Map.of("email", user.get("email"), "password", user.get("password")));
    }

    public static String saveUserDeliveryManAndReturnToken2(){
        var user = MockUser.reqUserDeliveryLogin2();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/users");

        return login(Map.of("email", user.get("email"), "password", user.get("password")));
    }

    public static String saveUserCommon1AndReturnToken(){
        var user = MockUser.reqCommonUserValidPost();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/users");

        return login(Map.of("email", user.get("email"), "password", user.get("password")));
    }

    public static String saveUserCommon2AndReturnToken(){
        var user = MockUser.reqCommonUserValidPost2();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/users");

        return login(Map.of("email", user.get("email"), "password", user.get("password")));
    }

}
