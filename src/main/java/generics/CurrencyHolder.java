package generics;

public interface CurrencyHolder<T extends Currency> {

    T getCurrency();

    boolean isHolderAnonymous();

    double getTotalValueInDollars();
}
