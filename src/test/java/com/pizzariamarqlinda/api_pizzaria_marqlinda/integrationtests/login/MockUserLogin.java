package com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.login;

import java.util.HashMap;
import java.util.Map;

public class MockUserLogin {
    public static Map<String, Object> reqInvalidFields(){
        Map<String, Object> req = new HashMap<>();
        req.put("email", "");
        req.put("password", "");
        return req;
    }
}
