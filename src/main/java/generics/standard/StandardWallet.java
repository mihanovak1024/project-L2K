package generics.standard;

import generics.Wallet;

public abstract class StandardWallet<T extends StandardCurrency> extends Wallet<T> implements StandardCurrencyHolder<T> {

    public StandardWallet(T currency, double currencyQuantity) {
        super(currency, currencyQuantity);
    }

    @Override
    public boolean isHolderAnonymous() {
        return false;
    }
}
