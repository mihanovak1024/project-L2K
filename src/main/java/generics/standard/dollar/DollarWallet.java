package generics.standard.dollar;

import generics.standard.StandardWallet;

public class DollarWallet extends StandardWallet<Dollar> {

    public DollarWallet(Dollar currency) {
        super(currency);
    }
}
