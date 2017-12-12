package com.example.cryptocoins;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;


import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RelatedNewsActivity extends AppCompatActivity{
    ArrayList<String> titleList;

    //private ArrayAdapter<NewsArticles> articleAdapter;

    ArrayList<NewsArticles> articleArray;
    private String TAG = RelatedNewsActivity.class.getSimpleName();

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.related_news);
        setTitle("Crypto News");
        //ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.list_item, titleList);
        new GetRelatedNews().execute();


    }

    public class NewsArticles{
        String author;
        String title;
        String description;
        String url;

    }

    private class GetRelatedNews extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        Toast.makeText(RelatedNewsActivity.this, "Getting news...", Toast.LENGTH_LONG).show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {

        HttpHandler httphand = new HttpHandler();
        // Making a request to url and getting response
        String historicUrl = "https://newsapi.org/v2/top-headlines?sources=crypto-coins-news&apiKey=3fe607ce542146458c7e6df53a71f609";
        String jsonStr = httphand.makeServiceCall(historicUrl);

        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                Gson gson = new GsonBuilder().create();
                // Getting JSON Array node
                JSONArray articles = jsonObj.getJSONArray("articles");

                //articleArray = gson.fromJson(articles.toString(), NewsArticles[].class);

                Type newsListType = new TypeToken<ArrayList<NewsArticles>>(){}.getType();

                articleArray = gson.fromJson(articles.toString(), newsListType);



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

        // Construct the data source
        ArrayList<NewsArticles> arrayOfNews = new ArrayList<>();
        // Create the adapter to convert the array to views

        ArticleAdapter adapter = new ArticleAdapter(getBaseContext(), articleArray);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.news_list);
        listView.setAdapter(adapter);
        }

    public class ArticleAdapter extends ArrayAdapter<NewsArticles> {
            public ArticleAdapter(Context context, ArrayList<NewsArticles> newsArticles) {
                super(context, 0, newsArticles);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // Get the data item for this position
                NewsArticles news = getItem(position);
                final String url = news.url;
                // Check if an existing view is being reused, otherwise inflate the view
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
                }
                // Lookup view for data population
                TextView tvTitle = (TextView) convertView.findViewById(R.id.txtTitle);
                TextView tvDescription = (TextView) convertView.findViewById(R.id.txtDescription);
                // Populate the data into the template view using the data object
                tvTitle.setText(news.title);
                if(news.description.length()>5) {
                    tvDescription.setText("\t"+news.description);
                }else{
                    tvDescription.setText("");
                }

                tvTitle.setMovementMethod(LinkMovementMethod.getInstance());
                tvTitle.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                        browserIntent.setData(Uri.parse(url));
                        startActivity(browserIntent);
                    }
                });
                // Return the completed view to render on screen
                return convertView;
            }

    }



    }
}






