package com.example.cryptocoins;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;


public class RegisterRequest extends StringRequest {
    //This accesses the registry php file which accesses the database
    private static final String REGISTER_REQUEST_URL = "http://crypto-coins.000webhostapp.com/Register.php";
    //This initializes the "params" variable which is used in the php file
    private Map<String, String> params;
    //Here is the constructor for our RegisterRequest class, which is used in the RegisterActivity
    public RegisterRequest(String first_name, String last_name, String email, String username, String password, Response.Listener<String> listener){

        super(Method.POST,REGISTER_REQUEST_URL, listener, null);
        //Putting all of the values into the params variable, to be read by the php file
        //the value *in the quotes* refers to a variable of that name in the php file
        //but the value which isn't in quotes is the one we have in android studio.
        //This basically just puts our android value into the php variable that exists in params.
        params = new HashMap<>();
        params.put("first_name", first_name);
        params.put("last_name", last_name);
        params.put("email", email);
        params.put("username", username);
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
