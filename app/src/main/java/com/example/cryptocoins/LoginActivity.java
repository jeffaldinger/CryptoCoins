package com.example.cryptocoins;
import android.support.v7.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Please Sign In or Register");

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button loginLink = (Button) findViewById(R.id.bSignIn);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegister);
        //final TextView loginLink = (TextView) findViewById(R.id.bSignIn);
        //final ProgressBar spinner = (ProgressBar)findViewById(R.id.progressBar);

        registerLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });


        /*loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginActivity.this, UserAreaActivity.class);
                LoginActivity.this.startActivity(loginIntent);
            }
        });*/

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String first_name = jsonResponse.getString("first_name");
                                String last_name = jsonResponse.getString("last_name"); //
                                String email = jsonResponse.getString("email");
                                int balance = jsonResponse.getInt("balance");

                                Intent loginIntent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                loginIntent.putExtra("username", username);
                                loginIntent.putExtra("balance", balance); //
                                loginIntent.putExtra("first_name", first_name);
                                loginIntent.putExtra("last_name", last_name);
                                loginIntent.putExtra("email", email);
                                LoginActivity.this.startActivity(loginIntent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(LoginActivity.this, AboutUs.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

