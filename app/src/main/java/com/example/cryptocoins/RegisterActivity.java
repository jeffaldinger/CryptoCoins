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
                final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                String errorString = "";

                if(!Objects.equals(password, confirmPassword)){
                    errorString+="The passwords don't match.\n";
                }
                if(!Objects.equals(email, confirmEmail)){
                    errorString+="The emails don't match.\n";
                }
                if(!cbTerms.isChecked()){
                    errorString+="You must agree to the terms.\n";
                }
                final String finalErrorString = errorString;
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(!success | finalErrorString!=""){

                                builder.setMessage("Registry failed.\n"+ finalErrorString)
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();


                            }else{
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(first_name, last_name, email, username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }

        });
    }
}
