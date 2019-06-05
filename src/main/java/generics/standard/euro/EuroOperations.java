package generics.standard.euro;

import generics.Currency;
import generics.CurrencyOperations;

public class EuroOperations implements CurrencyOperations<Euro> {

    @Override
    public <T extends Currency, V extends Currency> Euro sumTwoCurrencies(T firstCurrency, V secondCurrency) {
        return null;
    }
}
