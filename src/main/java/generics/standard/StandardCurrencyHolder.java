package generics.standard;

import generics.CurrencyHolder;

public interface StandardCurrencyHolder<T extends StandardCurrency> extends CurrencyHolder<T> {

    void transferCurrencyToBankAccount(double currencyAmountToTransfer, T currency);
}
