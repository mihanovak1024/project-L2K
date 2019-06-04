package generics.standard.euro;

import generics.standard.StandardCurrency;

public class Euro extends StandardCurrency {

    public Euro(int currencyDolarExchangeRate, int totalCurrencyDolarValue, int currencyQuantity) {
        super(currencyDolarExchangeRate, totalCurrencyDolarValue, currencyQuantity);
    }
}
