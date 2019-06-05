package generics.standard.dollar;

import generics.standard.StandardCurrency;

public class Dollar extends StandardCurrency {

    public Dollar(double currencyDolarExchangeRate, double totalCurrencyDolarValue, int currencyQuantity) {
        super(currencyDolarExchangeRate, totalCurrencyDolarValue, currencyQuantity);
    }
}
