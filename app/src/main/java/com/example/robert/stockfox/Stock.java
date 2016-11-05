package com.example.robert.stockfox;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by robert on 11/3/16.
 *
 * This class creates Stock objects. The objects directly mirror the JSON data that
 * comes from the Yahoo! Finance API, using attribute names taken from the JSON fields.
 *
 */

public class Stock {
    private String symbol;
    private String Ask;
    private String AverageDailyVolume;
    private String Bid;
    private String AskRealtime;
    private String BidRealtime;
    private String BookValue;
    private String Change_PercentChange;
    private String Change;
    private String Commission;
    private String Currency;
    private String ChangeRealtime;
    private String AfterHoursChangeRealtime;
    private String DividendShare;
    private String LastTradeDate;
    private String TradeDate;
    private String EarningsShare;
    private String ErrorIndicationreturnedforsymbolchangedinvalid;
    private String EPSEstimateCurrentYear;
    private String EPSEstimateNextYear;
    private String EPSEstimateNextQuarter;
    private String DaysLow;
    private String DaysHigh;
    private String YearLow;
    private String YearHigh;
    private String HoldingsGainPercent;
    private String AnnualizedGain;
    private String HoldingsGain;
    private String HoldingsGainPercentRealtime;
    private String HoldingsGainRealtime;
    private String MoreInfo;
    private String OrderBookRealtime;
    private String MarketCapitalization;
    private String MarketCapRealtime;
    private String EBITDA;
    private String ChangeFromYearLow;
    private String PercentChangeFromYearLow;
    private String LastTradeRealtimeWithTime;
    private String ChangePercentRealtime;
    private String ChangeFromYearHigh;
    private String PercebtChangeFromYearHigh;
    private String LastTradeWithTime;
    private String LastTradePriceOnly;
    private String HighLimit;
    private String LowLimit;
    private String DaysRange;
    private String DaysRangeRealtime;
    private String FiftydayMovingAverage;
    private String TwoHundreddayMovingAverage;
    private String ChangeFromTwoHundreddayMovingAverage;
    private String PercentChangeFromTwoHundreddayMovingAverage;
    private String ChangeFromFiftydayMovingAverage;
    private String PercentChangeFromFiftydayMovingAverage;
    private String Name;
    private String Notes;
    private String Open;
    private String PreviousClose;
    private String PricePaid;
    private String ChangeinPercent;
    private String PriceSales;
    private String PriceBook;
    private String ExDividendDate;
    private String PERatio;
    private String DividendPayDate;
    private String PERatioRealtime;
    private String PEGRatio;
    private String PriceEPSEstimateCurrentYear;
    private String PriceEPSEstimateNextYear;
    private String SharesOwned;
    private String ShortRatio;
    private String LastTradeTime;
    private String TickerTrend;
    private String OneyrTargetPrice;
    private String Volume;
    private String HoldingsValue;
    private String HoldingsValueRealtime;
    private String YearRange;
    private String DaysValueChange;
    private String DaysValueChangeRealtime;
    private String StockExchange;
    private String DividendYield;
    private String PercentChange;

    public Stock(JSONObject jsonObject) throws JSONException {
        symbol = jsonObject.getString("symbol");
        Ask = jsonObject.getString("Ask");
        AverageDailyVolume = jsonObject.getString("AverageDailyVolume");
        Bid = jsonObject.getString("Bid");
        AskRealtime = jsonObject.getString("AskRealtime");
        BidRealtime = jsonObject.getString("BidRealtime");
        BookValue = jsonObject.getString("BookValue");
        Change_PercentChange = jsonObject.getString("Change_PercentChange");
        Change = jsonObject.getString("Change");
        Commission = jsonObject.getString("Commission");
        Currency = jsonObject.getString("Currency");
        ChangeRealtime = jsonObject.getString("ChangeRealtime");
        AfterHoursChangeRealtime = jsonObject.getString("AfterHoursChangeRealtime");
        DividendShare = jsonObject.getString("DividendShare");
        LastTradeDate = jsonObject.getString("LastTradeDate");
        TradeDate = jsonObject.getString("TradeDate");
        EarningsShare = jsonObject.getString("EarningsShare");
        ErrorIndicationreturnedforsymbolchangedinvalid = jsonObject.getString("ErrorIndicationreturnedforsymbolchangedinvalid");
        EPSEstimateCurrentYear = jsonObject.getString("EPSEstimateCurrentYear");
        EPSEstimateNextYear = jsonObject.getString("EPSEstimateNextYear");
        EPSEstimateNextQuarter = jsonObject.getString("EPSEstimateNextQuarter");
        DaysLow = jsonObject.getString("DaysLow");
        DaysHigh = jsonObject.getString("DaysHigh");
        YearLow = jsonObject.getString("YearLow");
        YearHigh = jsonObject.getString("YearHigh");
        HoldingsGainPercent = jsonObject.getString("HoldingsGainPercent");
        AnnualizedGain = jsonObject.getString("AnnualizedGain");
        HoldingsGain = jsonObject.getString("HoldingsGain");
        HoldingsGainPercentRealtime = jsonObject.getString("HoldingsGainPercentRealtime");
        HoldingsGainRealtime = jsonObject.getString("HoldingsGainRealtime");
        MoreInfo = jsonObject.getString("MoreInfo");
        OrderBookRealtime = jsonObject.getString("OrderBookRealtime");
        MarketCapitalization = jsonObject.getString("MarketCapitalization");
        MarketCapRealtime = jsonObject.getString("MarketCapRealtime");
        EBITDA = jsonObject.getString("EBITDA");
        ChangeFromYearLow = jsonObject.getString("ChangeFromYearLow");
        PercentChangeFromYearLow = jsonObject.getString("PercentChangeFromYearLow");
        LastTradeRealtimeWithTime = jsonObject.getString("LastTradeRealtimeWithTime");
        ChangePercentRealtime = jsonObject.getString("ChangePercentRealtime");
        ChangeFromYearHigh = jsonObject.getString("ChangeFromYearHigh");
        PercebtChangeFromYearHigh = jsonObject.getString("PercebtChangeFromYearHigh");
        LastTradeWithTime = jsonObject.getString("LastTradeWithTime");
        LastTradePriceOnly = jsonObject.getString("LastTradePriceOnly");
        HighLimit = jsonObject.getString("HighLimit");
        LowLimit = jsonObject.getString("LowLimit");
        DaysRange = jsonObject.getString("DaysRange");
        DaysRangeRealtime = jsonObject.getString("DaysRangeRealtime");
        FiftydayMovingAverage = jsonObject.getString("FiftydayMovingAverage");
        TwoHundreddayMovingAverage = jsonObject.getString("TwoHundreddayMovingAverage");
        ChangeFromTwoHundreddayMovingAverage = jsonObject.getString("ChangeFromTwoHundreddayMovingAverage");
        PercentChangeFromTwoHundreddayMovingAverage = jsonObject.getString("PercentChangeFromTwoHundreddayMovingAverage");
        ChangeFromFiftydayMovingAverage = jsonObject.getString("ChangeFromFiftydayMovingAverage");
        PercentChangeFromFiftydayMovingAverage = jsonObject.getString("PercentChangeFromFiftydayMovingAverage");
        Name = jsonObject.getString("Name");
        Notes = jsonObject.getString("Notes");
        Open = jsonObject.getString("Open");
        PreviousClose = jsonObject.getString("PreviousClose");
        PricePaid = jsonObject.getString("PricePaid");
        ChangeinPercent = jsonObject.getString("ChangeinPercent");
        PriceSales = jsonObject.getString("PriceSales");
        PriceBook = jsonObject.getString("PriceBook");
        ExDividendDate = jsonObject.getString("ExDividendDate");
        PERatio = jsonObject.getString("PERatio");
        DividendPayDate = jsonObject.getString("DividendPayDate");
        PERatioRealtime = jsonObject.getString("PERatioRealtime");
        PEGRatio = jsonObject.getString("PEGRatio");
        PriceEPSEstimateCurrentYear = jsonObject.getString("PriceEPSEstimateCurrentYear");
        PriceEPSEstimateNextYear = jsonObject.getString("PriceEPSEstimateNextYear");
        SharesOwned = jsonObject.getString("SharesOwned");
        ShortRatio = jsonObject.getString("ShortRatio");
        LastTradeTime = jsonObject.getString("LastTradeTime");
        TickerTrend = jsonObject.getString("TickerTrend");
        OneyrTargetPrice = jsonObject.getString("OneyrTargetPrice");
        Volume = jsonObject.getString("Volume");
        HoldingsValue = jsonObject.getString("HoldingsValue");
        HoldingsValueRealtime = jsonObject.getString("HoldingsValueRealtime");
        YearRange = jsonObject.getString("YearRange");
        DaysValueChange = jsonObject.getString("DaysValueChange");
        DaysValueChangeRealtime = jsonObject.getString("DaysValueChangeRealtime");
        StockExchange = jsonObject.getString("StockExchange");
        DividendYield = jsonObject.getString("DividendYield");
        PercentChange = jsonObject.getString("PercentChange");
    }

    public Stock() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAsk() {
        return Ask;
    }

    public void setAsk(String ask) {
        Ask = ask;
    }

    public String getAverageDailyVolume() {
        return AverageDailyVolume;
    }

    public void setAverageDailyVolume(String averageDailyVolume) {
        AverageDailyVolume = averageDailyVolume;
    }

    public String getBid() {
        return Bid;
    }

    public void setBid(String bid) {
        Bid = bid;
    }

    public String getAskRealtime() {
        return AskRealtime;
    }

    public void setAskRealtime(String askRealtime) {
        AskRealtime = askRealtime;
    }

    public String getBidRealtime() {
        return BidRealtime;
    }

    public void setBidRealtime(String bidRealtime) {
        BidRealtime = bidRealtime;
    }

    public String getBookValue() {
        return BookValue;
    }

    public void setBookValue(String bookValue) {
        BookValue = bookValue;
    }

    public String getChange_PercentChange() {
        return Change_PercentChange;
    }

    public void setChange_PercentChange(String change_PercentChange) {
        Change_PercentChange = change_PercentChange;
    }

    public String getChange() {
        return Change;
    }

    public void setChange(String change) {
        Change = change;
    }

    public String getCommission() {
        return Commission;
    }

    public void setCommission(String commission) {
        Commission = commission;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getChangeRealtime() {
        return ChangeRealtime;
    }

    public void setChangeRealtime(String changeRealtime) {
        ChangeRealtime = changeRealtime;
    }

    public String getAfterHoursChangeRealtime() {
        return AfterHoursChangeRealtime;
    }

    public void setAfterHoursChangeRealtime(String afterHoursChangeRealtime) {
        AfterHoursChangeRealtime = afterHoursChangeRealtime;
    }

    public String getDividendShare() {
        return DividendShare;
    }

    public void setDividendShare(String dividendShare) {
        DividendShare = dividendShare;
    }

    public String getLastTradeDate() {
        return LastTradeDate;
    }

    public void setLastTradeDate(String lastTradeDate) {
        LastTradeDate = lastTradeDate;
    }

    public String getTradeDate() {
        return TradeDate;
    }

    public void setTradeDate(String tradeDate) {
        TradeDate = tradeDate;
    }

    public String getEarningsShare() {
        return EarningsShare;
    }

    public void setEarningsShare(String earningsShare) {
        EarningsShare = earningsShare;
    }

    public String getErrorIndicationreturnedforsymbolchangedinvalid() {
        return ErrorIndicationreturnedforsymbolchangedinvalid;
    }

    public void setErrorIndicationreturnedforsymbolchangedinvalid(String errorIndicationreturnedforsymbolchangedinvalid) {
        ErrorIndicationreturnedforsymbolchangedinvalid = errorIndicationreturnedforsymbolchangedinvalid;
    }

    public String getEPSEstimateCurrentYear() {
        return EPSEstimateCurrentYear;
    }

    public void setEPSEstimateCurrentYear(String EPSEstimateCurrentYear) {
        this.EPSEstimateCurrentYear = EPSEstimateCurrentYear;
    }

    public String getEPSEstimateNextYear() {
        return EPSEstimateNextYear;
    }

    public void setEPSEstimateNextYear(String EPSEstimateNextYear) {
        this.EPSEstimateNextYear = EPSEstimateNextYear;
    }

    public String getEPSEstimateNextQuarter() {
        return EPSEstimateNextQuarter;
    }

    public void setEPSEstimateNextQuarter(String EPSEstimateNextQuarter) {
        this.EPSEstimateNextQuarter = EPSEstimateNextQuarter;
    }

    public String getDaysLow() {
        return DaysLow;
    }

    public void setDaysLow(String daysLow) {
        DaysLow = daysLow;
    }

    public String getDaysHigh() {
        return DaysHigh;
    }

    public void setDaysHigh(String daysHigh) {
        DaysHigh = daysHigh;
    }

    public String getYearLow() {
        return YearLow;
    }

    public void setYearLow(String yearLow) {
        YearLow = yearLow;
    }

    public String getYearHigh() {
        return YearHigh;
    }

    public void setYearHigh(String yearHigh) {
        YearHigh = yearHigh;
    }

    public String getHoldingsGainPercent() {
        return HoldingsGainPercent;
    }

    public void setHoldingsGainPercent(String holdingsGainPercent) {
        HoldingsGainPercent = holdingsGainPercent;
    }

    public String getAnnualizedGain() {
        return AnnualizedGain;
    }

    public void setAnnualizedGain(String annualizedGain) {
        AnnualizedGain = annualizedGain;
    }

    public String getHoldingsGain() {
        return HoldingsGain;
    }

    public void setHoldingsGain(String holdingsGain) {
        HoldingsGain = holdingsGain;
    }

    public String getHoldingsGainPercentRealtime() {
        return HoldingsGainPercentRealtime;
    }

    public void setHoldingsGainPercentRealtime(String holdingsGainPercentRealtime) {
        HoldingsGainPercentRealtime = holdingsGainPercentRealtime;
    }

    public String getHoldingsGainRealtime() {
        return HoldingsGainRealtime;
    }

    public void setHoldingsGainRealtime(String holdingsGainRealtime) {
        HoldingsGainRealtime = holdingsGainRealtime;
    }

    public String getMoreInfo() {
        return MoreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        MoreInfo = moreInfo;
    }

    public String getOrderBookRealtime() {
        return OrderBookRealtime;
    }

    public void setOrderBookRealtime(String orderBookRealtime) {
        OrderBookRealtime = orderBookRealtime;
    }

    public String getMarketCapitalization() {
        return MarketCapitalization;
    }

    public void setMarketCapitalization(String marketCapitalization) {
        MarketCapitalization = marketCapitalization;
    }

    public String getMarketCapRealtime() {
        return MarketCapRealtime;
    }

    public void setMarketCapRealtime(String marketCapRealtime) {
        MarketCapRealtime = marketCapRealtime;
    }

    public String getEBITDA() {
        return EBITDA;
    }

    public void setEBITDA(String EBITDA) {
        this.EBITDA = EBITDA;
    }

    public String getChangeFromYearLow() {
        return ChangeFromYearLow;
    }

    public void setChangeFromYearLow(String changeFromYearLow) {
        ChangeFromYearLow = changeFromYearLow;
    }

    public String getPercentChangeFromYearLow() {
        return PercentChangeFromYearLow;
    }

    public void setPercentChangeFromYearLow(String percentChangeFromYearLow) {
        PercentChangeFromYearLow = percentChangeFromYearLow;
    }

    public String getLastTradeRealtimeWithTime() {
        return LastTradeRealtimeWithTime;
    }

    public void setLastTradeRealtimeWithTime(String lastTradeRealtimeWithTime) {
        LastTradeRealtimeWithTime = lastTradeRealtimeWithTime;
    }

    public String getChangePercentRealtime() {
        return ChangePercentRealtime;
    }

    public void setChangePercentRealtime(String changePercentRealtime) {
        ChangePercentRealtime = changePercentRealtime;
    }

    public String getChangeFromYearHigh() {
        return ChangeFromYearHigh;
    }

    public void setChangeFromYearHigh(String changeFromYearHigh) {
        ChangeFromYearHigh = changeFromYearHigh;
    }

    public String getPercebtChangeFromYearHigh() {
        return PercebtChangeFromYearHigh;
    }

    public void setPercebtChangeFromYearHigh(String percebtChangeFromYearHigh) {
        PercebtChangeFromYearHigh = percebtChangeFromYearHigh;
    }

    public String getLastTradeWithTime() {
        return LastTradeWithTime;
    }

    public void setLastTradeWithTime(String lastTradeWithTime) {
        LastTradeWithTime = lastTradeWithTime;
    }

    public String getLastTradePriceOnly() {
        return LastTradePriceOnly;
    }

    public void setLastTradePriceOnly(String lastTradePriceOnly) {
        LastTradePriceOnly = lastTradePriceOnly;
    }

    public String getHighLimit() {
        return HighLimit;
    }

    public void setHighLimit(String highLimit) {
        HighLimit = highLimit;
    }

    public String getLowLimit() {
        return LowLimit;
    }

    public void setLowLimit(String lowLimit) {
        LowLimit = lowLimit;
    }

    public String getDaysRange() {
        return DaysRange;
    }

    public void setDaysRange(String daysRange) {
        DaysRange = daysRange;
    }

    public String getDaysRangeRealtime() {
        return DaysRangeRealtime;
    }

    public void setDaysRangeRealtime(String daysRangeRealtime) {
        DaysRangeRealtime = daysRangeRealtime;
    }

    public String getFiftydayMovingAverage() {
        return FiftydayMovingAverage;
    }

    public void setFiftydayMovingAverage(String fiftydayMovingAverage) {
        FiftydayMovingAverage = fiftydayMovingAverage;
    }

    public String getTwoHundreddayMovingAverage() {
        return TwoHundreddayMovingAverage;
    }

    public void setTwoHundreddayMovingAverage(String twoHundreddayMovingAverage) {
        TwoHundreddayMovingAverage = twoHundreddayMovingAverage;
    }

    public String getChangeFromTwoHundreddayMovingAverage() {
        return ChangeFromTwoHundreddayMovingAverage;
    }

    public void setChangeFromTwoHundreddayMovingAverage(String changeFromTwoHundreddayMovingAverage) {
        ChangeFromTwoHundreddayMovingAverage = changeFromTwoHundreddayMovingAverage;
    }

    public String getPercentChangeFromTwoHundreddayMovingAverage() {
        return PercentChangeFromTwoHundreddayMovingAverage;
    }

    public void setPercentChangeFromTwoHundreddayMovingAverage(String percentChangeFromTwoHundreddayMovingAverage) {
        PercentChangeFromTwoHundreddayMovingAverage = percentChangeFromTwoHundreddayMovingAverage;
    }

    public String getChangeFromFiftydayMovingAverage() {
        return ChangeFromFiftydayMovingAverage;
    }

    public void setChangeFromFiftydayMovingAverage(String changeFromFiftydayMovingAverage) {
        ChangeFromFiftydayMovingAverage = changeFromFiftydayMovingAverage;
    }

    public String getPercentChangeFromFiftydayMovingAverage() {
        return PercentChangeFromFiftydayMovingAverage;
    }

    public void setPercentChangeFromFiftydayMovingAverage(String percentChangeFromFiftydayMovingAverage) {
        PercentChangeFromFiftydayMovingAverage = percentChangeFromFiftydayMovingAverage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getOpen() {
        return Open;
    }

    public void setOpen(String open) {
        Open = open;
    }

    public String getPreviousClose() {
        return PreviousClose;
    }

    public void setPreviousClose(String previousClose) {
        PreviousClose = previousClose;
    }

    public String getPricePaid() {
        return PricePaid;
    }

    public void setPricePaid(String pricePaid) {
        PricePaid = pricePaid;
    }

    public String getChangeinPercent() {
        return ChangeinPercent;
    }

    public void setChangeinPercent(String changeinPercent) {
        ChangeinPercent = changeinPercent;
    }

    public String getPriceSales() {
        return PriceSales;
    }

    public void setPriceSales(String priceSales) {
        PriceSales = priceSales;
    }

    public String getPriceBook() {
        return PriceBook;
    }

    public void setPriceBook(String priceBook) {
        PriceBook = priceBook;
    }

    public String getExDividendDate() {
        return ExDividendDate;
    }

    public void setExDividendDate(String exDividendDate) {
        ExDividendDate = exDividendDate;
    }

    public String getPERatio() {
        return PERatio;
    }

    public void setPERatio(String PERatio) {
        this.PERatio = PERatio;
    }

    public String getDividendPayDate() {
        return DividendPayDate;
    }

    public void setDividendPayDate(String dividendPayDate) {
        DividendPayDate = dividendPayDate;
    }

    public String getPERatioRealtime() {
        return PERatioRealtime;
    }

    public void setPERatioRealtime(String PERatioRealtime) {
        this.PERatioRealtime = PERatioRealtime;
    }

    public String getPEGRatio() {
        return PEGRatio;
    }

    public void setPEGRatio(String PEGRatio) {
        this.PEGRatio = PEGRatio;
    }

    public String getPriceEPSEstimateCurrentYear() {
        return PriceEPSEstimateCurrentYear;
    }

    public void setPriceEPSEstimateCurrentYear(String priceEPSEstimateCurrentYear) {
        PriceEPSEstimateCurrentYear = priceEPSEstimateCurrentYear;
    }

    public String getPriceEPSEstimateNextYear() {
        return PriceEPSEstimateNextYear;
    }

    public void setPriceEPSEstimateNextYear(String priceEPSEstimateNextYear) {
        PriceEPSEstimateNextYear = priceEPSEstimateNextYear;
    }

    public String getSharesOwned() {
        return SharesOwned;
    }

    public void setSharesOwned(String sharesOwned) {
        SharesOwned = sharesOwned;
    }

    public String getShortRatio() {
        return ShortRatio;
    }

    public void setShortRatio(String shortRatio) {
        ShortRatio = shortRatio;
    }

    public String getLastTradeTime() {
        return LastTradeTime;
    }

    public void setLastTradeTime(String lastTradeTime) {
        LastTradeTime = lastTradeTime;
    }

    public String getTickerTrend() {
        return TickerTrend;
    }

    public void setTickerTrend(String tickerTrend) {
        TickerTrend = tickerTrend;
    }

    public String getOneyrTargetPrice() {
        return OneyrTargetPrice;
    }

    public void setOneyrTargetPrice(String oneyrTargetPrice) {
        OneyrTargetPrice = oneyrTargetPrice;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String volume) {
        Volume = volume;
    }

    public String getHoldingsValue() {
        return HoldingsValue;
    }

    public void setHoldingsValue(String holdingsValue) {
        HoldingsValue = holdingsValue;
    }

    public String getHoldingsValueRealtime() {
        return HoldingsValueRealtime;
    }

    public void setHoldingsValueRealtime(String holdingsValueRealtime) {
        HoldingsValueRealtime = holdingsValueRealtime;
    }

    public String getYearRange() {
        return YearRange;
    }

    public void setYearRange(String yearRange) {
        YearRange = yearRange;
    }

    public String getDaysValueChange() {
        return DaysValueChange;
    }

    public void setDaysValueChange(String daysValueChange) {
        DaysValueChange = daysValueChange;
    }

    public String getDaysValueChangeRealtime() {
        return DaysValueChangeRealtime;
    }

    public void setDaysValueChangeRealtime(String daysValueChangeRealtime) {
        DaysValueChangeRealtime = daysValueChangeRealtime;
    }

    public String getStockExchange() {
        return StockExchange;
    }

    public void setStockExchange(String stockExchange) {
        StockExchange = stockExchange;
    }

    public String getDividendYield() {
        return DividendYield;
    }

    public void setDividendYield(String dividendYield) {
        DividendYield = dividendYield;
    }

    public String getPercentChange() {
        return PercentChange;
    }

    public void setPercentChange(String percentChange) {
        PercentChange = percentChange;
    }
}
