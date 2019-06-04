package generics.standard;

import generics.Wallet;

public class StandardWallet<T extends StandardCurrency> extends Wallet<T>{

    public StandardWallet(T currency) {
        super(currency);
    }
}
