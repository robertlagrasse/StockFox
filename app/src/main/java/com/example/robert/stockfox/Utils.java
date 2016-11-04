package com.example.robert.stockfox;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

/**
 * Created by robert on 11/3/16.
 */

public class Utils {

    public static Boolean networkCheck(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    public static PeriodicTask buildPeriodicTask(){
        long period = 3600L;
        long flex = 10L;
        String periodicTag = "periodic";

        return new PeriodicTask.Builder()
                .setService(GenericTaskService.class)
                .setPeriod(period)
                .setFlex(flex)
                .setTag(periodicTag)
                .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED)
                .setRequiresCharging(false)
                .build();
    }

    public static void networkToast(Context context){
        Toast.makeText(context, "Network down", Toast.LENGTH_SHORT).show();
        Log.e("networkToast()", "network is dead!");
    }

}
