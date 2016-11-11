package com.example.robert.stockfox;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.TaskParams;

/**
 * Created by robert on 11/3/16.
 */

public class GenericIntentService extends IntentService {
    String TAG = "GenericIntentService";

    public GenericIntentService(){
        super(GenericIntentService.class.getName());
    }

    public GenericIntentService(String name) {
        super(name);
    }

    @Override protected void onHandleIntent(Intent intent) {
        Bundle args = new Bundle();

        // Build the intent
        GenericTaskService genericTaskService = new GenericTaskService(this);

        String symbol = intent.getStringExtra(DatabaseContract.StockTable.SYMBOL);
        if (symbol == null){
            Log.e(TAG, "intent.getStringExtra(DatabaseContract.StockTable.SYMBOL) was null");
        }else {
            Log.e(TAG, "symbol = " + symbol);
        }

        // Populate the intent
        args.putString(DatabaseContract.StockTable.SYMBOL, symbol);

        // Launch the intent
        genericTaskService.onRunTask(new TaskParams("pass", args));
    }
}
