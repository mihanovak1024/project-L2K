package generics.ethereum;

import generics.Wallet;

public class EthereumWallet extends Wallet<Ethereum> {
    public EthereumWallet(Ethereum currency) {
        super(currency);
    }
}
