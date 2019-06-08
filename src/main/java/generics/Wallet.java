package generics;

public abstract class Wallet<T extends Currency> implements CurrencyHolder<T> {

    private double currencyQuantity;
    private T currency;

    public Wallet(T currency, double currencyQuantity) {
        this.currency = currency;
        this.currencyQuantity = currencyQuantity;
    }

    public T getCurrency() {
        return currency;
    }

    @Override
    public double getTotalValueInDollars() {
        return currency.getCurrencyDolarExchangeRate() * currencyQuantity;
    }
}
