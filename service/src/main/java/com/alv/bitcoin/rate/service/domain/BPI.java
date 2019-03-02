package com.alv.bitcoin.rate.service.domain;
/*
 * Created by alysonlv - 2019-03-01
 */

import com.alv.bitcoin.rate.service.utils.BitcoinRateMarshaller;

import java.time.LocalDate;

public class BPI {

    private LocalDate date;

    private float rate;

    private String currency;

    public BPI(LocalDate date, float rate, String currency) {
        this.date = date;
        this.rate = rate;
        this.currency = currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getRate() {
        return rate;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return BitcoinRateMarshaller.marshal(this);
    }
}
