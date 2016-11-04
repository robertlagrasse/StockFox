package com.example.robert.stockfox;

import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by robert on 11/3/16.
 */

public class StockTaskService extends GcmTaskService{
    private String LOG_TAG = StockTaskService.class.getSimpleName();

    private OkHttpClient client = new OkHttpClient();
    private Context mContext;
    private StringBuilder mStoredSymbols = new StringBuilder();
    private boolean isUpdate;
    private String TAG = "StockTaskService";

    public StockTaskService(){}

    public StockTaskService(Context context){
        mContext = context;
    }
    String fetchData(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public int onRunTask(TaskParams params) {
        Log.e(TAG, "onRunTask started");

        // build a cursor
        Cursor initQueryCursor;
        if (mContext == null){
            mContext = this;
        }
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
/*
        // Check to see what kind of request this is. Are we checking what we have, or adding something?
        if (params.getTag().equals("init") || params.getTag().equals("periodic")) {
            // We're pulling an update
            isUpdate = true;
            // Query the content provider
            initQueryCursor = mContext.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                    new String[] { "Distinct " + QuoteColumns.SYMBOL }, null,
                    null, null);

            // Check to see if ContentProvider returned and empty set
            if (initQueryCursor.getCount() == 0 || initQueryCursor == null){
                // Nothing in the local DB, so we'll build a query from scratch
                try {
                    urlStringBuilder.append(
                            URLEncoder.encode("\"YHOO\",\"AAPL\",\"GOOG\",\"MSFT\")", "UTF-8"));
                    // See what the request URL looks like now
                    Log.e(TAG, "urlStringBuilder = " + urlStringBuilder.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } // There's something in the database
            else if (initQueryCursor != null){
                Log.e(TAG, "initQueryCursor was not null. Original code dumps the cursor next.");
                DatabaseUtils.dumpCursor(initQueryCursor);
                initQueryCursor.moveToFirst();
                // Step through the returned rows
                for (int i = 0; i < initQueryCursor.getCount(); i++){
                    // Append each symbol found by the cursor, surrounded by quotes, to StringBuilder mStoredSymbols
                    mStoredSymbols.append("\""+
                            initQueryCursor.getString(initQueryCursor.getColumnIndex("symbol"))+"\",");
                    initQueryCursor.moveToNext();
                }
                Log.e(TAG, "mStoredSymbols = " + mStoredSymbols.toString());
                // Replace the last part with a ")"
                mStoredSymbols.replace(mStoredSymbols.length() - 1, mStoredSymbols.length(), ")");
                Log.e(TAG, "mStoredSymbols = " + mStoredSymbols.toString());
                try {
                    // add mStoredSymbols to the urlStringBuilder
                    urlStringBuilder.append(URLEncoder.encode(mStoredSymbols.toString(), "UTF-8"));
                    Log.e(TAG, "urlStringBuilder = " + urlStringBuilder.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        } else if (params.getTag().equals("add")){
            // We're adding something, not just taking a look at what's already in place
            isUpdate = false;
            // get symbol from params.getExtra and build query
            String stockInput = params.getExtras().getString("symbol");
            Log.e(TAG, "stockInput = " + stockInput);
            try {
                urlStringBuilder.append(URLEncoder.encode("\""+stockInput+"\")", "UTF-8"));
                Log.e(TAG, "urlStringBuilder = " + urlStringBuilder.toString());
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }


        // finalize the URL for the API query.
        urlStringBuilder.append("&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables."
                + "org%2Falltableswithkeys&callback=");
*/

        String urlString;
        String getResponse;
        int result = GcmNetworkManager.RESULT_FAILURE;

        if (urlStringBuilder != null){
            urlString = urlStringBuilder.toString();
            Log.e(TAG, "urlString = " + urlString);

            try{
                // Go grab the data specified in the URL
                getResponse = fetchData(urlString);
                Log.e(TAG, "getResponse = " + getResponse);
                result = GcmNetworkManager.RESULT_SUCCESS;

                /*
                try {
                    ContentValues contentValues = new ContentValues();
                    // update ISCURRENT to 0 (false) so new data is current
                    if (isUpdate){
                        contentValues.put(QuoteColumns.ISCURRENT, 0);
                        mContext.getContentResolver().update(QuoteProvider.Quotes.CONTENT_URI, contentValues,
                                null, null);
                    }
                    mContext.getContentResolver().applyBatch(QuoteProvider.AUTHORITY,
                            Utils.quoteJsonToContentVals(getResponse));
                }catch (RemoteException | OperationApplicationException e){
                    Log.e(LOG_TAG, "Error applying batch insert", e);
                }*/
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        return result;
    }

}
