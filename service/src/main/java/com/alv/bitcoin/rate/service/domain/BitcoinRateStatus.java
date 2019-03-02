package com.alv.bitcoin.rate.service.domain;
/*
 * Created by alysonlv - 2019-03-02
 */

public class BitcoinRateStatus {

    private BitcoinRateStatusCode status;

    private String message;

    public BitcoinRateStatusCode getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public BitcoinRateStatus(BitcoinRateStatusCode status, String message) {
        this.status = status;
        this.message = message;
    }

    public static enum BitcoinRateStatusCode {
        OK,
        NOK
    }
}
