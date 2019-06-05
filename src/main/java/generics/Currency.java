package generics;

public class Currency {

    private double currencyDolarExchangeRate;
    private double totalCurrencyDolarValue;
    private int currencyQuantity;

    public Currency(double currencyDolarExchangeRate, double totalCurrencyDolarValue, int currencyQuantity) {
        this.currencyDolarExchangeRate = currencyDolarExchangeRate;
        this.totalCurrencyDolarValue = totalCurrencyDolarValue;
        this.currencyQuantity = currencyQuantity;
    }
}
