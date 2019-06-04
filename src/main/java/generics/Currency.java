package generics;

public class Currency {

    private int currencyDolarExchangeRate;
    private int totalCurrencyDolarValue;
    private int currencyQuantity;

    public Currency(int currencyDolarExchangeRate, int totalCurrencyDolarValue, int currencyQuantity) {
        this.currencyDolarExchangeRate = currencyDolarExchangeRate;
        this.totalCurrencyDolarValue = totalCurrencyDolarValue;
        this.currencyQuantity = currencyQuantity;
    }
}
