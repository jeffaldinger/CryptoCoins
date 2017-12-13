package com.example.cryptocoins;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddWalletID extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addwalletid);

        final EditText wid = (EditText)findViewById(R.id.wid);
        Button update = (Button)findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(wid.getText().equals("") || wid.getText().length()<20){
                    Toast.makeText(AddWalletID.this,"Please enter a valid wallet id",Toast.LENGTH_LONG).show();
                }
                else{SharedPreferences sp = getSharedPreferences("DB",MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("WALLETID",wid.getText().toString());
                    ed.commit();
                    startActivity(new Intent(AddWalletID.this,UserAreaActivity.class));

                    finish();}
            }
        });
    }
}