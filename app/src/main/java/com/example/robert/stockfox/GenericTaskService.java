package com.example.robert.stockfox;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

/**
 * Created by robert on 11/3/16.
 *
 * The GenericTaskService runs on a background thread. It can be kicked off by a periodic task,
 * or by Intent.
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
        if (mContext == null){
            mContext = this;
        }
        String stockInput = null;
        String urlString;
        String getResponse = null;
        int result = GcmNetworkManager.RESULT_FAILURE;

        if (params.getTag().equals("periodic")) {
            stockInput=null;
        } else{
            stockInput = params.getExtras().getString(DatabaseContract.StockTable.SYMBOL);
        }

        // Build URL to identify target data source
        urlString = StockFoxUtils.buildURLasString(mContext, stockInput);

        // Fetch Data from target data source
        if (urlString != null){
            try{
                // Go grab the data specified in the URL
                getResponse = fetchData(urlString);
                result = GcmNetworkManager.RESULT_SUCCESS;

            } catch (IOException e){
                e.printStackTrace();
            }
        }
        // Handle received data
        if (getResponse != null) {
            StockFoxUtils.JSONtoDB(getResponse, mContext);
        } else{
            Toast.makeText(mContext, getString(R.string.server_not_responding), Toast.LENGTH_LONG);
        }
        return result;
    }

}
