package com.alv.bitcoin.rate.service.domain;

import org.apache.commons.lang3.StringUtils;

public class BitcoinDeskCurrency {

    private String currency;

    private String country;

    public BitcoinDeskCurrency(String currency, String country) {
        this.currency = currency;
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean match(String c) {
        return StringUtils.isNotEmpty(c) && StringUtils.equals(this.currency, c);
    }
}
