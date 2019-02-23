package com.example.poy.capstonedraftv8;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;

import java.util.Iterator;


public class Weather_pane extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<WeatherModel> weatherArrayList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_pane);

        listView = findViewById(R.id.idListView);

        URL weatherUrl = NetworkUtils.buildUrlForWeather();
        new FetchWeatherDetails().execute(weatherUrl);
        Log.i(TAG, "onCreate: weatherUrl: " + weatherUrl);
    }


    private class FetchWeatherDetails extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                        Message.message(getApplicationContext(),output2[1]);
                    }
                    catch (Exception e)
                    {
                        Message.message(getApplicationContext(),e.getMessage());
                        weather.setDate(date);
                    }


                    JSONObject temperatureObj = resultsObj.getJSONObject("Temperature");
                    String minTemperature = temperatureObj.getJSONObject("Minimum").getString("Value");
                    weather.setMinTemp(minTemperature);

                    String maxTemperature = temperatureObj.getJSONObject("Maximum").getString("Value");
                    weather.setMaxTemp(maxTemperature);

                    JSONObject dayObj = resultsObj.getJSONObject("Day");
                    int dayIcon = dayObj.getInt("Icon");
                    weather.setDayIcon(dayIcon);

                    String iconPhrase = dayObj.getString("IconPhrase");
                    weather.setIconPhrase(iconPhrase);




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
