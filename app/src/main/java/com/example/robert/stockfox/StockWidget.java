package com.example.robert.stockfox;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 *
 * Holds logic to:
 * create views
 * fill them
 * click listeners
 *
 * Widget Configuration: xml/stock_widget_info
 * Layout: layout/stock_widget
 *
 */
public class StockWidget extends AppWidgetProvider {
    String TAG = "StockWidget";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e(TAG, "onUpdate()");
        Intent widgetIntentService = new Intent(context, WidgetIntentService.class);
        context.startService(widgetIntentService);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.e(TAG, "onEnabled()");

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.e(TAG, "onDisabled()");

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e(TAG, "onReceive() intent.getAction().toString():" + intent.getAction().toString());
        Intent widgetIntentService = new Intent(context, WidgetIntentService.class);
        context.startService(widgetIntentService);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        context.startService(new Intent(context, WidgetIntentService.class));
        Log.e(TAG, "onAppWidgetOptionsChanged()");

    }
}

