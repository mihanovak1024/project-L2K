package generics.standard;

import generics.Currency;
import generics.CurrencyType;

public class StandardCurrency extends Currency {

    public StandardCurrency(double currencyDolarExchangeRate) {
        super(currencyDolarExchangeRate);
    }

    @Override
    public CurrencyType getCurrencyType() {
        return CurrencyType.STANDARD;
    }
}
