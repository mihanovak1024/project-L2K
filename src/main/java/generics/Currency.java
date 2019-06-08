package generics;

public abstract class Currency {

    private double currencyDolarExchangeRate;

    public Currency(double currencyDolarExchangeRate) {
        this.currencyDolarExchangeRate = currencyDolarExchangeRate;
    }

    public double getCurrencyDolarExchangeRate() {
        return currencyDolarExchangeRate;
    }
}
