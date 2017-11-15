package com.example.cryptocoins;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.GridLabelRenderer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;




public class UserAreaActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    private DrawerLayout menuDrawerLayout;
    private ActionBarDrawerToggle menuToggle;
    //
    private String TAG = UserAreaActivity.class.getSimpleName();
    ArrayList<Double> bitcoinValueList;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Home");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        menuDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        menuToggle = new ActionBarDrawerToggle(this, menuDrawerLayout, R.string.open, R.string.close);

        menuDrawerLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();
        //
        bitcoinValueList = new ArrayList<>();

        Button toWallet = (Button) findViewById(R.id.buttonWallet);
        toWallet.setOnClickListener(new View.OnClickListener() {
        new GetBitcoinValues().execute();
        //

            @Override
            public void onClick(View view) {

                Intent walletIntent = new Intent(UserAreaActivity.this, WalletActivity.class);
                startActivity(walletIntent);
            }


        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.menu_user_area);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case (R.id.menu_settings):
                        Intent settingsActivity = new Intent(getApplicationContext(), SettingsArea.class);
                        startActivity(settingsActivity);
                        break;
                    case (R.id.menu_about_us):
                        Intent aboutActivity = new Intent(getApplicationContext(), AboutUs.class);
                        startActivity(aboutActivity);
                        break;
                    case (R.id.menu_terms):
                        Intent termsActivity = new Intent(getApplicationContext(), TermsOfService.class);
                        startActivity(termsActivity);
                        break;
                    case (R.id.menu_logout):
                        Intent logoutActivity = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(logoutActivity);
                        break;
                }
                return true;
            }
        });
        /*
        GraphView homeGraph = (GraphView) findViewById(R.id.homeGraph);
        GridLabelRenderer gridLabelRenderer = homeGraph.getGridLabelRenderer();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, day1),
                new DataPoint(1, day2),
                new DataPoint(2, day3),
                new DataPoint(3, day4),
                new DataPoint(4, day5),
                new DataPoint(5, day6),
                new DataPoint(6, day7)
        });
        homeGraph.addSeries(series);
        series.setColor(Color.rgb(30, 197, 3));
        homeGraph.getGridLabelRenderer().setGridColor(Color.rgb(110,143,128));
        homeGraph.getGridLabelRenderer().setVerticalLabelsColor(Color.rgb(110,143,128));
        homeGraph.getGridLabelRenderer().setHorizontalLabelsColor(Color.rgb(110,143,128));
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //

    private class GetBitcoinValues extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(UserAreaActivity.this, "Getting market information", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh1 = new HttpHandler();
            // Making a request to url and getting response
            String historicUrl = "https://api.coindesk.com/v1/bpi/historical/close.json";
            String jsonStr = sh1.makeServiceCall(historicUrl);

            //This is for HISTORIC data

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONObject bpi = jsonObj.getJSONObject("bpi");
                    int bpiLength = bpi.length();
                    JSONArray dateList = bpi.names();
                    String date1 = dateList.getString(bpiLength - 7);
                    String date2 = dateList.getString(bpiLength - 6);
                    String date3 = dateList.getString(bpiLength - 5);
                    String date4 = dateList.getString(bpiLength - 4);
                    String date5 = dateList.getString(bpiLength - 3);
                    String date6 = dateList.getString(bpiLength - 2);
                    String date7 = dateList.getString(bpiLength - 1);

                    bitcoinValueList.add(bpi.getDouble(date1));
                    bitcoinValueList.add(bpi.getDouble(date2));
                    bitcoinValueList.add(bpi.getDouble(date3));
                    bitcoinValueList.add(bpi.getDouble(date4));
                    bitcoinValueList.add(bpi.getDouble(date5));
                    bitcoinValueList.add(bpi.getDouble(date6));
                    bitcoinValueList.add(bpi.getDouble(date7));

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            HttpHandler sh2 = new HttpHandler();
            // Making a request to url and getting response
            String currentUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";
            String currentJsonStr = sh2.makeServiceCall(currentUrl);

            //This is for CURRENT data

            Log.e(TAG, "Response from url: " + currentJsonStr);
            if (currentJsonStr != null) {
                try {
                    JSONObject currentJsonObj = new JSONObject(currentJsonStr);

                    // Getting JSON Object node
                    JSONObject jsonBPI = currentJsonObj.getJSONObject("bpi");
                    //JSONObject jsonBPI = jsonTime.getJSONObject("bpi");
                    JSONObject jsonUSD = jsonBPI.getJSONObject("USD");
                    bitcoinValueList.add(jsonUSD.getDouble("rate_float"));

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            GraphView homeGraph = (GraphView) findViewById(R.id.homeGraph);
            GridLabelRenderer gridLabelRenderer = homeGraph.getGridLabelRenderer();
            gridLabelRenderer.setHorizontalAxisTitle("Days of the Week");
            gridLabelRenderer.setVerticalAxisTitle("USD");
            gridLabelRenderer.setHorizontalAxisTitleColor(Color.rgb(110,143,128));
            gridLabelRenderer.setVerticalAxisTitleColor(Color.rgb(110,143,128));
            gridLabelRenderer.setHorizontalAxisTitleTextSize(64);
            gridLabelRenderer.setVerticalAxisTitleTextSize(64);
            gridLabelRenderer.setHorizontalLabelsVisible(false);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                    new DataPoint(0, bitcoinValueList.get(0)),
                    new DataPoint(1, bitcoinValueList.get(1)),
                    new DataPoint(2, bitcoinValueList.get(2)),
                    new DataPoint(3, bitcoinValueList.get(3)),
                    new DataPoint(4, bitcoinValueList.get(4)),
                    new DataPoint(5, bitcoinValueList.get(5)),
                    new DataPoint(6, bitcoinValueList.get(6)),
                    new DataPoint(7, bitcoinValueList.get(7))
            });
            homeGraph.addSeries(series);
            series.setColor(Color.rgb(30, 197, 3));
            homeGraph.getGridLabelRenderer().setGridColor(Color.rgb(110, 143, 128));
            homeGraph.getGridLabelRenderer().setVerticalLabelsColor(Color.rgb(110, 143, 128));
            homeGraph.getGridLabelRenderer().setHorizontalLabelsColor(Color.rgb(110, 143, 128));
        }
    }
}
