package com.alv.bitcoin.rate.service;

import com.alv.bitcoin.rate.service.domain.BPI;
import com.alv.bitcoin.rate.service.domain.BitcoinDeskCurrency;
import com.alv.bitcoin.rate.service.domain.HistoricalRate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BitcoinDeskService {

    List<BitcoinDeskCurrency> getSupportedCurrencies() throws IOException;

    Optional<BPI> getCurrentBitcoinRate(String currency) throws IOException;

    Optional<HistoricalRate> getHistoricalBitcoinRate(String currency) throws IOException;
}
