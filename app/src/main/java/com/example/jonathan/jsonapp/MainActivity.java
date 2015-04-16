package com.example.jonathan.jsonapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class MainActivity extends ActionBarActivity {

    String jsonString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String method = "myMethod";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("recipient", "Penny Adams");
        params.put("amount", 175.05);
        String id = "0";
    }

    public void sendPost(View button) throws Exception {
        Thread c = new Thread() {
            @Override
            public void run() {
                InputStream inputStream = null;
                String result = "";
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://193.147.49.146:8080");
                    Log.d("etiqueta",httpClient.toString());
                    StringEntity se = new StringEntity(jsonString);
                    //StringEntity se = new StringEntity("pepeito");
                    httpPost.setEntity(se);
                    httpPost.setHeader("Accept", "application/json");
                    httpPost.setHeader("Content-type", "application/json");
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    inputStream = httpResponse.getEntity().getContent();

                    if (inputStream != null) {
                        result = "OK";
                        String line;
                        while(true) {
                            BufferedReader inBuff = new BufferedReader(new InputStreamReader(inputStream));
                            line = inBuff.readLine();
                            Log.d("etiqueta", line);
                        }
                    }else
                        result = "Did not work!";

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("etiqueta", "Imposible conectar con el servidor");

                }
                Log.d("etiqueta", result);
            }
        };
        c.start();
        try {
            c.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}