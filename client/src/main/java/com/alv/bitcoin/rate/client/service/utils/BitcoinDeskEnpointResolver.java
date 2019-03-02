package com.alv.bitcoin.rate.client.service.utils;
/*
 * Created by alysonlv - 2019-03-01
 */

import java.time.LocalDate;

/**
 * Utilitary class with all API enpoints
 */
public class BitcoinDeskEnpointResolver {

    private static final String CURRENT_PRICE_ENDPOINT = "https://api.coindesk.com/v1/bpi/currentprice.json";

    private static final String CURRENT_PRICE_CURRENCY_ENDPOINT = "https://api.coindesk.com/v1/bpi/currentprice/%s.json";

    public static final String SUPPORTED_CURRENCY_ENDPOINT = "https://api.coindesk.com/v1/bpi/supported-currencies.json";

    private static final String HISTORICAL_PRICE_CURRENCY_ENDPOINT = "https://api.coindesk.com/v1/bpi/historical/close.json?currency=%s&start=%s&end=%s";

    private BitcoinDeskEnpointResolver() {
    }

    public static String getCurrentPriceEndpoint() {
        return CURRENT_PRICE_ENDPOINT;
    }

    public static String getCurrentPriceCurrencyEndpoint(String currency) {
        return String.format(CURRENT_PRICE_CURRENCY_ENDPOINT, currency.toUpperCase());
    }

    public static String  getHistoricalPriceCurrencyEndpoint(String currency) {
        LocalDate date = LocalDate.now();
        return String.format(HISTORICAL_PRICE_CURRENCY_ENDPOINT, currency.toUpperCase(), date.minusDays(30), date);
    }

}
