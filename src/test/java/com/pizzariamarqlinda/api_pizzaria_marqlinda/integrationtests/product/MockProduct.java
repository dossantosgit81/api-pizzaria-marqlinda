package com.pizzariamarqlinda.api_pizzaria_marqlinda.integrationtests.product;

import java.util.HashMap;
import java.util.Map;

public class MockProduct {
    public static Map<String, Object> reqInvalidFields(){
        Map<String, Object> req = new HashMap<>();
        req.put("description", "");
        req.put("details", "");
        req.put("price", null);
        return req;
    }
}
