package generics.standard.dollar;

import generics.standard.StandardWallet;

public class DollarWallet extends StandardWallet<Dollar> {

    public DollarWallet(Dollar currency, double currencyQuantity) {
        super(currency, currencyQuantity);
    }

    @Override
    public void transferCurrencyToBankAccount(double currencyAmountToTransfer, Dollar currency) {

    }
}
