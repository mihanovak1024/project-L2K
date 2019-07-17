package concurrency.rxjava.network;

public class CoinMarketCapConstants {

    public static final String BASE_CRYPTO_URL = "https://api.coinmarketcap.com/v1/ticker/";
    public static final String MARKET_DATA_URL = "https://api.coinmarketcap.com/v1/global/";

    public enum Currency {
        BTC("bitcoin"),
        ETH("ethereum"),
        LTC("litecoin");

        private final String currencyID;

        Currency(String currencyID) {
            this.currencyID = currencyID;
        }

        public static Currency forValue(String value) {
            for (Currency currency : values()) {
                if (value.equals(currency.currencyID)) {
                    return currency;
                }
            }
            return null;
        }

        public String getCurrencyID() {
            return currencyID;
        }
    }

}
