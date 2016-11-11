package com.example.robert.stockfox;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by robert on 11/2/16.
 *
 * The ContentProvider provides a front end for the local database so this application,
 * and potentially others, can access data from SQLite
 *
 */

public class DatabaseContentProvider extends ContentProvider{
    DatabaseManager databaseManager;
    private static String TAG = "DatabaseContentProvider";


    public static final String  CONTENT_AUTHORITY   = "com.example.robert.stockfox.DatabaseContentProvider";
    public static final Uri     BASE_CONTENT_URI    = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String  STOCK_PATH          = DatabaseContract.STOCK_PATH;
    public static final Uri     CONTENT_URI         = BASE_CONTENT_URI.buildUpon().appendPath(STOCK_PATH).build();

    private static final int    ALL_STOCKS          = 0;
    private static final int    ONE_STOCK           = 1;
    private static final int    UI_UPDATE           = 2;
    private static final int    ONE_ID              = 3;


    private static final UriMatcher uriMatcher = getUriMatcher();

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, "stocks", ALL_STOCKS);
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, "stocks/symbol/*", ONE_STOCK);
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, "stocks/UI", UI_UPDATE);
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, "stocks/#", ONE_ID);



        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Log.e(TAG, "onCreate");
        databaseManager = new DatabaseManager(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        Log.e(TAG, "query() URI: " + uri.toString());
        /*
        Cursor query (Uri uri,
                String[] projection,
                String selection,
                String[] selectionArgs,
                String sortOrder)
        Query the given URI, returning a Cursor over the result set.

        For best performance, the caller should follow these guidelines:

        Provide an explicit projection, to prevent reading
        data from storage that aren't going to be used.

        Use question mark parameter markers such as 'phone=?' instead of explicit values in the
        selection parameter, so that queries that differ only by those values will be recognized
        as the same for caching purposes.

        Parameters
        ----------
        uri	Uri: The URI, using the content:// scheme, for the content to retrieve.
        projection	String: A list of which columns to return. Passing null will return all columns, which is inefficient.
        selection	String: A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
        selectionArgs	String: You may include ?s in selection, which will be replaced by the values from selectionArgs, in the order that they appear in the selection. The values will be bound as Strings.
        sortOrder	String: How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order, which may be unordered.

         */

        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.

        Cursor retCursor;
        switch (getUriMatcher().match(uri)) {

            case ALL_STOCKS: {
                Log.e(TAG, "query() ALL_STOCKS");
                retCursor = databaseManager.getReadableDatabase().query(
                        DatabaseContract.StockTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        DatabaseContract.StockTable._ID + " DESC"
                );
                break;
            }
            case ONE_STOCK: {
                Log.e(TAG, "query() ONE_STOCK");
                String stockSymbol = uri.getPathSegments().get(2);
                retCursor = databaseManager.getReadableDatabase().query(
                        DatabaseContract.StockTable.TABLE_NAME,
                        projection,
                        DatabaseContract.StockTable.SYMBOL + " = ?",
                        new String[]{stockSymbol},
                        null,
                        null,
                        null
                );
                break;
            }
            case ONE_ID: {
                Log.e(TAG, "query() ONE_ID");
                String id = uri.getPathSegments().get(1);
                retCursor = databaseManager.getReadableDatabase().query(
                        DatabaseContract.StockTable.TABLE_NAME,
                        projection,
                        DatabaseContract.StockTable._ID + " = ?",
                        new String[]{id},
                        null,
                        null,
                        null
                );
                break;
            }
            case UI_UPDATE: {
                Log.e(TAG, "query() UI_UPDATE");
                Log.e(TAG, DatabaseContract.StockTable.STOCK_ALL_KEYS_STRING);
                retCursor = databaseManager.getReadableDatabase().rawQuery(
                        "SELECT " + DatabaseContract.StockTable.STOCK_ALL_KEYS_STRING +
                        ", MAX(" + DatabaseContract.StockTable._ID + ")" +
                        " FROM " + DatabaseContract.StockTable.TABLE_NAME +
                        " GROUP BY " + DatabaseContract.StockTable.SYMBOL,
                        null);
                break;
            }
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.e(TAG, "getType() URI: " + uri.toString());

        // Some quick definitions to make the switch statement cleaner.
        final String DIR = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + STOCK_PATH;
        final String ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + STOCK_PATH;

        switch (uriMatcher.match(uri)) {
            case ALL_STOCKS:
                return DIR;
            case ONE_STOCK:
                return ITEM;
        }
        return "";
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.e(TAG, "insert() URI: " + uri.toString());

        final SQLiteDatabase db = databaseManager.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri returnUri;
        returnUri = uri;
        switch (match) {
            case ALL_STOCKS: {
                Log.e(TAG, "ALL_STOCKS case met");
                long _id = db.insert(DatabaseContract.StockTable.TABLE_NAME, null, contentValues);
                returnUri = Uri.withAppendedPath(uri, String.valueOf(_id));
                Log.e(TAG, "Inserting: " + String.valueOf(contentValues.get(DatabaseContract.StockTable.SYMBOL)) +
                " returned " + String.valueOf(_id));
                break;
            }
            case ONE_STOCK: {
                Log.e(TAG, "ONE_STOCK case met");
                long _id = db.insert(DatabaseContract.StockTable.TABLE_NAME, null, contentValues);
                returnUri = Uri.withAppendedPath(uri, String.valueOf(_id));
                break;
            }
            // More cases will follow, but for other tables.
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.e(TAG, "delete() URI: " + uri.toString());

        final SQLiteDatabase db = databaseManager.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case ONE_STOCK:
                rowsDeleted = db.delete(
                        DatabaseContract.StockTable.TABLE_NAME, selection, selectionArgs);
                break;
            // additional cases as necessary
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.e(TAG, "update() URI: " + uri.toString());
        final SQLiteDatabase db = databaseManager.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case ONE_STOCK:
                String movie = uri.getPathSegments().get(1);
                rowsUpdated = db.update(DatabaseContract.StockTable.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
//        if (rowsUpdated != 0) {
//            getContext().getContentResolver().notifyChange(uri, null);
//        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        Log.e(TAG, "bulkInsert() URI: " + uri.toString());
        final SQLiteDatabase db = databaseManager.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        switch (match) {
            case ALL_STOCKS:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(DatabaseContract.StockTable.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

}
