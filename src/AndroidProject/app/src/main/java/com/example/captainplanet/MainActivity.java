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

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    TextView t1,t2,t3,t4,t5;
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=findViewById(R.id.textView);
        t2=findViewById(R.id.textView2);
        t3=findViewById(R.id.textView3);
        t4=findViewById(R.id.textView4);
        t5=findViewById(R.id.textView5);
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Résultats capturés");
        getThingspeakData();
    }


    public class MyActivity extends AppCompatActivity {
        // ...
    }
    public void getThingspeakData() {

        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest( "https://thingspeak.com/channels/54807/feed/last.json?",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objLast = new JSONObject(response);
                            String dateDonnee = objLast.getString("created_at");
                            String replace1=dateDonnee.replaceAll("T"," ");
                            String dateLast=replace1.replaceAll("Z"," ");
                            String tempLast = objLast.getString("field1");
                            String humiLast = objLast.getString("field2");
                            String pm25Last = objLast.getString("field3");
                            float pm25LastValue = Float.parseFloat(pm25Last);

                            t1.setText("Date de la capture:     " + dateLast);
                            t2.setText("Température (en C°):     " + tempLast + " \u2103");
                            t3.setText("Humidité:     " + humiLast + " %");
                            t4.setText("PM2.5:  " + pm25Last + " μg/m3");

//                            float temp2 =Float.parseFloat(tempLast);
//                            temp2=temp2*10;
//                            cg_temp.setValue((int)temp2);

                            if(0.0 <=pm25LastValue && pm25LastValue<= 15.4) {
                                t5.setText("Qualité de l'air : Bonne");
                                t5.setTextColor(0xff000000);   //black
                                t5.setBackgroundColor(0xff32cd32); //limegreen
                            }
                            else if(15.5 <=pm25LastValue && pm25LastValue<= 35.4) {
                                t5.setText("Qualité de l'air : Moyenne");
                                t5.setTextColor(0xff000000);
                                t5.setBackgroundColor(0xffcdcd00);   //yellow3
                            }
                            else if(35.5 <=pm25LastValue && pm25LastValue<= 54.4) {
                                t5.setText("Qualité de l'air :Mauvaise!");
                                t5.setTextColor(0xff000000);
                                t5.setBackgroundColor(0xffee9a00); //orange2
                            }
                            else if(54.5 <=pm25LastValue && pm25LastValue<= 150.4) {
                                t5.setText("Qualité de l'air :Très Mauvaise!!");
                                t5.setTextColor(0xff000000);   //
                                t5.setBackgroundColor(0xffee0000); //red2
                            }
                            else if(150.5 <=pm25LastValue && pm25LastValue<= 250.4) {
                                t5.setText("Très Mauvaise pour la santé!!");
                                t5.setTextColor(0xffffffff);   //white
                                t5.setBackgroundColor(0xff8b008b); //Magenta4
                            }
                            else if(250.5 <=pm25LastValue) {
                                t5.setText("Qualité de l'air :Dangereux!!");
                                t5.setTextColor(0xffffffff);   //white
                                t5.setBackgroundColor(0xff8b1a1a); //firebrick4
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
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
}
