package com.alv.bitcoin.rate.service.domain;
/*
 * Created by alysonlv - 2019-03-01
 */

import com.alv.bitcoin.rate.service.utils.BitcoinRateMarshaller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class HistoricalRate {

    private Map<LocalDate, BPI> historicalMap;

    private LocalDateTime updatedTime;

    private String currency;

    private Optional<BPI> lowestRate;

    private Optional<BPI> highestRate;

    public HistoricalRate(LocalDateTime updatedTime, Map<LocalDate, BPI> historicalMap, String currency) {
        this.updatedTime = updatedTime;
        this.historicalMap = historicalMap;
        this.currency = currency;

        lowestRate = historicalMap.values().stream().min(Comparator.comparing(BPI::getRate));
        highestRate = historicalMap.values().stream().max(Comparator.comparing(BPI::getRate));
    }

    @Override
    public String toString() {
        return BitcoinRateMarshaller.marshal(this);
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public String getCurrency() {
        return currency;
    }

    public Optional<BPI> getLowestRate() {
        return lowestRate;
    }

    public Optional<BPI> getHighestRate() {
        return highestRate;
    }

    public Optional<BPI> getRate(LocalDate date) {
        return Optional.ofNullable(historicalMap.get(date));
    }



    public Collection<BPI> getRates() {
        return historicalMap.values();
    }


}
