package com.example.cryptocoins;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by JP on 10/15/17.
 */


public class SettingsArea extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setTitle("Settings Area");
        setContentView(R.layout.settings_area);

        final Button IDLink = (Button) findViewById(R.id.WalletID);

        IDLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IDIntent = new Intent(SettingsArea.this, Pop.class);
                SettingsArea.this.startActivity(IDIntent);
            }
        });

    }
}
