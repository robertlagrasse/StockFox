package com.example.robert.stockfox;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.gcm.GcmNetworkManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.gcm.TaskParams;
import com.melnykov.fab.FloatingActionButton;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    Context mContext;
    Boolean networkConnected;
    Intent mServiceIntent;
    Cursor mCursor;
    QuoteCursorAdapter mCursorAdapter;
    ItemTouchHelper mItemTouchHelper;

    private static final int CURSOR_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String TAG = "MainActivity onCreate";
        mContext = this;

        // Check network connectivity
        networkConnected = GenericUtils.networkCheck(this);

        // Fire up the background thread
        mServiceIntent = new Intent(this, GenericIntentService.class);
        if (networkConnected) {
            // Setup a download service on the background thread
            String symbol = null;
            mServiceIntent.putExtra(DatabaseContract.StockTable.SYMBOL, symbol);
            startService(mServiceIntent);

            // Setup periodic downloads
            GcmNetworkManager.getInstance(this).schedule(GenericUtils.buildPeriodicTask());
        } else GenericUtils.networkToast(this);

        // Watch the database
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);

        // Setup the UI
        // Reference to recyclerView (in activity_my_stocks)
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Use the default implementation LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCursorAdapter = new QuoteCursorAdapter(this, null);
        recyclerView.setAdapter(mCursorAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this,
                new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View v, int position) {
                        //TODO:
                        Toast.makeText(mContext, "Clicked position: " + position, Toast.LENGTH_SHORT).show();
                        mCursor.moveToPosition(position);
                        String symbol = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.StockTable.SYMBOL));
                        String id = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.StockTable._ID));
                        Log.e("RecyclerView", "Position: " + position + " Symbol: " + symbol + " ID: " + id);
                    }
                }));

        // Grab reference to FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // Attach fab to RecyclerView TODO: Figure out why this is necessary.
        fab.attachToRecyclerView(recyclerView);

        // Listen for fab clicks
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (networkConnected){
                    Toast.makeText(mContext, "Add button clicked", Toast.LENGTH_LONG).show();
                    Intent tmpServiceIntent = new Intent(mContext, GenericIntentService.class);
                    tmpServiceIntent.putExtra(DatabaseContract.StockTable.SYMBOL, "CXW");
                    startService(tmpServiceIntent);

                } else {
                    networkToast();
                }

            }
        });

        // Enable listening for swipe events - essentially an advanced onClickListener
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mCursorAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);

        // attach listener to recyclerView to handle swipes
        mItemTouchHelper.attachToRecyclerView(recyclerView);

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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        String TAG = "onCreateLoader";
        Log.e(TAG, "onCreateLoader called");
        // This is the database query
        return new CursorLoader(this, DatabaseContract.CONTENT_URI.buildUpon().appendPath("UI").build(),
                DatabaseContract.StockTable.STOCK_ALL_KEYS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data){
        // This is the result of the database query - pass data to where ever.
        String TAG = "onLoadFinished()";
        mCursorAdapter.swapCursor(data);
        mCursor = data;

        Stock stock = new Stock();

        Log.e(TAG, "mCursor.getCount(): " + mCursor.getCount());

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        String TAG = "onLoaderReset()";
        Log.e(TAG, "onLoaderReset called");
        mCursorAdapter.swapCursor(null);
    }
}
