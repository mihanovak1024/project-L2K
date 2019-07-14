package concurrency.rxjava.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

//    {
//        "id": "bitcoin",
//        "name": "Bitcoin",
//        "symbol": "BTC",
//        "rank": "1",
//        "price_usd": "573.137",
//        "price_btc": "1.0",
//        "24h_volume_usd": "72855700.0",
//        "market_cap_usd": "9080883500.0",
//        "available_supply": "15844176.0",
//        "total_supply": "15844176.0",
//        "percent_change_1h": "0.04",
//        "percent_change_24h": "-0.3",
//        "percent_change_7d": "-0.57",
//        "last_updated": "1472762067"
//    }

public class Cryptocurrency implements Serializable {

    private String id;
    private String name;
    private String symbol;
    private String rank;
    @SerializedName("price_usd")
    private String priceUsd;
    @SerializedName("24h_volume_usd")
    private String priceBtc;
    @SerializedName("market_cap_usd")
    private String marketCapUsd;
    @SerializedName("available_supply")
    private String availableSupply;
    @SerializedName("total_supply")
    private String totalSupply;
    @SerializedName("percent_change_1h")
    private String percentChange1h;
    @SerializedName("percent_change_24h")
    private String percentChange24h;
    @SerializedName("percent_change_7d")
    private String percentChange7d;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
    }

    public String getPriceBtc() {
        return priceBtc;
    }

    public void setPriceBtc(String priceBtc) {
        this.priceBtc = priceBtc;
    }

    public String getMarketCapUsd() {
        return marketCapUsd;
    }

    public void setMarketCapUsd(String marketCapUsd) {
        this.marketCapUsd = marketCapUsd;
    }

    public String getAvailableSupply() {
        return availableSupply;
    }

    public void setAvailableSupply(String availableSupply) {
        this.availableSupply = availableSupply;
    }

    public String getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }

    public String getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(String percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public String getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(String percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public String getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(String percentChange7d) {
        this.percentChange7d = percentChange7d;
    }
}