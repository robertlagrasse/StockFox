package com.example.robert.stockfox;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by robert on 11/4/16.
 *
 * This is where utilities specific to this project will live.
 */

public class StockFoxUtils {

    public static String buildURLasString(){
        String TAG = "buildURLasString()";
        String urlString;
        StringBuilder urlStringBuilder = new StringBuilder();
        try{
            // Base URL for the Yahoo query
            urlStringBuilder.append("https://query.yahooapis.com/v1/public/yql?q=");
            urlStringBuilder.append(URLEncoder.encode("select * from yahoo.finance.quotes where symbol "
                    + "in (", "UTF-8"));
            Log.e(TAG, "urlStringBuilder = " + urlStringBuilder.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            urlStringBuilder.append(
                    URLEncoder.encode("\"YHOO\",\"AAPL\",\"GOOG\",\"MSFT\")", "UTF-8"));
            // See what the request URL looks like now
            Log.e(TAG, "urlStringBuilder = " + urlStringBuilder.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // finalize the URL for the API query.
        urlStringBuilder.append("&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables."
                + "org%2Falltableswithkeys&callback=");

        Log.e(TAG, "urlStringBuilder = " + urlStringBuilder.toString());

        urlString = urlStringBuilder.toString();

        return urlString;
    }
}
