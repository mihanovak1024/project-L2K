package generics.standard.dollar;

import generics.standard.StandardWallet;

public class DollarWallet extends StandardWallet<Dollar> {

    public DollarWallet(Dollar currency, int currencyQuantity) {
        super(currency, currencyQuantity);
    }

    @Override
    public void transferCurrencyToBankAccount(int currencyAmountToTransfer, Dollar currency) {

    }
}
