package com.sgo.wowtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Content().execute();
    }

}


class Content extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //This is the Firebase URL where data will be fetched from
        String url = "https://www.wowhead.com";
        //Connect to website
        try {
            Document document = Jsoup.connect(url).get();
//            Log.d("MiInfo",document.html());

//            Elements info = document.select("div[class=tiw-group tiw-blocks-assault]");
            Elements info = document.select("script");
            String data;
            for (int i = 0; i < info.size(); i++) {
                if(info.eq(i).html().contains("WH.Assaults.addDisplay")){
                    data = info.eq(i).html();
                    data = data.replace("WH.Assaults.addDisplay(\"US\", ","");
                    data = data.replace("WH.Assaults.addDisplay(\"EU\", ","");
                    data = data.replace(");","");
                    JSONObject finalData = new JSONObject(data);
                    Log.d("info4",finalData.getString("upcoming"));
                };
            }
//            Log.d("MiInfo",info.html());
            //String tiempo = info.eq(0).select("span[class=tiw-blocks-status-progress-text]").text();
            //Log.d("Tiempo",tiempo);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}