package com.example.robert.stockfox;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
    public static final Uri     ALL_STOCKS_URI      = CONTENT_URI;


    private static final int    ALL_STOCKS          = 0;
    private static final int    STOCK_BY_SYMBOL     = 1;
    private static final int    UI_UPDATE           = 2;
    private static final int    ONE_ID              = 3;
    private static final int    UI_GRAPH            = 4;


    private static final UriMatcher uriMatcher = getUriMatcher();

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, "stocks", ALL_STOCKS);
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, "stocks/symbol/*", STOCK_BY_SYMBOL);
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, "stocks/UI", UI_UPDATE);
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, "stocks/#", ONE_ID);
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, "stocks/graph/*", UI_GRAPH);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        databaseManager = new DatabaseManager(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        Cursor retCursor;
        switch (getUriMatcher().match(uri)) {

            case ALL_STOCKS: {
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
            case STOCK_BY_SYMBOL: {
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
                retCursor = databaseManager.getReadableDatabase().rawQuery(
                        "SELECT " + DatabaseContract.StockTable.STOCK_ALL_KEYS_STRING +
                        ", MAX(" + DatabaseContract.StockTable._ID + ")" +
                        " FROM " + DatabaseContract.StockTable.TABLE_NAME +
                        " GROUP BY " + DatabaseContract.StockTable.SYMBOL,
                        null);
                break;
            }
            case UI_GRAPH: {
                String stockSymbol = uri.getPathSegments().get(2);
                retCursor = databaseManager.getReadableDatabase().rawQuery(
                        "SELECT " + DatabaseContract.StockTable.STOCK_ALL_KEYS_STRING +
                                ", MAX(" + DatabaseContract.StockTable._ID + ")" +
                                " FROM " + DatabaseContract.StockTable.TABLE_NAME +
                                " WHERE " + DatabaseContract.StockTable.SYMBOL + " = \"" + stockSymbol + "\"" +
                                " GROUP BY " + DatabaseContract.StockTable.LASTTRADEDATE,
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
        // Some quick definitions to make the switch statement cleaner.
        final String DIR = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + STOCK_PATH;
        final String ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + STOCK_PATH;

        switch (uriMatcher.match(uri)) {
            case ALL_STOCKS:
                return DIR;
            case STOCK_BY_SYMBOL:
                return ITEM;
        }
        return "";
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = databaseManager.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri returnUri;
        returnUri = uri;
        switch (match) {
            case ALL_STOCKS: {
                long _id = db.insert(DatabaseContract.StockTable.TABLE_NAME, null, contentValues);
                returnUri = Uri.withAppendedPath(uri, String.valueOf(_id));
                break;
            }

            case STOCK_BY_SYMBOL: {
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

        final SQLiteDatabase db = databaseManager.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsDeleted;
        String stockSymbol = uri.getPathSegments().get(2);

        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case STOCK_BY_SYMBOL:
                rowsDeleted = db.delete(
                        DatabaseContract.StockTable.TABLE_NAME,
                        DatabaseContract.StockTable.SYMBOL + " = ?",
                        new String[]{stockSymbol});
                break;
            // additional cases as necessary
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = databaseManager.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case STOCK_BY_SYMBOL:
                String movie = uri.getPathSegments().get(1);
                rowsUpdated = db.update(DatabaseContract.StockTable.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
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
