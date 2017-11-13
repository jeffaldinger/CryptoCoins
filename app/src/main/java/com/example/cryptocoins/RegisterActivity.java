package com.example.cryptocoins;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Please Enter Your Information:");
        final EditText etFirstName = (EditText) findViewById(R.id.etFirstName);
        final EditText etLastName = (EditText) findViewById(R.id.etLastName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etConfirmEmail = (EditText) findViewById(R.id.etConfirmEmail);
        final TextView termsLink = (TextView) findViewById(R.id.tvTerms);
        final Button bSignUp = (Button) findViewById(R.id.bSignUp);
        final CheckBox cbTerms = (CheckBox) findViewById(R.id.cbTerms);



        termsLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent termsIntent = new Intent(RegisterActivity.this, TermsOfService.class);
                RegisterActivity.this.startActivity(termsIntent);
            }
        });

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                final String first_name = etFirstName.getText().toString();
                final String last_name = etLastName.getText().toString();
                final String email = etEmail.getText().toString();
                final String confirmEmail = etConfirmEmail.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String confirmPassword = etConfirmPassword.getText().toString();
                //This is initializes the registry error builder
                final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                //This initializes the registry error string to empty
                String errorString = "";
                //If the passwords don't match, it adds a new line to the error string
                if(!Objects.equals(password, confirmPassword)){
                    errorString+="The passwords don't match.\n";
                }
                //If the emails don't match, it adds a new line to the error string
                if(!Objects.equals(email, confirmEmail)){
                    errorString+="The emails don't match.\n";
                }
                //If the TOS agreement box isn't checked, it adds a new line to the error string
                if(!cbTerms.isChecked()){
                    errorString+="You must agree to the terms.\n";
                }
                //The error string is now put into a final variable so that it can be accessed in the
                //new method below
                final String finalErrorString = errorString;
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            //This looks for a response from JSON, from the php file, similar to how
                            //it can look for a click of a button from a user.
                            JSONObject jsonResponse = new JSONObject(response);
                            //The success boolean is in the php file and is true if connection to the
                            //database is successful
                            boolean success = jsonResponse.getBoolean("success");
                            //If it's not successful, but all other conditions are met, it will give a
                            //generic error message. Otherwise, it will give the reasons why it failed,
                            //which come from the errorstring which was created above.
                            if(!success | finalErrorString!=""){
                                //This whole block of code is how to make a popup error/alert message
                                builder.setMessage("Registry failed.\n"+ finalErrorString)
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();


                            }else{
                                //If everything is successful, and inputs verified, it will change to the
                                //login page
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);

                            }

                            //This try/catch block will catch a JSON error
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };
                //This is where the RegisterRequest class comes in. It makes a new register request object
                //and puts the registry input fields into it, including the response listener created above
                RegisterRequest registerRequest = new RegisterRequest(first_name, last_name, email, username, password, responseListener);
                //Makes a new queue for the registration, and adds the request to it.
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }

        });
    }
}
