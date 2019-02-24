package com.example.poy.capstonedraftv8;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.net.URLConnection;
import java.util.ArrayList;

import java.util.Iterator;

import dmax.dialog.SpotsDialog;


public class Weather_pane extends AppCompatActivity {



    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<WeatherModel> weatherArrayList = new ArrayList<>();
    private ListView listView;
    TextView currentTemp,weatherText,humidity,wind;
    ImageView dayNight;
    Button weather_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_pane);

        listView = findViewById(R.id.idListView);

        currentTemp = (TextView)findViewById(R.id.currentTemp);
        weatherText = (TextView)findViewById(R.id.WeatherText);
        humidity = (TextView)findViewById(R.id.Humidity);
        wind = (TextView)findViewById(R.id.Wind);

        weather_details = (Button) findViewById(R.id.button4);

        dayNight = (ImageView) findViewById(R.id.dayNight);

        URL weatherUrl = NetworkUtils.buildUrlForWeather();
        new FetchWeatherDetails().execute(weatherUrl);
        Log.i(TAG, "onCreate: weatherUrl: " + weatherUrl);

        new currentConditions().execute();


      //  new currentConditions().execute();

       /* URL weatherUrl2 = NetworkUtilsCurrentCondition.buildUrlForWeather();
        new FetchWeatherDetails2().execute(weatherUrl2);*/

       weather_details.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               /*Intent intent = new Intent(getApplicationContext(),Weather_details.class);
               startActivity(intent);
               overridePendingTransition(R.anim.slide_out, R.anim.mysplashanimation);*/
           }
       });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1,

                                    int position, long arg3) {

                // TODO Auto-generated method stub

                            /*Toast.makeText(MainActivity.this,

                                    "You have selected : " + myList.get(position),

                                    Toast.LENGTH_SHORT).show();*/

                WeatherModel dataModel = weatherArrayList.get(position);

                Message.message(getApplicationContext(),dataModel.getDate());
                Intent intent = new Intent(getApplicationContext(),Weather_details.class);
                intent.putExtra("weather_date",dataModel.getDate());
                startActivity(intent);
                overridePendingTransition(R.anim.mysplashanimation,R.anim.slide_in);



            }

        });
    }


    private class FetchWeatherDetails extends AsyncTask<URL, Void, String> {
        final AlertDialog alertDialog= new SpotsDialog.Builder().setContext(Weather_pane.this).setTheme(R.style.Custom).build();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

                alertDialog.show();

        }

        @Override
        protected String doInBackground(URL... urls) {



            URL weatherUrl = urls[0];
            String weatherSearchResults = null;

            try {
                weatherSearchResults = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: weatherSearchResults: " + weatherSearchResults);
            return weatherSearchResults;
        }



        @Override
        protected void onPostExecute(String weatherSearchResults) {




            if(weatherSearchResults != null && !weatherSearchResults.equals("")) {
                weatherArrayList = parseJSON(weatherSearchResults);
                //Just for testing
                Iterator itr = weatherArrayList.iterator();
                while(itr.hasNext()) {
                    WeatherModel weatherInIterator = (WeatherModel) itr.next();
                    Log.i(TAG, "onPostExecute: Date: " + weatherInIterator.getDate()+
                            " Min: " + weatherInIterator.getMinTemp() +
                            " Max: " + weatherInIterator.getMaxTemp());
                }
            }
            alertDialog.dismiss();
            super.onPostExecute(weatherSearchResults);
        }
    }





    private ArrayList<WeatherModel> parseJSON(String weatherSearchResults) {
        if(weatherArrayList != null) {
            weatherArrayList.clear();
        }

        if(weatherSearchResults != null) {
            try {
                JSONObject rootObject = new JSONObject(weatherSearchResults);
                JSONArray results = rootObject.getJSONArray("DailyForecasts");

                for (int i = 0; i < results.length(); i++) {
                    WeatherModel weather = new WeatherModel();
                    JSONObject resultsObj = results.getJSONObject(i);

                    String date = resultsObj.getString("Date");

                    try{
                        DateTime d = DateTime.parse(date);
                        String output = ISODateTimeFormat.date().print(d);
                        String[] output2 = output.split("-");
                        int month=Integer.parseInt(output2[1]);
                        String dateFinal =output2[2]+" "+getMonthName(month);

                        weather.setDate(dateFinal);
                        Weather_details.list1.add(dateFinal);

                    }
                    catch (Exception e)
                    {
                        Message.message(getApplicationContext(),e.getMessage());
                        weather.setDate(date);
                    }


                    JSONObject temperatureObj = resultsObj.getJSONObject("Temperature");
                    String minTemperature = temperatureObj.getJSONObject("Minimum").getString("Value");
                    weather.setMinTemp(minTemperature);
                    Weather_details.list1.add(minTemperature+" °C");

                    String maxTemperature = temperatureObj.getJSONObject("Maximum").getString("Value");
                    weather.setMaxTemp(maxTemperature);
                    Weather_details.list1.add(maxTemperature+" °C");

                    //******************************DAY***********************************************
                    JSONObject dayObj = resultsObj.getJSONObject("Day");
                    int dayIcon = dayObj.getInt("Icon");
                    weather.setDayIcon(dayIcon);
                    Weather_details.list1.add(dayIcon);

                    String iconPhrase = dayObj.getString("IconPhrase");
                    weather.setIconPhrase(iconPhrase);
                    Weather_details.list1.add(iconPhrase);

                    String rain = dayObj.getString("RainProbability");
                    Weather_details.list1.add(rain+" %");

                    String wind = dayObj.getJSONObject("Wind").getJSONObject("Speed").getString("Value");
                    Weather_details.list1.add(wind+" km/h");

                    //******************************NIGHT***********************************************
                    JSONObject nightObj = resultsObj.getJSONObject("Night");
                    int nightIcon = nightObj.getInt("Icon");
                    Weather_details.list1.add(nightIcon);

                    String iconPhraseNight = nightObj.getString("IconPhrase");
                    Weather_details.list1.add(iconPhraseNight);

                    String rainNight = nightObj.getString("RainProbability");
                    Weather_details.list1.add(rainNight+" %");

                    String windNight = nightObj.getJSONObject("Wind").getJSONObject("Speed").getString("Value");
                    Weather_details.list1.add(windNight+" km/h");




                   /* Log.i(TAG, "parseJSON: date: " + date + " " +
                            "Min: " + minTemperature + " " +
                            "Max: " + maxTemperature + " " +
                            "Link: " + link);*/

                    weatherArrayList.add(weather);
                }

                if(weatherArrayList != null) {
                    WeatherAdapter weatherAdapter = new WeatherAdapter(this, weatherArrayList);
                    listView.setAdapter(weatherAdapter);
                }

                return weatherArrayList;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private class currentConditions extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params)
        {

            String str="http://dataservice.accuweather.com/currentconditions/v1/265081?apikey=rquVGD9GWSzSoCRwzjYs17NvCXGsu0eh&details=true";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                return new String(stringBuffer.toString());
            }
            catch(Exception ex)
            {
                Log.e("App", "yourDataTask", ex);
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String response)
        {
            if(response != null)
            {
                String temperature="";
                String isDayTime="";
                String weatherText2="";
                String humidity2="";
                String wind2="";
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        temperature = jsonobject.getJSONObject("Temperature").getJSONObject("Metric").getString("Value");
                        isDayTime = jsonobject.getString("IsDayTime");
                        weatherText2 = jsonobject.getString("WeatherText");
                        humidity2 = jsonobject.getString("RelativeHumidity");
                        wind2 = jsonobject.getJSONObject("Wind").getJSONObject("Speed").getJSONObject("Metric").getString("Value");
                    }
                    if(isDayTime.equals("true"))
                    {
                    dayNight.setImageResource(R.mipmap.sun);
                    }
                    else
                    {
                    dayNight.setImageResource(R.mipmap.moon2);
                    }
                    currentTemp.setText(temperature+" °C");
                    weatherText.setText(weatherText2);
                    humidity.setText(humidity2+"%");
                    wind.setText(wind2+ "km/h");

                } catch (JSONException ex) {
                    Log.e("App", "Failure", ex);
                }
            }
        }
    }


















    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Weather_pane.this,Menu.class);
        startActivity(intent);
        finish();
    }

    public String getMonthName (int month){
        String monthName="";

        switch (month)
        {
            case 1:
                monthName="Jan";
                break;

            case 2:
                monthName="Feb";
                break;

            case 3:
                monthName="Mar";
                break;

            case 4:
                monthName="Apr";
                break;

            case 5:
                monthName="May";
                break;

            case 6:
                monthName="Jun";
                break;

            case 7:
                monthName="Jul";
                break;

            case 8:
                monthName="Aug";
                break;

            case 9:
                monthName="Sep";
                break;

            case 10:
                monthName="Oct";
                break;

            case 11:
                monthName="Nov";
                break;

            case 12:
                monthName="Dec";
                break;
            default:
                monthName="No Month";
                break;

        }

        return monthName;
    }


}
