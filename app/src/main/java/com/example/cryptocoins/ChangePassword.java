package com.example.cryptocoins;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;


public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("Please Enter Information Below:");
        final EditText OldPass = (EditText) findViewById(R.id.OldPass);
        final EditText NewPass = (EditText) findViewById(R.id.NewPass);
        final Button ChangePass = (Button) findViewById(R.id.PassChangeButton);



        ChangePass.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                final String OPassword = OldPass.getText().toString();
                final String NPassword = NewPass.getText().toString();
                //This is initializes the registry error builder
                final AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
                String errorString = "";

                if (Objects.equals(OldPass, NewPass)) {
                    errorString += "The passwords are already the same.\n";
                }
                final String finalErrorString = errorString;
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                            if (!success | finalErrorString != ""){
                                //This whole block of code is how to make a popup error/alert message
                                builder.setMessage("Password change failed.\n" + finalErrorString)
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();


                            } else {
                                //If everything is successful, and inputs verified, it will change to the
                                //login page
                                Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                                ChangePassword.this.startActivity(intent);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };

                PasswordRequest passwordRequest = new PasswordRequest(OPassword, NPassword, responseListener);
                //Makes a new queue for the registration, and adds the request to it.
                RequestQueue queue = Volley.newRequestQueue(ChangePassword.this);
                queue.add(passwordRequest);
            }
        });
    }
}

