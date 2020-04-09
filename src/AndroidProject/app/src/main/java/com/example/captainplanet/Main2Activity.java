package com.example.captainplanet;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.view.MotionEvent;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    WebView wv1,wv2,wv3,wv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Evolutions des résultats");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);

        wv4 = findViewById(R.id.webview4);
        wv4.setInitialScale(250);
        wv4.getSettings().setJavaScriptEnabled(true);
        wv4.getSettings().setLoadWithOverviewMode(true);
        wv4.getSettings().setUseWideViewPort(true);
        wv4.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        wv1 = findViewById(R.id.webview1);
        wv1.setInitialScale(250);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setLoadWithOverviewMode(true);
        wv1.getSettings().setUseWideViewPort(true);
        wv1.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        wv2 = findViewById(R.id.webview2);
        wv2.setInitialScale(250);
        wv2.getSettings().setJavaScriptEnabled(true);
        wv2.getSettings().setLoadWithOverviewMode(true);
        wv2.getSettings().setUseWideViewPort(true);
        wv2.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        wv3 = findViewById(R.id.webview3);
        wv3.setInitialScale(250);
        wv3.getSettings().setJavaScriptEnabled(true);
        wv3.getSettings().setLoadWithOverviewMode(true);
        wv3.getSettings().setUseWideViewPort(true);
        wv3.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        wv1.loadUrl("https://thingspeak.com/channels/1022022/charts/2?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");
        wv2.loadUrl("https://thingspeak.com/channels/1022022/charts/3?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");
        wv3.loadUrl("https://thingspeak.com/channels/1022022/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");
        wv4.loadUrl("https://thingspeak.com/channels/1022022/maps/channel_show");

    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
