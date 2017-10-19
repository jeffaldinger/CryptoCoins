package com.example.cryptocoins;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.GridLabelRenderer;


public class UserAreaActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint>series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Home");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        GridLabelRenderer gridLabelRenderer = graph.getGridLabelRenderer();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
        series.setColor(Color.rgb(30, 197, 3));
        graph.getGridLabelRenderer().setGridColor(Color.rgb(110,143,128));
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.rgb(110,143,128));
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.rgb(110,143,128));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_area, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
