package com.example.listedesstations;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    private static final String THINGSPEAK_FIELD1 = "field1";
    private static final String THINGSPEAK_FIELD2 = "field2";
    private static final String THINGSPEAK_FIELD3 = "field3";
    TextView t1,t2,t3,t4;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=(TextView)findViewById(R.id.textView2);
        t2=(TextView)findViewById(R.id.textView3);
        t3=(TextView)findViewById(R.id.textView4);
        t4=(TextView)findViewById(R.id.textView5);
        b1=(Button) findViewById(R.id.button);

    }

    public void getThingspeakData() {

        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new StringRequest( "https://thingspeak.com/channels/1015166/feed/last.json?",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objLast = new JSONObject(response);
                            String tempLast = objLast.getString(THINGSPEAK_FIELD1);
                            String humiLast = objLast.getString(THINGSPEAK_FIELD2);
                            String pm25Last = objLast.getString(THINGSPEAK_FIELD3);
                            float pm25LastValue = Float.parseFloat(pm25Last);

                            t1.setText("Température (en C°):     " + tempLast + " \u2103");  //℃ Unicode
                            t2.setText("Humidité:     " + humiLast + " %");
                            t3.setText("PM2.5:  " + pm25Last + " μg/m3");

//                            float temp2 =Float.parseFloat(tempLast);
//                            temp2=temp2*10;
//                            cg_temp.setValue((int)temp2);

                            if(0.0 <=pm25LastValue && pm25LastValue<= 15.4) {
                                t4.setText("Qualité de l'air : Bonne");
                                t4.setTextColor(0xff000000);   //black
                                t4.setBackgroundColor(0xff32cd32); //limegreen
                            }
                            else if(15.5 <=pm25LastValue && pm25LastValue<= 35.4) {
                                t4.setText("Qualité de l'air : Moyenne");
                                t4.setTextColor(0xff000000);
                                t4.setBackgroundColor(0xffcdcd00);   //yellow3
                            }
                            else if(35.5 <=pm25LastValue && pm25LastValue<= 54.4) {
                                t4.setText("Mauvaise!");
                                t4.setTextColor(0xff000000);
                                t4.setBackgroundColor(0xffee9a00); //orange2
                            }
                            else if(54.5 <=pm25LastValue && pm25LastValue<= 150.4) {
                                t4.setText("Très Mauvaise!!");
                                t4.setTextColor(0xff000000);   //
                                t4.setBackgroundColor(0xffee0000); //red2
                            }
                            else if(150.5 <=pm25LastValue && pm25LastValue<= 250.4) {
                                t4.setText("Très Mauvaise pour la santé!!");
                                t4.setTextColor(0xffffffff);   //white
                                t4.setBackgroundColor(0xff8b008b); //Magenta4
                            }
                            else if(250.5 <=pm25LastValue) {
                                t4.setText("Dangereux!!");
                                t4.setTextColor(0xffffffff);   //white
                                t4.setBackgroundColor(0xff8b1a1a); //firebrick4
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
    public void clickBouton(View v)
    {
        getThingspeakData();
    }
}
