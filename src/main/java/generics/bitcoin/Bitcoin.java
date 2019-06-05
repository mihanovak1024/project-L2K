package generics.bitcoin;

import generics.Currency;

public class Bitcoin extends Currency {
    public Bitcoin(double currencyDolarExchangeRate, double totalCurrencyDolarValue, int currencyQuantity) {
        super(currencyDolarExchangeRate, totalCurrencyDolarValue, currencyQuantity);
    }
}
