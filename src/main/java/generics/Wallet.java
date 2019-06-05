package generics;

public abstract class Wallet<T extends Currency> implements CurrencyHolder<T> {

    private T currency;

    public Wallet(T currency) {
        this.currency = currency;
    }

    public T getCurrency() {
        return currency;
    }
}
