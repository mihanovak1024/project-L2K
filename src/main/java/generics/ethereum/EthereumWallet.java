package generics.ethereum;

import generics.Wallet;

public class EthereumWallet extends Wallet<Ethereum> {

    public EthereumWallet(Ethereum currency, double currencyQuantity) {
        super(currency, currencyQuantity);
    }

    @Override
    public boolean isHolderAnonymous() {
        return true;
    }
}
