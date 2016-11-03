package com.example.robert.stockfox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        insertTest();
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
}
