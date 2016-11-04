package com.example.robert.stockfox;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.TaskParams;

/**
 * Created by robert on 11/3/16.
 */

public class StockIntentService extends IntentService {
    String TAG = "StockIntentService";

    public StockIntentService(){
        super(StockIntentService.class.getName());
    }

    public StockIntentService(String name) {
        super(name);
    }

    @Override protected void onHandleIntent(Intent intent) {
        Bundle args = new Bundle();

        //Log.e(TAG, "intent.getStringExtra(\"tag\") = " + intent.getStringExtra("tag"));
        StockTaskService stockTaskService = new StockTaskService(this);

//        if (intent.getStringExtra("tag").equals("add")){
//            Log.e(TAG, "Stock symbol "+ intent.getStringExtra("symbol") + "received.");
//            args.putString("symbol", intent.getStringExtra("symbol"));
//        }
        // We can call OnRunTask from the intent service to force it to run immediately instead of
        // scheduling a task.
        Log.e(TAG, "Kicking off stockTaskService.onRunTask");
        stockTaskService.onRunTask(new TaskParams(intent.getStringExtra("tag"), args));
    }
}
