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

/**
 * Created by robert on 11/10/16.
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
        Stock stock = new Stock();

        Log.e(TAG, "ID assigned to this object: " + id);
        // Look in the database to identify the last movie the user selected
        cursor = getActivity().getContentResolver().query(
                DatabaseContract.CONTENT_URI.buildUpon().appendPath(id).build(),
                null,
                null,
                null,
                null);

        cursor.moveToFirst();

        // TODO: Convert this mess into a Stock constructor that accepts a cursor. ala the JSON constructor

        stock.setSymbol(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.SYMBOL)));
        stock.setAsk(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.ASK)));
        stock.setAverageDailyVolume(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.AVERAGEDAILYVOLUME)));
        stock.setBid(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.BID)));
        stock.setAskRealtime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.ASKREALTIME)));
        stock.setBidRealtime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.BIDREALTIME)));
        stock.setBookValue(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.BOOKVALUE)));
        stock.setChange_PercentChange(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.CHANGE_PERCENTCHANGE)));
        stock.setChange(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.CHANGE)));
        stock.setCommission(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.COMMISSION)));
        stock.setCurrency(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.CURRENCY)));
        stock.setChangeRealtime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.CHANGEREALTIME)));
        stock.setAfterHoursChangeRealtime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.AFTERHOURSCHANGEREALTIME)));
        stock.setDividendShare(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.DIVIDENDSHARE)));
        stock.setLastTradeDate(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.LASTTRADEDATE)));
        stock.setTradeDate(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.TRADEDATE)));
        stock.setEarningsShare(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.EARNINGSSHARE)));
        stock.setErrorIndicationreturnedforsymbolchangedinvalid(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.ERRORINDICATIONRETURNEDFORSYMBOLCHANGEDINVALID)));
        stock.setEPSEstimateCurrentYear(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.EPSESTIMATECURRENTYEAR)));
        stock.setEPSEstimateNextYear(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.EPSESTIMATENEXTYEAR)));
        stock.setEPSEstimateNextQuarter(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.EPSESTIMATENEXTQUARTER)));
        stock.setDaysLow(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.DAYSLOW)));
        stock.setDaysHigh(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.DAYSHIGH)));
        stock.setYearLow(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.YEARLOW)));
        stock.setYearHigh(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.YEARHIGH)));
        stock.setHoldingsGainPercent(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.HOLDINGSGAINPERCENT)));
        stock.setAnnualizedGain(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.ANNUALIZEDGAIN)));
        stock.setHoldingsGain(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.HOLDINGSGAIN)));
        stock.setHoldingsGainPercentRealtime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.HOLDINGSGAINPERCENTREALTIME)));
        stock.setHoldingsGainRealtime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.HOLDINGSGAINREALTIME)));
        stock.setMoreInfo(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.MOREINFO)));
        stock.setOrderBookRealtime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.ORDERBOOKREALTIME)));
        stock.setMarketCapitalization(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.MARKETCAPITALIZATION)));
        stock.setMarketCapRealtime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.MARKETCAPREALTIME)));
        stock.setEBITDA(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.EBITDA)));
        stock.setChangeFromYearLow(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.CHANGEFROMYEARLOW)));
        stock.setPercentChangeFromYearLow(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PERCENTCHANGEFROMYEARLOW)));
        stock.setLastTradeRealtimeWithTime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.LASTTRADEREALTIMEWITHTIME)));
        stock.setChangePercentRealtime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.CHANGEPERCENTREALTIME)));
        stock.setChangeFromYearHigh(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.CHANGEFROMYEARHIGH)));
        stock.setPercebtChangeFromYearHigh(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PERCEBTCHANGEFROMYEARHIGH)));
        stock.setLastTradeWithTime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.LASTTRADEWITHTIME)));
        stock.setLastTradePriceOnly(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.LASTTRADEPRICEONLY)));
        stock.setHighLimit(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.HIGHLIMIT)));
        stock.setLowLimit(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.LOWLIMIT)));
        stock.setDaysRange(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.DAYSRANGE)));
        stock.setDaysRangeRealtime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.DAYSRANGEREALTIME)));
        stock.setFiftydayMovingAverage(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.FIFTYDAYMOVINGAVERAGE)));
        stock.setTwoHundreddayMovingAverage(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.TWOHUNDREDDAYMOVINGAVERAGE)));
        stock.setChangeFromTwoHundreddayMovingAverage(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.CHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE)));
        stock.setPercentChangeFromTwoHundreddayMovingAverage(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PERCENTCHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE)));
        stock.setChangeFromFiftydayMovingAverage(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.CHANGEFROMFIFTYDAYMOVINGAVERAGE)));
        stock.setPercentChangeFromFiftydayMovingAverage(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PERCENTCHANGEFROMFIFTYDAYMOVINGAVERAGE)));
        stock.setName(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.NAME)));
        stock.setNotes(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.NOTES)));
        stock.setOpen(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.OPEN)));
        stock.setPreviousClose(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PREVIOUSCLOSE)));
        stock.setPricePaid(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PRICEPAID)));
        stock.setChangeinPercent(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.CHANGEINPERCENT)));
        stock.setPriceSales(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PRICESALES)));
        stock.setPriceBook(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PRICEBOOK)));
        stock.setExDividendDate(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.EXDIVIDENDDATE)));
        stock.setPERatio(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PERATIO)));
        stock.setDividendPayDate(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.DIVIDENDPAYDATE)));
        stock.setPERatioRealtime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PERATIOREALTIME)));
        stock.setPEGRatio(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PEGRATIO)));
        stock.setPriceEPSEstimateCurrentYear(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PRICEEPSESTIMATECURRENTYEAR)));
        stock.setPriceEPSEstimateNextYear(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PRICEEPSESTIMATENEXTYEAR)));
        stock.setSharesOwned(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.SHARESOWNED)));
        stock.setShortRatio(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.SHORTRATIO)));
        stock.setLastTradeTime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.LASTTRADETIME)));
        stock.setTickerTrend(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.TICKERTREND)));
        stock.setOneyrTargetPrice(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.ONEYRTARGETPRICE)));
        stock.setVolume(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.VOLUME)));
        stock.setHoldingsValue(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.HOLDINGSVALUE)));
        stock.setHoldingsValueRealtime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.HOLDINGSVALUEREALTIME)));
        stock.setYearRange(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.YEARRANGE)));
        stock.setDaysValueChange(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.DAYSVALUECHANGE)));
        stock.setDaysValueChangeRealtime(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.DAYSVALUECHANGEREALTIME)));
        stock.setStockExchange(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.STOCKEXCHANGE)));
        stock.setDividendYield(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.DIVIDENDYIELD)));
        stock.setPercentChange(cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.PERCENTCHANGE)));

        cursor.close();

        TextView tvId = (TextView) rootView.findViewById(R.id.text_id);
        tvId.setText("ID: " + id);

        TextView tvName = (TextView) rootView.findViewById(R.id.text_name);
        tvName.setText("Name: " + stock.getName());

        TextView tvSymbol = (TextView) rootView.findViewById(R.id.text_symbol);
        tvSymbol.setText("Symbol: " + stock.getSymbol());
        Log.e(TAG, "Symbol: " + stock.getSymbol());

        TextView tvBid = (TextView) rootView.findViewById(R.id.text_bid);
        tvBid.setText("Bid: " + stock.getBid());

        cursor = getActivity().getContentResolver().query(
                DatabaseContract.CONTENT_URI.buildUpon().appendPath("symbol").appendPath(stock.getSymbol()).build(),
                null,
                null,
                null,
                null);

        cursor.moveToFirst();

        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++){
                // Append each symbol found by the cursor, surrounded by quotes, to StringBuilder mStoredSymbols
                Log.e(TAG, "Bid: "+cursor.getString(cursor.getColumnIndex(DatabaseContract.StockTable.BID)));
                cursor.moveToNext();
            }
            cursor.close();
        }

        return rootView;
    }

    public void setId(String id) {
        this.id = id;
    }
}
