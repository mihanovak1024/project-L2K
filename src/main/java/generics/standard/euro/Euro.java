package generics.standard.euro;

import generics.standard.StandardCurrency;

public class Euro extends StandardCurrency {

    public Euro(double currencyDolarExchangeRate, double totalCurrencyDolarValue, int currencyQuantity) {
        super(currencyDolarExchangeRate, totalCurrencyDolarValue, currencyQuantity);
    }
}
