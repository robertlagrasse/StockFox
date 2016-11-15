package com.example.robert.stockfox;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by robert on 11/4/16.
 *
 * This is where utilities specific to this project will live.
 */

public class StockFoxUtils {

    public static String buildURLasString(Context mContext, String addSymbol){
        String TAG = "buildURLasString()";
        String urlString;
        StringBuilder urlStringBuilder = new StringBuilder();
        StringBuilder mStoredSymbols = new StringBuilder();
        Boolean symbolIsUnique = true;

        // Start with the base of the URL
        try{
            // Base URL for the Yahoo query
            urlStringBuilder.append("https://query.yahooapis.com/v1/public/yql?q=");
            urlStringBuilder.append(URLEncoder.encode("select * from yahoo.finance.quotes where symbol "
                    + "in (", "UTF-8"));
            Log.e(TAG, "urlStringBuilder = " + urlStringBuilder.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Check the database. Grab the unique symbols.
        Cursor initQueryCursor;
        initQueryCursor = mContext.getContentResolver().query(DatabaseContract.CONTENT_URI,
                new String[] { "Distinct " + DatabaseContract.StockTable.SYMBOL },
                null,
                null,
                null);

        // Extract the existing symbols. Make new request is unique.
        if (initQueryCursor != null) {
            initQueryCursor.moveToFirst();
            for (int i = 0; i < initQueryCursor.getCount(); i++){
                String symbol = initQueryCursor.getString(initQueryCursor.getColumnIndex(DatabaseContract.StockTable.SYMBOL));
                if (symbol.equals(addSymbol)) {
                    symbolIsUnique = false;
                    Log.e(TAG, symbol + " is not unique");
                    Toast.makeText(mContext, " already exists.", Toast.LENGTH_LONG).show();
                }
                mStoredSymbols.append("\""+ symbol +"\",");
                initQueryCursor.moveToNext();
            }
            initQueryCursor.close();
        }
        Log.e(TAG, "Existing symbols in the database: " + mStoredSymbols.toString());

        // Append any requested symbol to the list of existing symbols.
        if ((addSymbol != null) && (symbolIsUnique)){
            Log.e(TAG, "Appending unique, non null symbol " + addSymbol);
            mStoredSymbols.append("\"" + addSymbol + "\",");
        }
        Log.e(TAG, "All symbols to be requested: " + mStoredSymbols.toString());

        // If nothing was passed or in the DB, use these defaults
        if (mStoredSymbols.toString().length() == 0){
            mStoredSymbols.append("\"YHOO\",\"AAPL\",\"MSFT\",");
            Log.e(TAG, "No symbols found. Using defaults");
        }

        // Finalize symbol picks
        mStoredSymbols.replace(mStoredSymbols.length() - 1, mStoredSymbols.length(), ")");
        try {
            urlStringBuilder.append(URLEncoder.encode(mStoredSymbols.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // finalize the URL for the API query.
        urlStringBuilder.append("&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables."
                + "org%2Falltableswithkeys&callback=");

        urlString = urlStringBuilder.toString();
        Log.e(TAG, "Final request to Yahoo!: " + urlString);

        return urlString;
    }

    public static void JSONtoDB(String JSON, Context mContext){
        String TAG = "JSONtoDB";
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        JSONObject jsonObject = null;
        JSONArray resultsArray = null;
        try{
            jsonObject = new JSONObject(JSON);
            if (jsonObject != null && jsonObject.length() != 0){
                jsonObject = jsonObject.getJSONObject("query");
                int count = Integer.parseInt(jsonObject.getString("count"));
                if (count == 1){
                    jsonObject = jsonObject.getJSONObject("results").getJSONObject("quote");
                    Stock stock = new Stock(jsonObject);
                    if (stockIsValid(stock)){
                        ContentValues values = stockToContentValues(stock);
                        Uri insertedUri = mContext.getContentResolver().insert(
                                DatabaseContract.CONTENT_URI,
                                values
                        );
                    } else {
                        Log.e(TAG, stock.getSymbol() + " returned invalid data and was not inserted");
                    }
                } else {
                    resultsArray = jsonObject.getJSONObject("results").getJSONArray("quote");
                    if (resultsArray != null && resultsArray.length() != 0){
                        for (int i = 0; i < resultsArray.length(); i++){
                            jsonObject = resultsArray.getJSONObject(i);
                            Stock stock = new Stock(jsonObject);
                            if (stockIsValid(stock)){
                                ContentValues values = stockToContentValues(stock);
                                Uri insertedUri = mContext.getContentResolver().insert(
                                        DatabaseContract.CONTENT_URI,
                                        values
                                );
                            } else {
                                Log.e(TAG, stock.getSymbol() + " returned invalid data and was not inserted");
                            }
                        }
                    }
                }
            }
        } catch (JSONException e){
            Log.e(TAG, "JSON input could not be parsed. " + e);
        }
    }

    public static Boolean stockIsValid(Stock stock){
        // The Yahoo! API is unreliable. Sometimes it returns null values in the response where
        // names or numbers are expected. We also see null values returned when invalid stocks
        // are requested.

        String TAG = "SFU/stockIsValid()";
        Log.e(TAG, "getSymbol: " + stock.getSymbol());
        Log.e(TAG, "getBid: " + stock.getBid());
        Log.e(TAG, "getName: " + stock.getName());
        Log.e(TAG, "getOpen: " + stock.getOpen());
        Log.e(TAG, "getLastTradeDate: " + stock.getLastTradeDate());

        return ((!stock.getName().equals("null")) &&
                (!stock.getBid().equals("null")) &&
                (!stock.getOpen().equals("null")) &&
                (!stock.getLastTradeDate().equals("null")));
    }

    public static ContentValues stockToContentValues(Stock stock){
        ContentValues cv = new ContentValues();

        cv.put(DatabaseContract.StockTable.SYMBOL, stock.getSymbol());
        cv.put(DatabaseContract.StockTable.ASK, stock.getAsk());
        cv.put(DatabaseContract.StockTable.AVERAGEDAILYVOLUME, stock.getAverageDailyVolume());
        cv.put(DatabaseContract.StockTable.BID, stock.getBid());
        cv.put(DatabaseContract.StockTable.ASKREALTIME, stock.getAskRealtime());
        cv.put(DatabaseContract.StockTable.BIDREALTIME, stock.getBidRealtime());
        cv.put(DatabaseContract.StockTable.BOOKVALUE, stock.getBookValue());
        cv.put(DatabaseContract.StockTable.CHANGE_PERCENTCHANGE, stock.getChange_PercentChange());
        cv.put(DatabaseContract.StockTable.CHANGE, stock.getChange());
        cv.put(DatabaseContract.StockTable.COMMISSION, stock.getCommission());
        cv.put(DatabaseContract.StockTable.CURRENCY, stock.getCurrency());
        cv.put(DatabaseContract.StockTable.CHANGEREALTIME, stock.getChangeRealtime());
        cv.put(DatabaseContract.StockTable.AFTERHOURSCHANGEREALTIME, stock.getAfterHoursChangeRealtime());
        cv.put(DatabaseContract.StockTable.DIVIDENDSHARE, stock.getDividendShare());
        cv.put(DatabaseContract.StockTable.LASTTRADEDATE, stock.getLastTradeDate());
        cv.put(DatabaseContract.StockTable.TRADEDATE, stock.getTradeDate());
        cv.put(DatabaseContract.StockTable.EARNINGSSHARE, stock.getEarningsShare());
        cv.put(DatabaseContract.StockTable.ERRORINDICATIONRETURNEDFORSYMBOLCHANGEDINVALID, stock.getErrorIndicationreturnedforsymbolchangedinvalid());
        cv.put(DatabaseContract.StockTable.EPSESTIMATECURRENTYEAR, stock.getEPSEstimateCurrentYear());
        cv.put(DatabaseContract.StockTable.EPSESTIMATENEXTYEAR, stock.getEPSEstimateNextYear());
        cv.put(DatabaseContract.StockTable.EPSESTIMATENEXTQUARTER, stock.getEPSEstimateNextQuarter());
        cv.put(DatabaseContract.StockTable.DAYSLOW, stock.getDaysLow());
        cv.put(DatabaseContract.StockTable.DAYSHIGH, stock.getDaysHigh());
        cv.put(DatabaseContract.StockTable.YEARLOW, stock.getYearLow());
        cv.put(DatabaseContract.StockTable.YEARHIGH, stock.getYearHigh());
        cv.put(DatabaseContract.StockTable.HOLDINGSGAINPERCENT, stock.getHoldingsGainPercent());
        cv.put(DatabaseContract.StockTable.ANNUALIZEDGAIN, stock.getAnnualizedGain());
        cv.put(DatabaseContract.StockTable.HOLDINGSGAIN, stock.getHoldingsGain());
        cv.put(DatabaseContract.StockTable.HOLDINGSGAINPERCENTREALTIME, stock.getHoldingsGainPercentRealtime());
        cv.put(DatabaseContract.StockTable.HOLDINGSGAINREALTIME, stock.getHoldingsGainRealtime());
        cv.put(DatabaseContract.StockTable.MOREINFO, stock.getMoreInfo());
        cv.put(DatabaseContract.StockTable.ORDERBOOKREALTIME, stock.getOrderBookRealtime());
        cv.put(DatabaseContract.StockTable.MARKETCAPITALIZATION, stock.getMarketCapitalization());
        cv.put(DatabaseContract.StockTable.MARKETCAPREALTIME, stock.getMarketCapRealtime());
        cv.put(DatabaseContract.StockTable.EBITDA, stock.getEBITDA());
        cv.put(DatabaseContract.StockTable.CHANGEFROMYEARLOW, stock.getChangeFromYearLow());
        cv.put(DatabaseContract.StockTable.PERCENTCHANGEFROMYEARLOW, stock.getPercentChangeFromYearLow());
        cv.put(DatabaseContract.StockTable.LASTTRADEREALTIMEWITHTIME, stock.getLastTradeRealtimeWithTime());
        cv.put(DatabaseContract.StockTable.CHANGEPERCENTREALTIME, stock.getChangePercentRealtime());
        cv.put(DatabaseContract.StockTable.CHANGEFROMYEARHIGH, stock.getChangeFromYearHigh());
        cv.put(DatabaseContract.StockTable.PERCEBTCHANGEFROMYEARHIGH, stock.getPercebtChangeFromYearHigh());
        cv.put(DatabaseContract.StockTable.LASTTRADEWITHTIME, stock.getLastTradeWithTime());
        cv.put(DatabaseContract.StockTable.LASTTRADEPRICEONLY, stock.getLastTradePriceOnly());
        cv.put(DatabaseContract.StockTable.HIGHLIMIT, stock.getHighLimit());
        cv.put(DatabaseContract.StockTable.LOWLIMIT, stock.getLowLimit());
        cv.put(DatabaseContract.StockTable.DAYSRANGE, stock.getDaysRange());
        cv.put(DatabaseContract.StockTable.DAYSRANGEREALTIME, stock.getDaysRangeRealtime());
        cv.put(DatabaseContract.StockTable.FIFTYDAYMOVINGAVERAGE, stock.getFiftydayMovingAverage());
        cv.put(DatabaseContract.StockTable.TWOHUNDREDDAYMOVINGAVERAGE, stock.getTwoHundreddayMovingAverage());
        cv.put(DatabaseContract.StockTable.CHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE, stock.getChangeFromTwoHundreddayMovingAverage());
        cv.put(DatabaseContract.StockTable.PERCENTCHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE, stock.getPercentChangeFromTwoHundreddayMovingAverage());
        cv.put(DatabaseContract.StockTable.CHANGEFROMFIFTYDAYMOVINGAVERAGE, stock.getChangeFromFiftydayMovingAverage());
        cv.put(DatabaseContract.StockTable.PERCENTCHANGEFROMFIFTYDAYMOVINGAVERAGE, stock.getPercentChangeFromFiftydayMovingAverage());
        cv.put(DatabaseContract.StockTable.NAME, stock.getName());
        cv.put(DatabaseContract.StockTable.NOTES, stock.getNotes());
        cv.put(DatabaseContract.StockTable.OPEN, stock.getOpen());
        cv.put(DatabaseContract.StockTable.PREVIOUSCLOSE, stock.getPreviousClose());
        cv.put(DatabaseContract.StockTable.PRICEPAID, stock.getPricePaid());
        cv.put(DatabaseContract.StockTable.CHANGEINPERCENT, stock.getChangeinPercent());
        cv.put(DatabaseContract.StockTable.PRICESALES, stock.getPriceSales());
        cv.put(DatabaseContract.StockTable.PRICEBOOK, stock.getPriceBook());
        cv.put(DatabaseContract.StockTable.EXDIVIDENDDATE, stock.getExDividendDate());
        cv.put(DatabaseContract.StockTable.PERATIO, stock.getPERatio());
        cv.put(DatabaseContract.StockTable.DIVIDENDPAYDATE, stock.getDividendPayDate());
        cv.put(DatabaseContract.StockTable.PERATIOREALTIME, stock.getPERatioRealtime());
        cv.put(DatabaseContract.StockTable.PEGRATIO, stock.getPEGRatio());
        cv.put(DatabaseContract.StockTable.PRICEEPSESTIMATECURRENTYEAR, stock.getPriceEPSEstimateCurrentYear());
        cv.put(DatabaseContract.StockTable.PRICEEPSESTIMATENEXTYEAR, stock.getPriceEPSEstimateNextYear());
        cv.put(DatabaseContract.StockTable.SHARESOWNED, stock.getSharesOwned());
        cv.put(DatabaseContract.StockTable.SHORTRATIO, stock.getShortRatio());
        cv.put(DatabaseContract.StockTable.LASTTRADETIME, stock.getLastTradeTime());
        cv.put(DatabaseContract.StockTable.TICKERTREND, stock.getTickerTrend());
        cv.put(DatabaseContract.StockTable.ONEYRTARGETPRICE, stock.getOneyrTargetPrice());
        cv.put(DatabaseContract.StockTable.VOLUME, stock.getVolume());
        cv.put(DatabaseContract.StockTable.HOLDINGSVALUE, stock.getHoldingsValue());
        cv.put(DatabaseContract.StockTable.HOLDINGSVALUEREALTIME, stock.getHoldingsValueRealtime());
        cv.put(DatabaseContract.StockTable.YEARRANGE, stock.getYearRange());
        cv.put(DatabaseContract.StockTable.DAYSVALUECHANGE, stock.getDaysValueChange());
        cv.put(DatabaseContract.StockTable.DAYSVALUECHANGEREALTIME, stock.getDaysValueChangeRealtime());
        cv.put(DatabaseContract.StockTable.STOCKEXCHANGE, stock.getStockExchange());
        cv.put(DatabaseContract.StockTable.DIVIDENDYIELD, stock.getDividendYield());
        cv.put(DatabaseContract.StockTable.PERCENTCHANGE, stock.getPercentChange());
        return cv;
    }

}
