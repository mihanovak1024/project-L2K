package generics;

import generics.standard.StandardWallet;

public interface CurrencyOperations<Z extends StandardWallet> {

    <T extends CurrencyHolder, V extends CurrencyHolder> Z sumTwoCurrencies(T firstCurrencyHolder, V secondCurrencyHolder);
}
