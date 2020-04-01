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

    WebView wv1,wv2,wv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Evolutions des résultats");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

        wv1.loadUrl("https://thingspeak.com/channels/54807/charts/1?&results=60&dynamic=true&title=Type%20%3A%20line");
        wv2.loadUrl("https://thingspeak.com/channels/54807/charts/2?&results=60&dynamic=true&title=Type%20%3A%20line");
        wv3.loadUrl("https://thingspeak.com/channels/54807/charts/3?&results=60&dynamic=true&title=Type%20%3A%20line");
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
