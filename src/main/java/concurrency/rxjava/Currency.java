package concurrency.rxjava;

public enum Currency {
    BTC("bitcoin"),
    ETH("ethereum"),
    LTC("litecoin");

    private final String currencyId;

    Currency(String currencyId) {
        this.currencyId = currencyId;
    }

    public static Currency forCurrencyId(String currencyId) {
        for (Currency currency : values()) {
            if (currencyId.equals(currency.currencyId)) {
                return currency;
            }
        }
        return null;
    }

    public String getCurrencyId() {
        return currencyId;
    }
}