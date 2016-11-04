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

        GenericTaskService genericTaskService = new GenericTaskService(this);
        genericTaskService.onRunTask(new TaskParams(intent.getStringExtra("some-tag"), args));
    }
}
