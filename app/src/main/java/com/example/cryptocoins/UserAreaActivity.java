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

import static android.R.id.message;


public class UserAreaActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint>series;
    private DrawerLayout menuDrawerLayout;
    private ActionBarDrawerToggle menuToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Home");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        menuDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        menuToggle = new ActionBarDrawerToggle(this, menuDrawerLayout, R.string.open, R.string.close);

        menuDrawerLayout.addDrawerListener(menuToggle);
        menuToggle.syncState();

        Button toWallet = (Button) findViewById(R.id.buttonWallet);
        toWallet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent walletIntent = new Intent(UserAreaActivity.this, WalletActivity.class);
                startActivity(walletIntent);
            }


        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.menu_user_area);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem){
                switch (menuItem.getItemId()){
                    case(R.id.menu_settings):
                        Intent settingsActivity = new Intent(getApplicationContext(), SettingsArea.class);
                        startActivity(settingsActivity);
                        break;
                    case(R.id.menu_about_us):
                        Intent aboutActivity = new Intent(getApplicationContext(), AboutUs.class);
                        startActivity(aboutActivity);
                        break;
                    case(R.id.menu_terms):
                        Intent termsActivity = new Intent(getApplicationContext(), TermsOfService.class);
                        startActivity(termsActivity);
                        break;
                    case(R.id.menu_logout):
                        Intent logoutActivity = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(logoutActivity);
                        break;
                }
                return true;
            }
        });

        GraphView homeGraph = (GraphView) findViewById(R.id.homeGraph);
        GridLabelRenderer gridLabelRenderer = homeGraph.getGridLabelRenderer();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        homeGraph.addSeries(series);
        series.setColor(Color.rgb(30, 197, 3));
        homeGraph.getGridLabelRenderer().setGridColor(Color.rgb(110,143,128));
        homeGraph.getGridLabelRenderer().setVerticalLabelsColor(Color.rgb(110,143,128));
        homeGraph.getGridLabelRenderer().setHorizontalLabelsColor(Color.rgb(110,143,128));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(menuToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_area, menu);
        return super.onCreateOptionsMenu(menu);
    }
*/
}
