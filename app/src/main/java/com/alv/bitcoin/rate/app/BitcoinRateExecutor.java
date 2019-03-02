package com.alv.bitcoin.rate.app;
/*
 * Created by alysonlv - 2019-03-02
 */

import com.alv.bitcoin.rate.client.service.BitcoinDeskServiceImpl;
import com.alv.bitcoin.rate.service.BitcoinDeskService;
import com.alv.bitcoin.rate.service.domain.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

public class BitcoinRateExecutor {

    private BitcoinDeskService service;

    /**
     * BiPRedicate defind to test if the currency informed by user is valid
     */
    private BiPredicate<String, List<BitcoinDeskCurrency>> validCurrency = (currency, list) -> list.stream().anyMatch(bitcoinDeskCurrency -> bitcoinDeskCurrency.match(currency));


    public BitcoinRateExecutor() {
        this.service = BitcoinDeskServiceImpl.getInstace();
    }

    /**
     * Given the currency build the BitcoinRate containing the current, the lowest,
     * the highest and the status of execution
     * @param currency
     * @return
     */
    public BitcoinRate getBitcoinRate(String currency) {

        currency = currency.toUpperCase().trim();

        try {
            List<BitcoinDeskCurrency> bitcoinDeskCurrencies = getSupportedCurrencies();
            if (validCurrency.test(currency, bitcoinDeskCurrencies)) {
                Optional<HistoricalRate> historicalRate = getHistoricalBitcoinRate(currency);

                Optional<BPI> currentRate = getCurrentBitcoinRate(currency);

                if (!currentRate.isPresent()) {
                    return getErrorMessage();
                }

                return new BitcoinRate.Builder()
                        .withBPI(currentRate.get())
                        .withBPI(historicalRate.get().getLowestRate().get().getRate())
                        .withHighestRage(historicalRate.get().getHighestRate().get().getRate())
                        .withStatus(BitcoinRateStatus.BitcoinRateStatusCode.OK)
                        .build();
            } else {
                return new BitcoinRate.Builder()
                        .withStatus(BitcoinRateStatus.BitcoinRateStatusCode.NOK, "Currency not supported: " + currency)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorMessage();
        }


    }

    protected List<BitcoinDeskCurrency> getSupportedCurrencies() throws IOException {
        return service.getSupportedCurrencies();
    }

    protected Optional<HistoricalRate> getHistoricalBitcoinRate(String currency) throws IOException {
        return service.getHistoricalBitcoinRate(currency);
    }

    protected Optional<BPI> getCurrentBitcoinRate(String currency) throws IOException {
        return service.getCurrentBitcoinRate(currency);

    }


    private BitcoinRate getErrorMessage() {
        return new BitcoinRate.Builder()
                .withStatus(BitcoinRateStatus.BitcoinRateStatusCode.NOK, "Could not retrieve rate")
                .build();
    }


}
