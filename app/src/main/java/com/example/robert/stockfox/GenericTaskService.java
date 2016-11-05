package com.example.robert.stockfox;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

/**
 * Created by robert on 11/3/16.
 */

public class GenericTaskService extends GcmTaskService{
    private String LOG_TAG = GenericTaskService.class.getSimpleName();

    private OkHttpClient client = new OkHttpClient();
    private Context mContext;
    private StringBuilder mStoredSymbols = new StringBuilder();
    private boolean isUpdate;
    private String TAG = "GenericTaskService";

    public GenericTaskService(){}

    public GenericTaskService(Context context){
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
        // Build the URL string using method in project specific utility class StockFoxUtils
        // Fetch the data
        // Send the data to a method in project specific utility class for processing.


        Log.e(TAG, "onRunTask started");

        if (mContext == null){
            mContext = this;
        }

        String urlString;
        String getResponse = "no data";
        int result = GcmNetworkManager.RESULT_FAILURE;

        // Build URL
        urlString = StockFoxUtils.buildURLasString();

        // Fetch Data
        if (urlString != null){
            try{
                // Go grab the data specified in the URL
                getResponse = fetchData(urlString);
                result = GcmNetworkManager.RESULT_SUCCESS;

            } catch (IOException e){
                e.printStackTrace();
            }
        }

        // Handle data
        Log.e(TAG, "getResponse = " + getResponse);
        StockFoxUtils.quoteJsonToStock(getResponse, mContext);

        return result;
    }

}
