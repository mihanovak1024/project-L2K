package generics.standard.euro;

import generics.standard.StandardWallet;

public class EuroWallet extends StandardWallet<Euro> {

    public EuroWallet(Euro currency, double currencyQuantity) {
        super(currency, currencyQuantity);
    }

    @Override
    public void transferCurrencyToBankAccount(double currencyAmountToTransfer, Euro currency) {

    }
}
