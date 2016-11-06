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
        mServiceIntent = new Intent(this, GenericIntentService.class);
        if (networkConnected) {
            // Setup a download service on the background thread
            startService(mServiceIntent);

            // Setup periodic downloads
            GcmNetworkManager.getInstance(this).schedule(Utils.buildPeriodicTask());
        } else Utils.networkToast(this);

        queryTest();
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

        Cursor cursor;
        // Pull the details on the selected movie from the MovieEntry table
        cursor = mContext.getContentResolver().query(
                // Have to build a URI here that looks like
                // content://com.example.robert.stockfox/stocks/
                DatabaseContract.CONTENT_URI,
                null,
                null,
                null,
                null);

        // Populate the movie object
        Stock stock = new Stock();

        cursor.moveToFirst();
        stock.setSymbol(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.SYMBOL)));
        Log.e(TAG, "stock.getSymbol(): " + stock.getSymbol());
        try {
            while (cursor.moveToNext()) {
                stock.setSymbol(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.SYMBOL)));
                Log.e(TAG, "stock.getSymbol(): " + stock.getSymbol());
            }
        } finally {
            cursor.close();
        }

    }

    public void networkToast(){
        Toast.makeText(mContext, getString(R.string.network_toast), Toast.LENGTH_SHORT).show();
        Log.e("networkToast()", "network is dead!");
    }
}
