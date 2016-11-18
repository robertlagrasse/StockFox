package com.example.robert.stockfox;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.gcm.GcmNetworkManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.gcm.TaskParams;
import com.melnykov.fab.FloatingActionButton;
import com.afollestad.materialdialogs.MaterialDialog;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    Context mContext;
    Boolean networkConnected;
    Intent mServiceIntent;
    Cursor mCursor; // Result of LoaderManager query
    QuoteCursorAdapter mCursorAdapter; // receives data from LoaderManager query every time it happens
    ItemTouchHelper mItemTouchHelper;
    String mRequestedSymbol;
    Boolean showPercent = false;

    private static final int CURSOR_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String TAG = "MainActivity onCreate";
        mContext = this;
        mRequestedSymbol = null;

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
        recyclerView.setContentDescription("List of stocks");

        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this,
                new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        mCursor.moveToPosition(position);
                        String symbol = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.StockTable.SYMBOL));
                        String id = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.StockTable._ID));
                        Intent tmpServiceIntent = new Intent(mContext, GenericDetailActivity.class);
                        tmpServiceIntent.putExtra(DatabaseContract.StockTable._ID, id);
                        startActivity(tmpServiceIntent);
                    }
                }));

        // Grab reference to FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // Attach fab to RecyclerView TODO: Figure out why this is necessary.
        fab.attachToRecyclerView(recyclerView);
        fab.setContentDescription("Button to add a new stock");

        // Listen for fab clicks
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkConnected) {
                    new MaterialDialog.Builder(mContext).title(R.string.symbol_search)
                            .content(R.string.content_test)
                            .inputType(InputType.TYPE_CLASS_TEXT)
                            .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                    if (StockFoxUtils.stockIsUnique(mContext, input.toString().toUpperCase())) {
                                        mRequestedSymbol = input.toString().toUpperCase();
                                        Intent tmpServiceIntent = new Intent(mContext, GenericIntentService.class);
                                        tmpServiceIntent.putExtra(DatabaseContract.StockTable.SYMBOL, input.toString().toUpperCase());
                                        startService(tmpServiceIntent);
                                    } else {
                                        Toast.makeText(mContext, "Not unique!", Toast.LENGTH_LONG).show();
                                        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                                        v.vibrate(500);
                                    }
                                }
                            })
                            .show();
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

    public void insertTest() {
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

    public void queryTest() {
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

    public void networkToast() {
        Toast.makeText(mContext, getString(R.string.network_toast), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String TAG = "onCreateLoader";
        // This is the database query
        // TODO: Proper URI Builder in the ContentProvider
        return new CursorLoader(this, DatabaseContract.CONTENT_URI.buildUpon().appendPath("UI").build(),
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // This is the result of the database query - pass data to where ever.
        String TAG = "onLoadFinished()";
        mCursorAdapter.swapCursor(data);
        mCursor = data;
        Log.e(TAG, "called");
        // mRequestedSymbol is only non-null when the fab has been clicked.
        if (mRequestedSymbol != null){
            Log.e(TAG, "mRequestedSymbol was not null");
            // if mRequestedSymbol is unique, it's not in the database, which means
            // Yahoo! didn't have information on that stock. Alert user.
            if (StockFoxUtils.stockIsUnique(mContext, mRequestedSymbol.toString().toUpperCase())){
                Log.e(TAG, "Stock was not in database");
                Toast.makeText(mContext, mRequestedSymbol + " not found!", Toast.LENGTH_LONG).show();
                Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
            }
        }
        mRequestedSymbol = null;
        StockFoxUtils.updateWidgets(mContext);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        String TAG = "onLoaderReset()";
        Log.e(TAG, "onLoaderReset called");
        mCursorAdapter.swapCursor(null);
    }


    @Override
    public void onResume() {
        mRequestedSymbol = null;
        String TAG = "onResume";
        super.onResume();
        getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);
        if (!networkConnected) networkToast();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("---> StockHawk <---");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_stocks, menu);
        restoreActionBar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_change_units){
            // this is for changing stock changes from percent value to dollar value
            StockFoxUtils.showPercent = !StockFoxUtils.showPercent;
            this.getContentResolver().notifyChange(DatabaseContract.CONTENT_URI, null);
        }

        return super.onOptionsItemSelected(item);
    }
}