package generics;

import java.util.ArrayList;
import java.util.List;

import generics.bitcoin.Bitcoin;
import generics.bitcoin.BitcoinWallet;
import generics.ethereum.Ethereum;
import generics.ethereum.EthereumWallet;
import generics.standard.dollar.Dollar;
import generics.standard.dollar.DollarWallet;
import generics.standard.euro.Euro;
import generics.standard.euro.EuroWallet;

public class Wallmart {

    private static final double BITCOIN_DOLAR_EXCHANGE_RATE = 7768;
    private static final double ETHEREUM_DOLAR_EXCHANGE_RATE = 244;
    private static final double EURO_DOLAR_EXCHANGE_RATE = 1.12;

    private static List<CurrencyHolder> walletList;

    public static void main(String[] args) {
        new Wallmart();
    }

    public Wallmart() {
        setupWalletList();
    }

    private void setupWalletList() {
        walletList = new ArrayList<>();

        Bitcoin bitcoin = new Bitcoin(BITCOIN_DOLAR_EXCHANGE_RATE);
        CurrencyHolder bitcoinWallet = new BitcoinWallet(bitcoin, 10);
        walletList.add(bitcoinWallet);

        Ethereum ethereum = new Ethereum(ETHEREUM_DOLAR_EXCHANGE_RATE);
        CurrencyHolder ethereumWallet = new EthereumWallet(ethereum, 100);
        walletList.add(ethereumWallet);

        Euro euro = new Euro(EURO_DOLAR_EXCHANGE_RATE);
        CurrencyHolder euroWallet = new EuroWallet(euro, 500);
        walletList.add(euroWallet);

        Dollar dollar = new Dollar(1);
        CurrencyHolder dolarWallet = new DollarWallet(dollar, 250);
        walletList.add(dolarWallet);
    }
}
