package com.example.poy.capstonedraftv8;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtilsCurrentCondition
{
    private static final String TAG = "CurrentCondition";

    private final static String WEATHERDB_BASE_URL=
            "http://dataservice.accuweather.com/currentconditions/v1/265081";

    private final static String API_KEY = "rquVGD9GWSzSoCRwzjYs17NvCXGsu0eh";

    private final static String PARAM_API_KEY = "apikey";

    private final static String PARAM_DETAILS = "details";

    private final static String DETAILS_VALUE = "true";




    public static URL buildUrlForWeather() {
        Uri builtUri = Uri.parse(WEATHERDB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(PARAM_DETAILS, DETAILS_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "buildUrlForWeather: url: "+url);
        return url;
    }

    public static String getResponseFromHttpUrl2(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in  = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}