package com.example.robert.stockfox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

/**
 * Created by robert on 11/2/16.
 *
 * This class provides access to the database, but will only be accessed
 * through the ContentProvider for CRUD (Create, Request, Update, Delete)
 * operations
 *
 */

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseManager";

    // Define some global database stuff
    private static final int DATABASE_VERSION = 1; // TODO: Move to contract
    private static final String DATABASE_NAME = "database"; // TODO: Move to contract
    private final Uri DATABASE_URI =
            Uri.parse("content://com.example.android.StockFox/stocks");


    DatabaseManager (Context context) {
        //super(context, name, factory, version); // original super.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "onCreate Called");
        db.execSQL(DatabaseContract.StockTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "New Database Created. Version: " + newVersion);
        // delete the existing database
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.StockTable.TABLE_NAME);

        // call onCreate
        onCreate(db);
    }

}
