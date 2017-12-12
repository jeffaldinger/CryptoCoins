package com.example.cryptocoins;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.id.message;

public class WalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        setTitle("Wallet");

        TextView wid = (TextView)findViewById(R.id.wid);
        wid.setText("1EzwoHtiXB4iFwedPr49iywjZn2nnekhoj");
       // KwjouFjeF25SmwHvhCv3AWxehktTDPaiX2rmtcL9TE7DdnjCnuRT
        final ProgressDialog a = new ProgressDialog(this);
        a.show();
        a.setMessage("getting data");


        ////YOU CAN SET A CUSTOM WALLET ID FROM SETTINGS WITH SHAREDPREFS AND PULL THE VALUE HERE AND IN USER AREA
        SharedPreferences sp = getSharedPreferences("DB",MODE_PRIVATE);

        RequestQueue queue =
                Volley.newRequestQueue(this);
        queue.add(new StringRequest(Request.Method.GET,
                "https://blockchain.info/q/addressbalance/1EzwoHtiXB4iFwedPr49iywjZn2nnekhoj",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
a.hide();
                        TextView balance = (TextView)findViewById(R.id.balance);
                        balance.setText(
                                String.valueOf(Float.parseFloat(String.valueOf(Float.parseFloat((response))/1000000000.0f)))
                                        + " BTC"
                        );

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
a.hide();
            }
        }));
        final ArrayList<String> transations = new ArrayList<String>();

        queue.add(new JsonObjectRequest(Request.Method.GET,
                "https://blockchain.info/rawaddr/1EzwoHtiXB4iFwedPr49iywjZn2nnekhoj",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       // a.hide();
try{
    TextView r = (TextView)findViewById(R.id.received);
    TextView s = (TextView)findViewById(R.id.sent);
    r.setText (response.get("total_received").toString());
    s.setText (response.get("total_sent").toString());
   for( int i = 0; i< ((JSONArray)response.get("txts")).length(); i++){

   }
}
catch (Exception  ss){

}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                a.hide();
            }
        }));
    }
}
