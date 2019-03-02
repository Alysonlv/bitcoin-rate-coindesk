package com.alv.bitcoin.rate.app;
/*
 * Created by alysonlv - 2019-03-02
 */

import com.alv.bitcoin.rate.service.domain.BitcoinRate;

import java.util.Scanner;

public class BitcoinRateApplication {

    public static void main(String[] args) {
        System.out.print("Please inform the currency to see Bitcoin rate (e.g. USD): ");
        Scanner in = new Scanner(System.in);
        String currency = in.next();

        BitcoinRateExecutor executor = new BitcoinRateExecutor();
        BitcoinRate bitcoinRate = executor.getBitcoinRate(currency);
        BitcoinRate.printBitcoinRate(bitcoinRate);

    }
}
