package generics.standard.euro;

import generics.standard.StandardWallet;

public class EuroWallet extends StandardWallet<Euro> {

    public EuroWallet(Euro currency, int currencyQuantity) {
        super(currency, currencyQuantity);
    }

    @Override
    public void transferCurrencyToBankAccount(int currencyAmountToTransfer, Euro currency) {

    }
}
