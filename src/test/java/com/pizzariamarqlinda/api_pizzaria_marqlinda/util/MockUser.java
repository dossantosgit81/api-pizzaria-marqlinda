package com.pizzariamarqlinda.api_pizzaria_marqlinda.util;

import java.util.HashMap;
import java.util.Map;

public class MockUser {

    public static Map<String, Object> reqCommonUserValidPost(){
        Map<String, Object> req = new HashMap<>();
        req.put("name", "John");
        req.put("lastName", "Travolta");
        req.put("email", "travolta@test.com");
        req.put("password", "12345678");
        req.put("phone", "77940028922");
        return req;
    }

    public static Map<String, Object> reqCommonUserValidPost2(){
        Map<String, Object> req = new HashMap<>();
        req.put("name", "Laercio");
        req.put("lastName", "Carlos");
        req.put("email", "carlos@test.com");
        req.put("password", "12345678");
        req.put("phone", "77940028922");
        return req;
    }

    public static Map<String, Object> reqCommonUserValidPost3(){
        Map<String, Object> req = new HashMap<>();
        req.put("name", "Brad");
        req.put("lastName", "Silva");
        req.put("email", "silva@test.com");
        req.put("password", "12345678");
        req.put("phone", "77940028922");
        return req;
    }

    public static Map<String, Object> reqValidPost4(){
        Map<String, Object> req = new HashMap<>();
        req.put("name", "Louis");
        req.put("lastName", "Armstrong");
        req.put("email", "louis@test.com");
        req.put("password", "12345678");
        req.put("phone", "77940028922");
        return req;
    }

    public static Map<String, Object> reqCommonUserValidPost5(){
        Map<String, Object> req = new HashMap<>();
        req.put("name", "Julio");
        req.put("lastName", "Lima");
        req.put("email", "julio@test.com");
        req.put("password", "12345678");
        req.put("phone", "77940028922");
        return req;
    }

    public static Map<String, Object> reqInvalidPost(){
        Map<String, Object> req = new HashMap<>();
        req.put("name", "Luis");
        req.put("lastName", "Suarez");
        req.put("email", "luis@test.com");
        req.put("password", "12345678");
        req.put("phone", "77940028922");
        return req;
    }

    public static Map<String, Object> reqInvalidFields(){
        Map<String, Object> req = new HashMap<>();
        req.put("null", null);
        req.put("null2", "Suarez");
        req.put("email", "luistest.com");
        req.put("password", "12345");
        req.put("phone", "40028922");
        return req;
    }

    public static Map<String, Object> reqUserAdminLogin(){
        Map<String, Object> req = new HashMap<>();
        req.put("email", "rafael@gmail.com");
        req.put("password", "adminuser");
        return req;
    }

    public static Map<String, Object> reqUserDeliveryLogin1(){
        Map<String, Object> req = new HashMap<>();
        req.put("email", "deliveryMan@gmail.com");
        req.put("password", "adminuser");
        return req;
    }

    public static Map<String, Object> reqUserDeliveryLogin2(){
        Map<String, Object> req = new HashMap<>();
        req.put("email", "delivery2Man@gmail.com");
        req.put("password", "adminuser");
        return req;
    }


}
