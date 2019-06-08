package generics.bitcoin;

import generics.Wallet;

public class BitcoinWallet extends Wallet<Bitcoin> {

    public BitcoinWallet(Bitcoin currency, int currencyQuantity) {
        super(currency, currencyQuantity);
    }

    @Override
    public boolean isHolderAnonymous() {
        return true;
    }
}
