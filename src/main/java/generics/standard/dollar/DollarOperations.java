package generics.standard.dollar;

import generics.Currency;
import generics.CurrencyOperations;

public class DollarOperations implements CurrencyOperations<Dollar> {

    @Override
    public <T extends Currency, V extends Currency> Dollar sumTwoCurrencies(T firstCurrency, V secondCurrency) {
        return null;
    }
}
