package generics.ethereum;

import generics.Currency;
import generics.CurrencyType;

public class Ethereum extends Currency {

    public Ethereum(double currencyDolarExchangeRate) {
        super(currencyDolarExchangeRate);
    }

    @Override
    public CurrencyType getCurrencyType() {
        return CurrencyType.CRYPTOCURRENCY;
    }
}
