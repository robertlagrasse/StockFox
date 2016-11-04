package com.example.robert.stockfox;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    Boolean networkConnected;
    Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String TAG = "MainActivity onCreate";
        mContext = this;
//        insertTest();
//        queryTest();
        // Check network connectivity
        networkConnected = Utils.networkCheck(this);

        // Fire up the background thread
        mServiceIntent = new Intent(this, StockIntentService.class);
        if (networkConnected) {
            // Setup a download service on the background thread
            startService(mServiceIntent);

            // Schedule a periodic task to perform the downloads additional downloads
            long period = 3600L;
            long flex = 10L;
            String periodicTag = "periodic";

            // create a periodic task to pull stocks once every hour after the app has been opened. This
            // is so Widget data stays up to date.
            Log.e(TAG, "Starting Periodic Task");
            PeriodicTask periodicTask = new PeriodicTask.Builder()
                    .setService(StockTaskService.class)
                    .setPeriod(period)
                    .setFlex(flex)
                    .setTag(periodicTag)
                    .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                    .setRequiresCharging(false)
                    .build();
            // Schedule task with tag "periodic." This ensure that only the stocks present in the DB
            // are updated.
            GcmNetworkManager.getInstance(this).schedule(periodicTask);
        } else networkToast();
    }

    public void insertTest(){
        String TAG = "insertTest()";

        ContentValues values = new ContentValues();
        Stock stock = new Stock();
        stock.setSymbol("FARK");

        values.put(DatabaseContract.StockTable.SYMBOL, stock.getSymbol());

        Uri insertedUri = mContext.getContentResolver().insert(
                DatabaseContract.CONTENT_URI,
                values
        );

        Log.e(TAG, "Returned URI: " + insertedUri.toString());
    }

    public void queryTest(){
        String TAG = "queryTest()";
        String fullURI;
        fullURI = DatabaseContract.CONTENT_URI.buildUpon().appendPath("symbol").appendPath("FARK").build().toString();
        Log.e(TAG, "fullURI = " + fullURI);

        Cursor cursor;
        // Pull the details on the selected movie from the MovieEntry table
        cursor = mContext.getContentResolver().query(
                // Have to build a URI here that looks like
                // content://com.example.robert.stockfox/stocks/symbol/xxxx
                DatabaseContract.CONTENT_URI.buildUpon().appendPath("symbol").appendPath("FARK").build(),
                DatabaseContract.StockTable.STOCK_ALL_KEYS,
                null,
                null,
                null);

        // Populate the movie object
        Stock stock = new Stock();

        if (cursor.moveToFirst()) {
            stock.setSymbol(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.SYMBOL)));
            // Build the balance of these elements as above

        } else {
            // Load it up with dummy information if you don't find anything in the MovieEntry table
            // This happens the first time the app is fired up, as it hasn't been populated yet.
            Log.e(TAG, "Cursor returned no rows - populating with bogus information");
            stock.setSymbol("bogus");
        }
        Log.e(TAG, "stock.getSymbol() returns: " + stock.getSymbol());
        cursor.close();

    }

    public void networkToast(){
        Toast.makeText(mContext, getString(R.string.network_toast), Toast.LENGTH_SHORT).show();
        Log.e("networkToast()", "network is dead!");
    }
}
