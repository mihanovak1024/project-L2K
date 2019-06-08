package generics.ethereum;

import generics.Wallet;

public class EthereumWallet extends Wallet<Ethereum> {

    public EthereumWallet(Ethereum currency, int currencyQuantity) {
        super(currency, currencyQuantity);
    }

    @Override
    public boolean isHolderAnonymous() {
        return true;
    }
}
