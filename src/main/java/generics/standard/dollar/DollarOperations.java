package generics.standard.dollar;

import generics.CurrencyHolder;
import generics.CurrencyOperations;

public class DollarOperations implements CurrencyOperations<DollarWallet> {

    @Override
    public <T extends CurrencyHolder, V extends CurrencyHolder> DollarWallet sumTwoCurrencies(T firstCurrencyHolder,
                                                                                              V secondCurrencyHolder) {
        Dollar dollar = new Dollar(1);
        double result = firstCurrencyHolder.getTotalValueInDollars() + secondCurrencyHolder.getTotalValueInDollars();
        DollarWallet dollarWallet = new DollarWallet(dollar, result);
        return dollarWallet;
    }
}
