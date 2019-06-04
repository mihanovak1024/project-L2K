package generics;

public class Wallet<T extends Currency> {

    private T currency;

    public Wallet(T currency) {
        this.currency = currency;
    }

    public T getCurrency() {
        return currency;
    }

}
