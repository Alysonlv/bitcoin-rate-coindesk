package com.alv.bitcoin.rate.service.domain;
/*
 * Created by alysonlv - 2019-03-02
 */

import com.alv.bitcoin.rate.service.utils.BitcoinRateMarshaller;

import java.text.DecimalFormat;

public class BitcoinRate {

    private BPI bpi;

    private float lowesRate;

    private float highestRage;

    private BitcoinRateStatus status;

    private BitcoinRate(Builder builder) {
        this.bpi = builder.getBpi();
        this.lowesRate = builder.getLowesRate();
        this.highestRage = builder.getHighestRage();
        this.status = builder.status;
    }

    public BPI getBpi() {
        return bpi;
    }

    public float getLowesRate() {
        return lowesRate;
    }

    public float getHighestRage() {
        return highestRage;
    }

    public BitcoinRateStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return BitcoinRateMarshaller.marshal(this);
    }

    public static class Builder {
        private BPI bpi;

        private float lowesRate;

        private float highestRage;

        private BitcoinRateStatus status;

        private BPI getBpi() {
            return bpi;
        }

        private float getLowesRate() {
            return lowesRate;
        }

        private float getHighestRage() {
            return highestRage;
        }

        public Builder withBPI(BPI bpi) {
            this.bpi = bpi;
            return this;
        }

        public BitcoinRateStatus getStatus() {
            return status;
        }

        public Builder withBPI(float lowesRate) {
            this.lowesRate = lowesRate;
            return this;
        }

        public Builder withHighestRage(float highestRage) {
            this.highestRage = highestRage;
            return this;
        }

        public Builder withStatus(BitcoinRateStatus.BitcoinRateStatusCode code) {
            status = new BitcoinRateStatus(code, "");
            return this;
        }

        public Builder withStatus(BitcoinRateStatus.BitcoinRateStatusCode code, String message) {
            status = new BitcoinRateStatus(code, message);
            return this;
        }

        public BitcoinRate build() {
            return new BitcoinRate(this);
        }
    }

    private static final String pattern = "#,##0.####";
    private static final DecimalFormat formatter = new DecimalFormat(pattern);

    public static void printBitcoinRate(BitcoinRate bitcoinRate) {
        if (BitcoinRateStatus.BitcoinRateStatusCode.OK == bitcoinRate.getStatus().getStatus()) {
            System.out.println(String.format("The current Bitcoin rate at %s, in the requested currency (%s): %s", bitcoinRate.getBpi().getDate(), bitcoinRate.getBpi().getCurrency(), formatFloat(bitcoinRate.getBpi().getRate())));
            System.out.println(String.format("The lowest Bitcoin rate in the last 30 days, in the requested currencY (%s): %s" , bitcoinRate.getBpi().getCurrency(), formatFloat(bitcoinRate.getLowesRate())));
            System.out.println(String.format("The highest Bitcoin rate in the last 30 days, in the requested currencY (%s): %s" , bitcoinRate.getBpi().getCurrency(), formatFloat(bitcoinRate.getHighestRage())));
        } else {
            System.out.println(bitcoinRate.getStatus().getMessage());
        }

    }

    private final static String formatFloat(float value) {
        return formatter.format(value);
    }
}
