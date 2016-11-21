package com.example.robert.stockfox;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.icu.math.BigDecimal;
import android.icu.text.DecimalFormat;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by robert on 11/16/16.
 *
 * This class takes updating the data for the widget off of the UI Thread
 *
 */

public class WidgetIntentService extends IntentService {
    String TAG = "WidgetIntentService";

    public WidgetIntentService(){
        super("WidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, StockWidget.class));

        Cursor initQueryCursor;
        initQueryCursor = getContentResolver().query(DatabaseContract.buildUiUpdateUri(),
                null,
                null,
                null,
                null);

        // Extract required data from cursor
        int listLength = initQueryCursor.getCount();

        String[] symbolList = new String[listLength];
        String[] bidList = new String[listLength];
        String[] diffList = new String[listLength];
        String[] colorList = new String[listLength];


        if (initQueryCursor != null) {
            initQueryCursor.moveToFirst();
            for (int i = 0; i < initQueryCursor.getCount(); i++) {
                // extract data for each element
                String symbol = initQueryCursor.getString(initQueryCursor.getColumnIndex(DatabaseContract.StockTable.SYMBOL));
                float open  = Float.parseFloat(initQueryCursor.getString(initQueryCursor.getColumnIndex(DatabaseContract.StockTable.OPEN)));
                float bid = Float.parseFloat(initQueryCursor.getString(initQueryCursor.getColumnIndex(DatabaseContract.StockTable.BID)));

                // Add symbol to symbolList
                symbolList[i]=symbol;
                bidList[i] = String.valueOf(bid);
                if (bid>open){
                    diffList[i] = String.format("+%.2f", bid-open);
                    colorList[i] = "<font color=#00ff00>";
                }else {
                    diffList[i] = String.format("%.2f", bid-open);
                    colorList[i] = "<font color=#ff0000>";
                }
                initQueryCursor.moveToNext();
            }
            initQueryCursor.close();
        }


        // Loop through widgetIds
        for (int appWidgetId : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.stock_widget);
            String scrollText = "";
            for (int i=0; i<listLength; ++i){
                scrollText += "<font color=#FFFFFF>   |   </font>" +
                        colorList[i] +
                        symbolList[i] +
                        "   " +
                        diffList[i] +
                        "   " +
                        bidList[i] +
                        "</font>";
            }
            remoteViews.setTextViewText(R.id.widget_stock0, Html.fromHtml(scrollText));

            Intent launchIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }
}
