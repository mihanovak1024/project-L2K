package concurrency.rxjava.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

//    {
//        "total_market_cap_usd": 201241796675,
//        "total_24h_volume_usd": 4548680009,
//        "bitcoin_percentage_of_market_cap": 62.54,
//        "active_currencies": 896,
//        "active_assets": 360,
//        "active_markets": 6439,
//        "last_updated": 1509909852
//    }

public class GlobalMarketData implements Serializable {

    @SerializedName("total_market_cap_usd")
    private String totalMarketCapUsd;
    @SerializedName("total_24h_volume_usd")
    private String totalDailyVolumeUsd;
    @SerializedName("bitcoin_percentage_of_market_cap")
    private String bitcoinPercentage;
    @SerializedName("active_currencies")
    private String activeCurrencies;
    @SerializedName("active_assets")
    private String activeAssets;
    @SerializedName("active_markets")
    private String activeMarkets;
    @SerializedName("last_updated")
    private String lastUpdated;

    public String getTotalMarketCapUsd() {
        return totalMarketCapUsd;
    }

    public void setTotalMarketCapUsd(String totalMarketCapUsd) {
        this.totalMarketCapUsd = totalMarketCapUsd;
    }

    public String getTotalDailyVolumeUsd() {
        return totalDailyVolumeUsd;
    }

    public void setTotalDailyVolumeUsd(String totalDailyVolumeUsd) {
        this.totalDailyVolumeUsd = totalDailyVolumeUsd;
    }

    public String getBitcoinPercentage() {
        return bitcoinPercentage;
    }

    public void setBitcoinPercentage(String bitcoinPercentage) {
        this.bitcoinPercentage = bitcoinPercentage;
    }

    public String getActiveCurrencies() {
        return activeCurrencies;
    }

    public void setActiveCurrencies(String activeCurrencies) {
        this.activeCurrencies = activeCurrencies;
    }

    public String getActiveAssets() {
        return activeAssets;
    }

    public void setActiveAssets(String activeAssets) {
        this.activeAssets = activeAssets;
    }

    public String getActiveMarkets() {
        return activeMarkets;
    }

    public void setActiveMarkets(String activeMarkets) {
        this.activeMarkets = activeMarkets;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}