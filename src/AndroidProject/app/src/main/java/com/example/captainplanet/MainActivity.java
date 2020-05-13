package com.example.captainplanet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

//import io.reactivex.Scheduler;

public class MainActivity extends AppCompatActivity
{
    CountDownTimer countDownTimer;


    TextView air_quality_text,temperature_text,humidity_text,ppm_indicator,h_indicator,f_indicator,c_indicator,temperature_text_f;
    Button b1,b2;
    androidx.cardview.widget.CardView danger_level_card;
    long timePeriod  =3000;
    ImageView death_image,safety_image,harard_image;
    TextView level_indicator;
    ProgressBar progress_bar,ppm_progress_bar,temp_progress_bar ,humi_progress_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b2=findViewById(R.id.button2);

        loadWidgets();


       WorkManager mWorkManager = WorkManager.getInstance();
       PeriodicWorkRequest mPerdiodicWorkRequest =new  PeriodicWorkRequest.Builder(NotificationsWorker.class,15, TimeUnit.MINUTES).build();
       mWorkManager.enqueue(mPerdiodicWorkRequest);
         usingCountDownTimer();

    }


    public void usingCountDownTimer() {
        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 3000) {

            // This is called after every 10 sec interval.
            public void onTick(long millisUntilFinished) {
                getAirQualityRecord();
                getHumidityRecord();
                getTemperatureRecord();
            }

            public void onFinish() {
                start();
            }
        }.start();
    }
    public void loadWidgets(){


         air_quality_text=findViewById(R.id.air_quality_text);
         temperature_text=findViewById(R.id.temperature_text);
         temperature_text_f=findViewById(R.id.temperature_text_f);
         humidity_text=findViewById(R.id.humidity_text);


        ppm_indicator =findViewById(R.id.ppm_indicator);
        ppm_indicator.setVisibility(View.INVISIBLE);
        c_indicator =findViewById(R.id.c_indicator);
        c_indicator.setVisibility(View.INVISIBLE);
        f_indicator=findViewById(R.id.f_indicator);
        f_indicator.setVisibility(View.INVISIBLE);
        h_indicator=findViewById(R.id.h_indicator);
        h_indicator.setVisibility(View.INVISIBLE);

        level_indicator = findViewById(R.id.safety_level_text);

        progress_bar =findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.VISIBLE);
        ppm_progress_bar =findViewById(R.id.ppm_progress_bar);
        ppm_progress_bar.setVisibility(View.VISIBLE);
        temp_progress_bar =findViewById(R.id.temp_bar);
        temp_progress_bar.setVisibility(View.VISIBLE);
        humi_progress_bar=findViewById(R.id.humid_bar);
        humi_progress_bar.setVisibility(View.VISIBLE);




        danger_level_card = findViewById(R.id.safety_level_card);
        death_image=findViewById(R.id.death_image);
        safety_image=findViewById(R.id.safe_image);

        death_image.setVisibility(View.INVISIBLE);
        safety_image.setVisibility(View.INVISIBLE);
        danger_level_card.setCardBackgroundColor(getResources().getColor(R.color.White));




        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    public void setSafetyLevel(){
        progress_bar.setVisibility(View.INVISIBLE);
        danger_level_card.setCardBackgroundColor(getResources().getColor(R.color.Safe));
        death_image.setVisibility(View.INVISIBLE);
        safety_image.setVisibility(View.VISIBLE);
        level_indicator.setText("Safe");


    }
    public void setDangerousLevel(){
        progress_bar.setVisibility(View.INVISIBLE);
        danger_level_card.setCardBackgroundColor(getResources().getColor(R.color.Dangerous));
        death_image.setVisibility(View.VISIBLE);
        safety_image.setVisibility(View.INVISIBLE);
        level_indicator.setText("Dangerous");

    }
    public void setModerateDangerLevel(){

    }
    public void setHazardLevel(){

    }



    public void getAirQualityfeeds(){
        String channel_air_quality_url = "https://api.thingspeak.com/channels/1022022/fields/1.json?results=2";
        JsonArrayRequest mRequest = new JsonArrayRequest(Request.Method.GET, channel_air_quality_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

              Toast.makeText(getApplicationContext(),response+"",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    //json object version
    public void getAirQuality(){
    String channel_air_quality_url = "https://api.thingspeak.com/channels/1022022/fields/1.json?results=2";
    RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
    JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, channel_air_quality_url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONArray feeds = response.getJSONArray("feeds");
                        Toast.makeText(getApplicationContext(), ""+feeds, Toast.LENGTH_SHORT).show();
                        JSONObject jojo ;
                        for(int i=0;i<feeds.length();i++){
                            jojo = feeds.getJSONObject(i);
                            if(!jojo.getString("field1").equals("null")){
                                //update the view
                                air_quality_text.setText(jojo.getString("field1"));
                                Toast.makeText(getApplicationContext(),"Air quality  ="+jojo.getString("field1"),Toast.LENGTH_LONG).show();}
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"exception message :"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
    );
    mQueue.add(mRequest);
    mQueue.start();

    }
    public void getTemperature(){
        String channel_temperature_url = "https://api.thingspeak.com/channels/1022022/fields/2.json?results=2";
        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, channel_temperature_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray feeds = response.getJSONArray("feeds");
                            JSONObject jojo ;
                            for(int i=0;i<feeds.length();i++){
                                jojo = feeds.getJSONObject(i);
                                if(!jojo.getString("field2").equals("null")){
                                    //update the view
                                    temperature_text.setText(jojo.getString("field2"));
                                    //Toast.makeText(getApplicationContext(),"temperature  ="+jojo.getString("field2"),Toast.LENGTH_LONG).show();
                                }
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"exception message :"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        mQueue.add(mRequest);
        mQueue.start();
    }
    public void getHumidity(){
        String channel_humidity_url = "https://api.thingspeak.com/channels/1022022/fields/3.json?results=2";
        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, channel_humidity_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray feeds = response.getJSONArray("feeds");
                            JSONObject jojo ;
                            for(int i=0;i<feeds.length();i++){
                                jojo = feeds.getJSONObject(i);
                                if(!jojo.getString("field3").equals("null")){
                                    //update the view
                                    humidity_text.setText(jojo.getString("field3"));
                                   // Toast.makeText(getApplicationContext(),"Humidity  ="+jojo.getString("field3"),Toast.LENGTH_LONG).show();
                                    }
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"exception message :"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        mQueue.add(mRequest);
        mQueue.start();
    }

    public void getThingspeakData() {

        String channel_url = "https://api.thingspeak.com/channels/1022022/fields/3.json?results=2";
        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,channel_url,null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray feeds = response.getJSONArray("feeds");
                            JSONObject jojo ;
                            for(int i=0;i<feeds.length();i++){
                                jojo = feeds.getJSONObject(i);
                                if(!jojo.getString("field3").equals("null")){
                                Toast.makeText(getApplicationContext(),"Air quality  ="+jojo.getString("field3"),Toast.LENGTH_LONG).show();}
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"exception message :"+e.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(),"error message :"+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        );

        mQueue.add(request);
        mQueue.start();
    }


    public void clickResultat(View v)
    {
        getThingspeakData();
    }

    public void clickEvolution(View v)
    {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public float convertFromCelcius(float c){
     return 0 ;
    }

    //string request version
    public void getAirQualityRecord(){

        String channel_air_quality_url = "https://api.thingspeak.com/channels/1022022/fields/1.json";
        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest mRequest = new StringRequest(Request.Method.GET, channel_air_quality_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray array=object.getJSONArray("feeds");
                    Log.d("feeds",""+array);
                     for(int i=array.length()-1;i>=0;i--) {
                        JSONObject object1=array.getJSONObject(i);
                        if(!object1.getString("field1").equals("null")){
                            float f = Float.parseFloat(object1.getString("field1"));

                            ppm_progress_bar.setVisibility(View.INVISIBLE);
                            air_quality_text.setText(new DecimalFormat("###.###").format(f));
                            ppm_indicator.setVisibility(View.VISIBLE);


                            if(f <3){
                             setSafetyLevel();
                            }else if (f >3){
                                setDangerousLevel();
                            }
                          
                            break ;
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

    mQueue.add(mRequest);
    mQueue.start();
    }
    public void getTemperatureRecord(){
        String channel_temperature_url = "https://api.thingspeak.com/channels/1022022/fields/2.json";
        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest mRequest = new StringRequest(Request.Method.GET, channel_temperature_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray array=object.getJSONArray("feeds");
                    Log.d("feeds",""+array);
                    for(int i=array.length()-1;i>=0;i--) {
                        JSONObject object1=array.getJSONObject(i);
                        if(!object1.getString("field2").equals("null")){
                            float f = Float.parseFloat(object1.getString("field2"));
                            temp_progress_bar.setVisibility(View.INVISIBLE);
                            temperature_text.setText(new DecimalFormat("###.###").format(f));
                            c_indicator.setVisibility(View.VISIBLE);
                            f = Float.parseFloat(fromCelciusToFeh(Float.parseFloat(object1.getString("field2"))));
                            temperature_text_f.setText(new DecimalFormat("###.###").format(f));
                            f_indicator.setVisibility(View.VISIBLE);
                            if(f<10 || f>30){
                                setDangerousLevel();
                            }
                            break ;
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        mQueue.add(mRequest);
        mQueue.start();

    }
    public void getHumidityRecord(){
        String channel_humidity_url = "https://api.thingspeak.com/channels/1022022/fields/3.json";
        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest mRequest = new StringRequest(Request.Method.GET, channel_humidity_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray array=object.getJSONArray("feeds");
                    Log.d("feeds",""+array);
                    for(int i=array.length()-1;i>=0;i--) {
                        JSONObject object1=array.getJSONObject(i);
                        if(!object1.getString("field3").equals("null")){
                            float f = Float.parseFloat(object1.getString("field3"));
                            humi_progress_bar.setVisibility(View.INVISIBLE);
                            humidity_text.setText(new DecimalFormat("###.###").format(f));
                            h_indicator.setVisibility(View.VISIBLE);
                            if(f<30 || f>50){
                                setDangerousLevel();
                            }
                            break ;
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        mQueue.add(mRequest);
        mQueue.start();

    }

    //update the danger level
    public String fromCelciusToFeh(float c){
        return ""+(((c*9)/5)+32);
    }
    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onStart(){
        super.onStart();
    }
    
    @Override
    public void onPause(){
        super.onPause();
        try{
            countDownTimer.cancel();
        }catch (Exception idk){
            idk.printStackTrace();

        }
    }

 public class NotificationsService extends IntentService{
        public NotificationsService (String name){
            super("my worker service");
        }
     @Override
     public IBinder onBind(Intent intent) {
         return null;
     }
     @Override
     public int onStartCommand(Intent intent, int flags, int startId) {

         return super.onStartCommand(intent, flags, startId);
     }
        @Override
        protected void onHandleIntent(Intent myIntent){

          synchronized (this){
              usingCountDownTimer();
          }
        }

 }
    public void startService(){
        Intent mIntent= new Intent(getApplicationContext(),NotificationsService.class);
        startService(mIntent);
    }
}
