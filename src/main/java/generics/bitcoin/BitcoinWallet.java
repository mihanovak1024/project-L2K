package generics.bitcoin;

import generics.Wallet;

public class BitcoinWallet extends Wallet<Bitcoin> {

    public BitcoinWallet(Bitcoin currency) {
        super(currency);
    }

}
