package com.example.robert.stockfox;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by robert on 11/2/16.
 *
 * This class contains all of the Database Definitions. Table names, column names
 * table creation strings, authority information for the ContentProvider, etc.
 *
 * Tables for the stocks will contain all of the fields the Yahoo API sends down
 * with a standard request.
 *
 */

class DatabaseContract {

    // Some "global" stuff
    public static final String      API_KEY             = "";
    public static final int         DATABASE_VERSION    = 1;
    public static final String      DATABASE_NAME       = "database";


    // Define some SQL Data Types - makes table creation code cleaner and less error prone
    private static final String     VARCHAR_255         = " VARCHAR(255), ";
    private static final String     INTEGER             = " INTEGER, ";
    private static final String     FLOAT               = " FLOAT, ";
    private static final String     BOOLEAN             = " BOOLEAN, ";

    // This defines the content authority
    static final String CONTENT_AUTHORITY    = "com.example.robert.stockfox";

    // This is the base path for our URI
    private static final Uri BASE_CONTENT_URI        = Uri.parse("content://" + CONTENT_AUTHORITY);

    // This path matches the stocks table
    static final String STOCK_PATH = "stocks";
    static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(STOCK_PATH).build();

    public static Uri buildAllStocksUri() {
        return CONTENT_URI;
    }

    public static Uri buildStockBySymbolUri(String symbol){
        return CONTENT_URI.buildUpon().appendPath("symbol").appendPath(symbol).build();
    }

    public static Uri buildUiUpdateUri(){
        return CONTENT_URI.buildUpon().appendPath("UI").build();
    }

    public static Uri buildStockByIdUri(String id){
        return CONTENT_URI.buildUpon().appendPath(id).build();
    }

    public static Uri buildStockGraphUri(String symbol){
        return CONTENT_URI.buildUpon().appendPath("graph").appendPath(symbol).build();
    }

    static final class StockTable implements BaseColumns {
        /*
        This class defines the properties of each stock. This is identical to
        the fields as they come down in JSON from Yahoo.
     */
        static final String TABLE_NAME                                       = "stocks";
        static final String _ID                                              = "_id";
        static final String SYMBOL                                           = "symbol";
        static final String ASK                                              = "Ask";
        static final String AVERAGEDAILYVOLUME                               = "AverageDailyVolume";
        static final String BID                                              = "Bid";
        static final String ASKREALTIME                                      = "AskRealtime";
        static final String BIDREALTIME                                      = "BidRealtime";
        static final String BOOKVALUE                                        = "BookValue";
        static final String CHANGE_PERCENTCHANGE                             = "Change_PercentChange";
        static final String CHANGE                                           = "Change";
        static final String COMMISSION                                       = "Commission";
        static final String CURRENCY                                         = "Currency";
        static final String CHANGEREALTIME                                   = "ChangeRealtime";
        static final String AFTERHOURSCHANGEREALTIME                         = "AfterHoursChangeRealtime";
        static final String DIVIDENDSHARE                                    = "DividendShare";
        static final String LASTTRADEDATE                                    = "LastTradeDate";
        static final String TRADEDATE                                        = "TradeDate";
        static final String EARNINGSSHARE                                    = "EarningsShare";
        static final String ERRORINDICATIONRETURNEDFORSYMBOLCHANGEDINVALID   = "ErrorIndicationreturnedforsymbolchangedinvalid";
        static final String EPSESTIMATECURRENTYEAR                           = "EPSEstimateCurrentYear";
        static final String EPSESTIMATENEXTYEAR                              = "EPSEstimateNextYear";
        static final String EPSESTIMATENEXTQUARTER                           = "EPSEstimateNextQuarter";
        static final String DAYSLOW                                          = "DaysLow";
        static final String DAYSHIGH                                         = "DaysHigh";
        static final String YEARLOW                                          = "YearLow";
        static final String YEARHIGH                                         = "YearHigh";
        static final String HOLDINGSGAINPERCENT                              = "HoldingsGainPercent";
        static final String ANNUALIZEDGAIN                                   = "AnnualizedGain";
        static final String HOLDINGSGAIN                                     = "HoldingsGain";
        static final String HOLDINGSGAINPERCENTREALTIME                      = "HoldingsGainPercentRealtime";
        static final String HOLDINGSGAINREALTIME                             = "HoldingsGainRealtime";
        static final String MOREINFO                                         = "MoreInfo";
        static final String ORDERBOOKREALTIME                                = "OrderBookRealtime";
        static final String MARKETCAPITALIZATION                             = "MarketCapitalization";
        static final String MARKETCAPREALTIME                                = "MarketCapRealtime";
        static final String EBITDA                                           = "EBITDA";
        static final String CHANGEFROMYEARLOW                                = "ChangeFromYearLow";
        static final String PERCENTCHANGEFROMYEARLOW                         = "PercentChangeFromYearLow";
        static final String LASTTRADEREALTIMEWITHTIME                        = "LastTradeRealtimeWithTime";
        static final String CHANGEPERCENTREALTIME                            = "ChangePercentRealtime";
        static final String CHANGEFROMYEARHIGH                               = "ChangeFromYearHigh";
        static final String PERCEBTCHANGEFROMYEARHIGH                        = "PercebtChangeFromYearHigh";
        static final String LASTTRADEWITHTIME                                = "LastTradeWithTime";
        static final String LASTTRADEPRICEONLY                               = "LastTradePriceOnly";
        static final String HIGHLIMIT                                        = "HighLimit";
        static final String LOWLIMIT                                         = "LowLimit";
        static final String DAYSRANGE                                        = "DaysRange";
        static final String DAYSRANGEREALTIME                                = "DaysRangeRealtime";
        static final String FIFTYDAYMOVINGAVERAGE                            = "FiftydayMovingAverage";
        static final String TWOHUNDREDDAYMOVINGAVERAGE                       = "TwoHundreddayMovingAverage";
        static final String CHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE             = "ChangeFromTwoHundreddayMovingAverage";
        static final String PERCENTCHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE      = "PercentChangeFromTwoHundreddayMovingAverage";
        static final String CHANGEFROMFIFTYDAYMOVINGAVERAGE                  = "ChangeFromFiftydayMovingAverage";
        static final String PERCENTCHANGEFROMFIFTYDAYMOVINGAVERAGE           = "PercentChangeFromFiftydayMovingAverage";
        static final String NAME                                             = "Name";
        static final String NOTES                                            = "Notes";
        static final String OPEN                                             = "Open";
        static final String PREVIOUSCLOSE                                    = "PreviousClose";
        static final String PRICEPAID                                        = "PricePaid";
        static final String CHANGEINPERCENT                                  = "ChangeinPercent";
        static final String PRICESALES                                       = "PriceSales";
        static final String PRICEBOOK                                        = "PriceBook";
        static final String EXDIVIDENDDATE                                   = "ExDividendDate";
        static final String PERATIO                                          = "PERatio";
        static final String DIVIDENDPAYDATE                                  = "DividendPayDate";
        static final String PERATIOREALTIME                                  = "PERatioRealtime";
        static final String PEGRATIO                                         = "PEGRatio";
        static final String PRICEEPSESTIMATECURRENTYEAR                      = "PriceEPSEstimateCurrentYear";
        static final String PRICEEPSESTIMATENEXTYEAR                         = "PriceEPSEstimateNextYear";
        static final String SHARESOWNED                                      = "SharesOwned";
        static final String SHORTRATIO                                       = "ShortRatio";
        static final String LASTTRADETIME                                    = "LastTradeTime";
        static final String TICKERTREND                                      = "TickerTrend";
        static final String ONEYRTARGETPRICE                                 = "OneyrTargetPrice";
        static final String VOLUME                                           = "Volume";
        static final String HOLDINGSVALUE                                    = "HoldingsValue";
        static final String HOLDINGSVALUEREALTIME                            = "HoldingsValueRealtime";
        static final String YEARRANGE                                        = "YearRange";
        static final String DAYSVALUECHANGE                                  = "DaysValueChange";
        static final String DAYSVALUECHANGEREALTIME                          = "DaysValueChangeRealtime";
        static final String STOCKEXCHANGE                                    = "StockExchange";
        static final String DIVIDENDYIELD                                    = "DividendYield";
        static final String PERCENTCHANGE                                    = "PercentChange";


        // Handy string array for when you need to refer to all columns
        public static final String[] STOCK_ALL_KEYS = new String[] {
                SYMBOL,
                _ID,
                ASK,
                AVERAGEDAILYVOLUME,
                BID,
                ASKREALTIME,
                BIDREALTIME,
                BOOKVALUE,
                CHANGE_PERCENTCHANGE,
                CHANGE,
                COMMISSION,
                CURRENCY,
                CHANGEREALTIME,
                AFTERHOURSCHANGEREALTIME,
                DIVIDENDSHARE,
                LASTTRADEDATE,
                TRADEDATE,
                EARNINGSSHARE,
                ERRORINDICATIONRETURNEDFORSYMBOLCHANGEDINVALID,
                EPSESTIMATECURRENTYEAR,
                EPSESTIMATENEXTYEAR,
                EPSESTIMATENEXTQUARTER,
                DAYSLOW,
                DAYSHIGH,
                YEARLOW,
                YEARHIGH,
                HOLDINGSGAINPERCENT,
                ANNUALIZEDGAIN,
                HOLDINGSGAIN,
                HOLDINGSGAINPERCENTREALTIME,
                HOLDINGSGAINREALTIME,
                MOREINFO,
                ORDERBOOKREALTIME,
                MARKETCAPITALIZATION,
                MARKETCAPREALTIME,
                EBITDA,
                CHANGEFROMYEARLOW,
                PERCENTCHANGEFROMYEARLOW,
                LASTTRADEREALTIMEWITHTIME,
                CHANGEPERCENTREALTIME,
                CHANGEFROMYEARHIGH,
                PERCEBTCHANGEFROMYEARHIGH,
                LASTTRADEWITHTIME,
                LASTTRADEPRICEONLY,
                HIGHLIMIT,
                LOWLIMIT,
                DAYSRANGE,
                DAYSRANGEREALTIME,
                FIFTYDAYMOVINGAVERAGE,
                TWOHUNDREDDAYMOVINGAVERAGE,
                CHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE,
                PERCENTCHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE,
                CHANGEFROMFIFTYDAYMOVINGAVERAGE,
                PERCENTCHANGEFROMFIFTYDAYMOVINGAVERAGE,
                NAME,
                NOTES,
                OPEN,
                PREVIOUSCLOSE,
                PRICEPAID,
                CHANGEINPERCENT,
                PRICESALES,
                PRICEBOOK,
                EXDIVIDENDDATE,
                PERATIO,
                DIVIDENDPAYDATE,
                PERATIOREALTIME,
                PEGRATIO,
                PRICEEPSESTIMATECURRENTYEAR,
                PRICEEPSESTIMATENEXTYEAR,
                SYMBOL,
                SHARESOWNED,
                SHORTRATIO,
                LASTTRADETIME,
                TICKERTREND,
                ONEYRTARGETPRICE,
                VOLUME,
                HOLDINGSVALUE,
                HOLDINGSVALUEREALTIME,
                YEARRANGE,
                DAYSVALUECHANGE,
                DAYSVALUECHANGEREALTIME,
                STOCKEXCHANGE,
                DIVIDENDYIELD,
                PERCENTCHANGE};

        public static final String STOCK_ALL_KEYS_STRING =
                SYMBOL + ","+
                        _ID + ","+
                        ASK + ","+
                        AVERAGEDAILYVOLUME + ","+
                        BID + ","+
                        ASKREALTIME + ","+
                        BIDREALTIME + ","+
                        BOOKVALUE + ","+
                        CHANGE_PERCENTCHANGE + ","+
                        CHANGE + ","+
                        COMMISSION + ","+
                        CURRENCY + ","+
                        CHANGEREALTIME + ","+
                        AFTERHOURSCHANGEREALTIME + ","+
                        DIVIDENDSHARE + ","+
                        LASTTRADEDATE + ","+
                        TRADEDATE + ","+
                        EARNINGSSHARE + ","+
                        ERRORINDICATIONRETURNEDFORSYMBOLCHANGEDINVALID + ","+
                        EPSESTIMATECURRENTYEAR + ","+
                        EPSESTIMATENEXTYEAR + ","+
                        EPSESTIMATENEXTQUARTER + ","+
                        DAYSLOW + ","+
                        DAYSHIGH + ","+
                        YEARLOW + ","+
                        YEARHIGH + ","+
                        HOLDINGSGAINPERCENT + ","+
                        ANNUALIZEDGAIN + ","+
                        HOLDINGSGAIN + ","+
                        HOLDINGSGAINPERCENTREALTIME + ","+
                        HOLDINGSGAINREALTIME + ","+
                        MOREINFO + ","+
                        ORDERBOOKREALTIME + ","+
                        MARKETCAPITALIZATION + ","+
                        MARKETCAPREALTIME + ","+
                        EBITDA + ","+
                        CHANGEFROMYEARLOW + ","+
                        PERCENTCHANGEFROMYEARLOW + ","+
                        LASTTRADEREALTIMEWITHTIME + ","+
                        CHANGEPERCENTREALTIME + ","+
                        CHANGEFROMYEARHIGH + ","+
                        PERCEBTCHANGEFROMYEARHIGH + ","+
                        LASTTRADEWITHTIME + ","+
                        LASTTRADEPRICEONLY + ","+
                        HIGHLIMIT + ","+
                        LOWLIMIT + ","+
                        DAYSRANGE + ","+
                        DAYSRANGEREALTIME + ","+
                        FIFTYDAYMOVINGAVERAGE + ","+
                        TWOHUNDREDDAYMOVINGAVERAGE + ","+
                        CHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE + ","+
                        PERCENTCHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE + ","+
                        CHANGEFROMFIFTYDAYMOVINGAVERAGE + ","+
                        PERCENTCHANGEFROMFIFTYDAYMOVINGAVERAGE + ","+
                        NAME + ","+
                        NOTES + ","+
                        OPEN + ","+
                        PREVIOUSCLOSE + ","+
                        PRICEPAID + ","+
                        CHANGEINPERCENT + ","+
                        PRICESALES + ","+
                        PRICEBOOK + ","+
                        EXDIVIDENDDATE + ","+
                        PERATIO + ","+
                        DIVIDENDPAYDATE + ","+
                        PERATIOREALTIME + ","+
                        PEGRATIO + ","+
                        PRICEEPSESTIMATECURRENTYEAR + ","+
                        PRICEEPSESTIMATENEXTYEAR + ","+
                        SYMBOL + ","+
                        SHARESOWNED + ","+
                        SHORTRATIO + ","+
                        LASTTRADETIME + ","+
                        TICKERTREND + ","+
                        ONEYRTARGETPRICE + ","+
                        VOLUME + ","+
                        HOLDINGSVALUE + ","+
                        HOLDINGSVALUEREALTIME + ","+
                        YEARRANGE + ","+
                        DAYSVALUECHANGE + ","+
                        DAYSVALUECHANGEREALTIME + ","+
                        STOCKEXCHANGE + ","+
                        DIVIDENDYIELD + ","+
                        PERCENTCHANGE;

        // SQL Create table command
        static final String CREATE_TABLE               =
                "CREATE TABLE "            +
                        TABLE_NAME                 + "(" +
                        _ID                 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SYMBOL + VARCHAR_255 +
                        ASK + VARCHAR_255 +
                        AVERAGEDAILYVOLUME + VARCHAR_255 +
                        BID + VARCHAR_255 +
                        ASKREALTIME + VARCHAR_255 +
                        BIDREALTIME + VARCHAR_255 +
                        BOOKVALUE + VARCHAR_255 +
                        CHANGE_PERCENTCHANGE + VARCHAR_255 +
                        CHANGE + VARCHAR_255 +
                        COMMISSION + VARCHAR_255 +
                        CURRENCY + VARCHAR_255 +
                        CHANGEREALTIME + VARCHAR_255 +
                        AFTERHOURSCHANGEREALTIME + VARCHAR_255 +
                        DIVIDENDSHARE + VARCHAR_255 +
                        LASTTRADEDATE + VARCHAR_255 +
                        TRADEDATE + VARCHAR_255 +
                        EARNINGSSHARE + VARCHAR_255 +
                        ERRORINDICATIONRETURNEDFORSYMBOLCHANGEDINVALID + VARCHAR_255 +
                        EPSESTIMATECURRENTYEAR + VARCHAR_255 +
                        EPSESTIMATENEXTYEAR + VARCHAR_255 +
                        EPSESTIMATENEXTQUARTER + VARCHAR_255 +
                        DAYSLOW + VARCHAR_255 +
                        DAYSHIGH + VARCHAR_255 +
                        YEARLOW + VARCHAR_255 +
                        YEARHIGH + VARCHAR_255 +
                        HOLDINGSGAINPERCENT + VARCHAR_255 +
                        ANNUALIZEDGAIN + VARCHAR_255 +
                        HOLDINGSGAIN + VARCHAR_255 +
                        HOLDINGSGAINPERCENTREALTIME + VARCHAR_255 +
                        HOLDINGSGAINREALTIME + VARCHAR_255 +
                        MOREINFO + VARCHAR_255 +
                        ORDERBOOKREALTIME + VARCHAR_255 +
                        MARKETCAPITALIZATION + VARCHAR_255 +
                        MARKETCAPREALTIME + VARCHAR_255 +
                        EBITDA + VARCHAR_255 +
                        CHANGEFROMYEARLOW + VARCHAR_255 +
                        PERCENTCHANGEFROMYEARLOW + VARCHAR_255 +
                        LASTTRADEREALTIMEWITHTIME + VARCHAR_255 +
                        CHANGEPERCENTREALTIME + VARCHAR_255 +
                        CHANGEFROMYEARHIGH + VARCHAR_255 +
                        PERCEBTCHANGEFROMYEARHIGH + VARCHAR_255 +
                        LASTTRADEWITHTIME + VARCHAR_255 +
                        LASTTRADEPRICEONLY + VARCHAR_255 +
                        HIGHLIMIT + VARCHAR_255 +
                        LOWLIMIT + VARCHAR_255 +
                        DAYSRANGE + VARCHAR_255 +
                        DAYSRANGEREALTIME + VARCHAR_255 +
                        FIFTYDAYMOVINGAVERAGE + VARCHAR_255 +
                        TWOHUNDREDDAYMOVINGAVERAGE + VARCHAR_255 +
                        CHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE + VARCHAR_255 +
                        PERCENTCHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE + VARCHAR_255 +
                        CHANGEFROMFIFTYDAYMOVINGAVERAGE + VARCHAR_255 +
                        PERCENTCHANGEFROMFIFTYDAYMOVINGAVERAGE + VARCHAR_255 +
                        NAME + VARCHAR_255 +
                        NOTES + VARCHAR_255 +
                        OPEN + VARCHAR_255 +
                        PREVIOUSCLOSE + VARCHAR_255 +
                        PRICEPAID + VARCHAR_255 +
                        CHANGEINPERCENT + VARCHAR_255 +
                        PRICESALES + VARCHAR_255 +
                        PRICEBOOK + VARCHAR_255 +
                        EXDIVIDENDDATE + VARCHAR_255 +
                        PERATIO + VARCHAR_255 +
                        DIVIDENDPAYDATE + VARCHAR_255 +
                        PERATIOREALTIME + VARCHAR_255 +
                        PEGRATIO + VARCHAR_255 +
                        PRICEEPSESTIMATECURRENTYEAR + VARCHAR_255 +
                        PRICEEPSESTIMATENEXTYEAR + VARCHAR_255 +
                        SHARESOWNED + VARCHAR_255 +
                        SHORTRATIO + VARCHAR_255 +
                        LASTTRADETIME + VARCHAR_255 +
                        TICKERTREND + VARCHAR_255 +
                        ONEYRTARGETPRICE + VARCHAR_255 +
                        VOLUME + VARCHAR_255 +
                        HOLDINGSVALUE + VARCHAR_255 +
                        HOLDINGSVALUEREALTIME + VARCHAR_255 +
                        YEARRANGE + VARCHAR_255 +
                        DAYSVALUECHANGE + VARCHAR_255 +
                        DAYSVALUECHANGEREALTIME + VARCHAR_255 +
                        STOCKEXCHANGE + VARCHAR_255 +
                        DIVIDENDYIELD + VARCHAR_255 +
                        PERCENTCHANGE + VARCHAR_255 +
                        "UNIQUE ("+ _ID +") ON CONFLICT IGNORE);";
    }
}
