package com.example.robert.stockfox;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by robert on 11/10/16.
 *
 * This fragment displays information about the specific stock the user selected.
 * Once instantiated, setId() method should be called so the fragment knows where to
 * look in the database. id indicates the record number associated with a particular stock.
 *
 * onCreateView grabs the row with _id id, and builds a stock object from the data it finds.
 *
 * UI elements are then populated with the data found in the newly created stock.
 *
 * Another inquiry is then sent to the database to grab historical data on the stock.
 *
 * My goal in plotting the stock data was to plot two datapoints for each trading day -
 * the opening price, and the last bid price each day. Opening price is provided by the API
 * every time we make a request. Closing price is just the last bid of the day.
 *
 * A query sent to the content provider with a with graph/symbol in the path will return
 * the row with the highest _ID for each day for the symbol. In other words, the last update
 * each day for the requested stock. From that, we iterate through the rows and build a set of
 * data points to be plotted.
 *
 * Because we're plotting opening and closing values, there are necessarily twice as many
 * datapoints to be plotted as their are rows returned by the cursor. The
 * valuePosition = ((x+1)*2)-2; statement allows me to keep track of the element I need
 * to assign in the datapoints array as I iterate through the cursor.
 *
 * I used midnight and noon as the time stamps to evenly space out the two datapoints per
 * day.
 *
 */

public class GenericDetailFragment extends Fragment {
    private String id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.generic_detail_fragment, container, false);

        String TAG = "GenericDetailFragment";
        Cursor cursor;

        cursor = getActivity().getContentResolver().query(
                DatabaseContract.CONTENT_URI.buildUpon().appendPath(id).build(),
                null,
                null,
                null,
                null);

        Stock stock = new Stock(cursor);

        TextView tvId = (TextView) rootView.findViewById(R.id.text_id);
        tvId.setText("ID: " + id);

        TextView tvName = (TextView) rootView.findViewById(R.id.text_name);
        tvName.setText("Name: " + stock.getName());

        TextView tvSymbol = (TextView) rootView.findViewById(R.id.text_symbol);
        tvSymbol.setText("Symbol: " + stock.getSymbol());
        Log.e(TAG, "Symbol: " + stock.getSymbol());

        TextView tvBid = (TextView) rootView.findViewById(R.id.text_bid);
        tvBid.setText("Bid: " + stock.getLastTradeDate());

        cursor = getActivity().getContentResolver().query(
                DatabaseContract.CONTENT_URI.buildUpon().appendPath("graph").appendPath(stock.getSymbol()).build(),
                null,
                null,
                null,
                null);

        GraphView graphView = (GraphView) rootView.findViewById(R.id.graph);

        DataPoint[] dataPoints = new DataPoint[cursor.getCount()*2];

        if (cursor != null) {
            cursor.moveToFirst();
            for (int x = 0; x < cursor.getCount(); x++){
                int valuePosition = ((x+1)*2)-2;
                // Append each symbol found by the cursor, surrounded by quotes, to StringBuilder mStoredSymbols
                String open = cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.OPEN));
                String close = cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.BID));
                String openDate = cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.LASTTRADEDATE)) + " 00:00:00";
                String closeDate = cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.LASTTRADEDATE)) + " 12:00:00";

                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                try {
                    Date startDate = dateFormat.parse(openDate);
                    Date endDate = dateFormat.parse(closeDate);
                    DataPoint value = new DataPoint(startDate, Double.parseDouble(open));
                    dataPoints[valuePosition] = value;
                    valuePosition++;
                    value = new DataPoint(endDate, Double.parseDouble(close));
                    dataPoints[valuePosition] = value;

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cursor.moveToNext();
            }
            cursor.close();
        }

        LineGraphSeries series = new LineGraphSeries<DataPoint>(dataPoints);
        graphView.addSeries(series);
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graphView.getGridLabelRenderer().setNumHorizontalLabels(4);

        return rootView;
    }

    public void setId(String id) {
        this.id = id;
    }
}
