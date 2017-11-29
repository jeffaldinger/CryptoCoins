package com.example.cryptocoins;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PasswordRequest extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://crypto-coins.000webhostapp.com/Register.php";
    //This initializes the "params" variable which is used in the php file
    private Map<String, String> params;
    //Here is the constructor for our RegisterRequest class, which is used in the RegisterActivity
    public PasswordRequest(String OPassword, String NPassword, Response.Listener<String> listener){

        super(Method.POST,REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("password", OPassword);
        params.put("password", NPassword);
        ;
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

