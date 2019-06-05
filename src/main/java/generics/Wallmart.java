package generics;

import java.util.ArrayList;
import java.util.List;

import generics.bitcoin.Bitcoin;
import generics.bitcoin.BitcoinWallet;
import generics.ethereum.Ethereum;
import generics.ethereum.EthereumWallet;
import generics.standard.StandardCurrency;
import generics.standard.euro.Euro;
import generics.standard.euro.EuroWallet;

public class Wallmart {

    private static List<CurrencyHolder<StandardCurrency>> walletList;

    public Wallmart() {
        setupWalletList();
    }

    private void setupWalletList() {
        walletList = new ArrayList<>();

        Bitcoin bitcoin = new Bitcoin(
                7768,
                776800,
                10
        );
        CurrencyHolder bitcoinWallet = new BitcoinWallet(bitcoin);
        walletList.add(bitcoinWallet);

        Ethereum ethereum = new Ethereum(
                244,
                244000,
                1000
        );
        CurrencyHolder ethereumWallet = new EthereumWallet(ethereum);
        walletList.add(ethereumWallet);

        Euro euro = new Euro(
                1.12,
                112,
                100
        );
        CurrencyHolder euroWallet = new EuroWallet(euro);
        walletList.add(euroWallet);
    }
}
