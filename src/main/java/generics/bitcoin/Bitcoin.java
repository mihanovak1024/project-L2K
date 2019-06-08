package generics.bitcoin;

import generics.Currency;
import generics.CurrencyType;

public class Bitcoin extends Currency {

    public Bitcoin(double currencyDolarExchangeRate) {
        super(currencyDolarExchangeRate);
    }

    @Override
    public CurrencyType getCurrencyType() {
        return CurrencyType.CRYPTOCURRENCY;
    }
}
