package com.example.captainplanet;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class NotificationsWorker extends Worker {
    private String CHANNEL_ID = "555";
    public NotificationsWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        createNotificationChannel();

        //prepareNotifications();
    }

    public String fromCelciusToFeh(float c){
        return ""+(((c*9)/5)+32);
    }
    //string request version
    public void getAirQualityRecord(){

        String channel_air_quality_url = "https://api.thingspeak.com/channels/1022022/fields/1.json";
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
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

                            if(f > 2){
                              prepareNotifications(""+f,0);
                            }
                            //Toast.makeText(getApplicationContext(),""+object1.getString("field1"),Toast.LENGTH_LONG).show();
                            break ;
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                //Log.d("response",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(),"exception"+error.getMessage(),Toast.LENGTH_LONG).show();
                // Log.d("error", "onErrorResponse: ");;
            }
        });

        mQueue.add(mRequest);
        mQueue.start();
    }
    public void getTemperatureRecord(){
        String channel_temperature_url = "https://api.thingspeak.com/channels/1022022/fields/2.json";
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
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

                           Float  f =Float.parseFloat(object1.getString("field2"));

                           if(f<10){
                               prepareNotifications(""+f,1);
                           }else if (f>30){
                               prepareNotifications(""+f,1);
                           }
                            //Toast.makeText(getApplicationContext(),""+object1.getString("field1"),Toast.LENGTH_LONG).show();
                            break ;
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                //Log.d("response",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(),"exception"+error.getMessage(),Toast.LENGTH_LONG).show();
                // Log.d("error", "onErrorResponse: ");;
            }
        });

        mQueue.add(mRequest);
        mQueue.start();

    }
    public void getHumidityRecord(){
        String channel_humidity_url = "https://api.thingspeak.com/channels/1022022/fields/3.json";
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
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
                           String s=object1.getString("field3");
                            float f = Float.parseFloat(s);
                            if(f>50){
                                prepareNotifications(""+f,2);
                            }else if(f<30){
                                prepareNotifications(""+f,2);
                            }
                            //Toast.makeText(getApplicationContext(),""+object1.getString("field1"),Toast.LENGTH_LONG).show();
                            break ;
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                //Log.d("response",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(),"exception"+error.getMessage(),Toast.LENGTH_LONG).show();
                // Log.d("error", "onErrorResponse: ");;
            }
        });

        mQueue.add(mRequest);
        mQueue.start();

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "mChannel";
            String description = "who cares ffs";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager =getApplicationContext().getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void prepareNotifications(String text,int typeOfDanger){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        switch (typeOfDanger){
            case 0:
                //air quality

                        builder.setSmallIcon(R.drawable.green_death)
                        .setContentTitle("Air Quality Alert")
                        .setContentText("air quality level at :"+text)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("air quality level at :"+text))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent).setAutoCancel(true);
                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(0, builder.build());
                break;
            case 1:
                //temperature
                        builder.setSmallIcon(R.drawable.green_death)
                        .setContentTitle("Temperature Alert")
                        .setContentText("temperature level at: "+text)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("temperature level at: "+text))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent).setAutoCancel(true);
                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(1, builder.build());
                break;
            case 2:
                //humidity
                builder.setSmallIcon(R.drawable.green_death)
                        .setContentTitle("Humidity Alert")
                        .setContentText("humidity level at: "+text)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("humidity level at: "+text))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pendingIntent).setAutoCancel(true);
                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(2, builder.build());
                break;
        }


    }

    @Override
    public Result doWork() {

        getAirQualityRecord();
        getHumidityRecord();
        getTemperatureRecord();
        // Do the work here--in this case, upload the images.
        // usingCountDownTimer();
        //Toast.makeText(TimeL,"im in the background",Toast.LENGTH_SHORT).show();

        // Indicate whether the task finished successfully with the Result
        return Result.success();
    }
}
