package generics;

import generics.standard.StandardCurrency;

public interface CurrencyOperations<Z extends StandardCurrency> {

    <T extends Currency, V extends Currency> Z sumTwoCurrencies(T firstCurrency, V secondCurrency);

}
