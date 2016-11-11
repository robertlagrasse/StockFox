package com.example.robert.stockfox;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by robert on 11/10/16.
 */

public class GenericDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_detail_activity);
        String TAG = "GenericDetailActivity";

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Extract _ID from extra passed with intent that fired up this activity
        Intent intent = getIntent();
        String id = intent.getStringExtra(DatabaseContract.StockTable._ID);
        Log.e(TAG, "ID passed via intent: " + id);
        GenericDetailFragment gdf = new GenericDetailFragment();
        gdf.setId(id);
        fragmentTransaction.add(R.id.generic_detail_activity, gdf);
        fragmentTransaction.commit();

    }
}
