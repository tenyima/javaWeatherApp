package com.abmcollegeattenzin.nyima.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


//d789c6ee3eaaf2706e5ce33fbfcbbae7
public class MainActivity extends AppCompatActivity {

    TextView temperature;
    TextView myCity;

    ImageView img;
    TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCity = findViewById(R.id.city);
        temperature = findViewById(R.id.temp);
        img = findViewById(R.id.img);
        desc = findViewById(R.id.description);

        String url = "http://api.openweathermap.org/data/2.5/weather?q=Hamilton&appid=d789c6ee3eaaf2706e5ce33fbfcbbae7";

                    //api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}&appid={API key}

                    //L8W 3N1

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject responseObject) {

                try
                {
                    JSONObject mainJSONObject = responseObject.getJSONObject("main");
                    JSONArray weatherArray = responseObject.getJSONArray("weather");
                    JSONObject firstWeatherObject = weatherArray.getJSONObject(0);

                    double t = Math.round(mainJSONObject.getDouble("temp"));
                    String temp = customFormat("##.##", (t-273.5));

                    String weatherDescription = firstWeatherObject.getString("description");
                    if(weatherDescription.contains("cloud"))
                    {
                        img.setImageResource(R.drawable.cloudy);
                    }
                    String city = responseObject.getString("name");

                    temperature.setText(temp + " \u00B0C");
                    desc.setText(weatherDescription);
                    myCity.setText(city);

                    int iconResourceId = getResources().getIdentifier("icon_" + weatherDescription.replace(" ", ""), "drawable", getPackageName());
                    img.setImageResource(iconResourceId);

                    if(weatherDescription.contains("cloud"))
                    {
                        img.setImageResource(R.drawable.cloudy);
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
    }

    private String getCurrentDate ()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd");
        String formattedDate = dateFormat.format(calendar.getTime());

        return formattedDate;
    }

    static public String customFormat(String pattern, double value ) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        return output;
    }

}






















