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

public class DatabaseContract {

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
    public static final String CONTENT_AUTHORITY    = "com.example.robert.stockfox";

    // This is the base path for our URI
    public static final Uri BASE_CONTENT_URI        = Uri.parse("content://" + CONTENT_AUTHORITY);

    // This path matches the stocks table
    public static final String STOCK_PATH = "stocks";
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(STOCK_PATH).build();

    // Some public methods to help build ContentProvider requests
    // TODO: Build relevant methods for stock requests
    public static Uri buildMovieURI(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri buildUserSelectionURI() {
        return CONTENT_URI.buildUpon().appendPath("choice").build();
    }
    public static Uri buildPopularURI() {
        return CONTENT_URI.buildUpon().appendPath("popular").build();
    }
    public static Uri buildTopRatedURI() {
        return CONTENT_URI.buildUpon().appendPath("top_rated").build();
    }
    public static Uri buildFavoritesURI() {
        return CONTENT_URI.buildUpon().appendPath("favorites").build();
    }

    public static final class StockTable implements BaseColumns {
        /*
        This class defines the properties of each stock. This is identical to
        the fields as they come down in JSON from Yahoo.
     */
        public static final String TABLE_NAME                                       = "stocks";
        public static final String _ID                                              = "_id";
        public static final String SYMBOL                                           = "symbol";
        public static final String ASK                                              = "Ask";
        public static final String AVERAGEDAILYVOLUME                               = "AverageDailyVolume";
        public static final String BID                                              = "Bid";
        public static final String ASKREALTIME                                      = "AskRealtime";
        public static final String BIDREALTIME                                      = "BidRealtime";
        public static final String BOOKVALUE                                        = "BookValue";
        public static final String CHANGE_PERCENTCHANGE                             = "Change_PercentChange";
        public static final String CHANGE                                           = "Change";
        public static final String COMMISSION                                       = "Commission";
        public static final String CURRENCY                                         = "Currency";
        public static final String CHANGEREALTIME                                   = "ChangeRealtime";
        public static final String AFTERHOURSCHANGEREALTIME                         = "AfterHoursChangeRealtime";
        public static final String DIVIDENDSHARE                                    = "DividendShare";
        public static final String LASTTRADEDATE                                    = "LastTradeDate";
        public static final String TRADEDATE                                        = "TradeDate";
        public static final String EARNINGSSHARE                                    = "EarningsShare";
        public static final String ERRORINDICATIONRETURNEDFORSYMBOLCHANGEDINVALID   = "ErrorIndicationreturnedforsymbolchangedinvalid";
        public static final String EPSESTIMATECURRENTYEAR                           = "EPSEstimateCurrentYear";
        public static final String EPSESTIMATENEXTYEAR                              = "EPSEstimateNextYear";
        public static final String EPSESTIMATENEXTQUARTER                           = "EPSEstimateNextQuarter";
        public static final String DAYSLOW                                          = "DaysLow";
        public static final String DAYSHIGH                                         = "DaysHigh";
        public static final String YEARLOW                                          = "YearLow";
        public static final String YEARHIGH                                         = "YearHigh";
        public static final String HOLDINGSGAINPERCENT                              = "HoldingsGainPercent";
        public static final String ANNUALIZEDGAIN                                   = "AnnualizedGain";
        public static final String HOLDINGSGAIN                                     = "HoldingsGain";
        public static final String HOLDINGSGAINPERCENTREALTIME                      = "HoldingsGainPercentRealtime";
        public static final String HOLDINGSGAINREALTIME                             = "HoldingsGainRealtime";
        public static final String MOREINFO                                         = "MoreInfo";
        public static final String ORDERBOOKREALTIME                                = "OrderBookRealtime";
        public static final String MARKETCAPITALIZATION                             = "MarketCapitalization";
        public static final String MARKETCAPREALTIME                                = "MarketCapRealtime";
        public static final String EBITDA                                           = "EBITDA";
        public static final String CHANGEFROMYEARLOW                                = "ChangeFromYearLow";
        public static final String PERCENTCHANGEFROMYEARLOW                         = "PercentChangeFromYearLow";
        public static final String LASTTRADEREALTIMEWITHTIME                        = "LastTradeRealtimeWithTime";
        public static final String CHANGEPERCENTREALTIME                            = "ChangePercentRealtime";
        public static final String CHANGEFROMYEARHIGH                               = "ChangeFromYearHigh";
        public static final String PERCEBTCHANGEFROMYEARHIGH                        = "PercebtChangeFromYearHigh";
        public static final String LASTTRADEWITHTIME                                = "LastTradeWithTime";
        public static final String LASTTRADEPRICEONLY                               = "LastTradePriceOnly";
        public static final String HIGHLIMIT                                        = "HighLimit";
        public static final String LOWLIMIT                                         = "LowLimit";
        public static final String DAYSRANGE                                        = "DaysRange";
        public static final String DAYSRANGEREALTIME                                = "DaysRangeRealtime";
        public static final String FIFTYDAYMOVINGAVERAGE                            = "FiftydayMovingAverage";
        public static final String TWOHUNDREDDAYMOVINGAVERAGE                       = "TwoHundreddayMovingAverage";
        public static final String CHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE             = "ChangeFromTwoHundreddayMovingAverage";
        public static final String PERCENTCHANGEFROMTWOHUNDREDDAYMOVINGAVERAGE      = "PercentChangeFromTwoHundreddayMovingAverage";
        public static final String CHANGEFROMFIFTYDAYMOVINGAVERAGE                  = "ChangeFromFiftydayMovingAverage";
        public static final String PERCENTCHANGEFROMFIFTYDAYMOVINGAVERAGE           = "PercentChangeFromFiftydayMovingAverage";
        public static final String NAME                                             = "Name";
        public static final String NOTES                                            = "Notes";
        public static final String OPEN                                             = "Open";
        public static final String PREVIOUSCLOSE                                    = "PreviousClose";
        public static final String PRICEPAID                                        = "PricePaid";
        public static final String CHANGEINPERCENT                                  = "ChangeinPercent";
        public static final String PRICESALES                                       = "PriceSales";
        public static final String PRICEBOOK                                        = "PriceBook";
        public static final String EXDIVIDENDDATE                                   = "ExDividendDate";
        public static final String PERATIO                                          = "PERatio";
        public static final String DIVIDENDPAYDATE                                  = "DividendPayDate";
        public static final String PERATIOREALTIME                                  = "PERatioRealtime";
        public static final String PEGRATIO                                         = "PEGRatio";
        public static final String PRICEEPSESTIMATECURRENTYEAR                      = "PriceEPSEstimateCurrentYear";
        public static final String PRICEEPSESTIMATENEXTYEAR                         = "PriceEPSEstimateNextYear";
        public static final String SHARESOWNED                                      = "SharesOwned";
        public static final String SHORTRATIO                                       = "ShortRatio";
        public static final String LASTTRADETIME                                    = "LastTradeTime";
        public static final String TICKERTREND                                      = "TickerTrend";
        public static final String ONEYRTARGETPRICE                                 = "OneyrTargetPrice";
        public static final String VOLUME                                           = "Volume";
        public static final String HOLDINGSVALUE                                    = "HoldingsValue";
        public static final String HOLDINGSVALUEREALTIME                            = "HoldingsValueRealtime";
        public static final String YEARRANGE                                        = "YearRange";
        public static final String DAYSVALUECHANGE                                  = "DaysValueChange";
        public static final String DAYSVALUECHANGEREALTIME                          = "DaysValueChangeRealtime";
        public static final String STOCKEXCHANGE                                    = "StockExchange";
        public static final String DIVIDENDYIELD                                    = "DividendYield";
        public static final String PERCENTCHANGE                                    = "PercentChange";

        // Handy string array for when you need to refer to all columns
        public static final String[] STOCK_ALL_KEYS = new String[] {
                _ID,
                SYMBOL,
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

        // SQL Create table command
        public static final String CREATE_TABLE               =
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
                        "UNIQUE ("+ SYMBOL +") ON CONFLICT REPLACE);";
    }
}
