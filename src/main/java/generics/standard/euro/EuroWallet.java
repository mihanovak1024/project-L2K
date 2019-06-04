package generics.standard.euro;

import generics.standard.StandardWallet;

public class EuroWallet extends StandardWallet<Euro> {
    public EuroWallet(Euro currency) {
        super(currency);
    }
}
