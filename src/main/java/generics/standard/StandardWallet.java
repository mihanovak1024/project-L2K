package generics.standard;

import generics.Wallet;

public abstract class StandardWallet<T extends StandardCurrency> extends Wallet<T> implements StandardCurrencyHolder<T> {

    public StandardWallet(T currency) {
        super(currency);
    }

    @Override
    public boolean isHolderAnonymous() {
        return false;
    }
}
