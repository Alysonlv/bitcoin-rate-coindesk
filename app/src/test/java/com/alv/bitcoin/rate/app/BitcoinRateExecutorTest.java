package com.alv.bitcoin.rate.app;

import com.alv.bitcoin.rate.service.domain.*;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.alv.bitcoin.rate.app.FileMockReader.getFile;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class BitcoinRateExecutorTest {

    public static final String USD = "USD";
    private BitcoinRateExecutor executor;

    @BeforeEach
    void setUp() {
        executor = Mockito.spy(new BitcoinRateExecutor());
    }

    @Test
    void getBitcoinRate() throws IOException {
        List<BitcoinDeskCurrency> bitcoinDeskCurrencies = Arrays.asList(
                new BitcoinDeskCurrency(USD, "USA"),
                new BitcoinDeskCurrency("EUR", "Portugal"),
                new BitcoinDeskCurrency("BLR", "Brazil")
        );

        doReturn(bitcoinDeskCurrencies).when(executor).getSupportedCurrencies();

        Map<LocalDate, BPI> historicalMap = new HashMap<>();
        LocalDate date = LocalDate.now();
        historicalMap.put(date, new BPI(date, 100.98f, USD));
        historicalMap.put(date.minusDays(1), new BPI(date.minusDays(1), 101.98f, USD));
        historicalMap.put(date.minusDays(2), new BPI(date.minusDays(2), 91.98f, USD));
        historicalMap.put(date.minusDays(3), new BPI(date.minusDays(3), 131.98f, USD));
        
        Optional<HistoricalRate> historicalRate = Optional.of(new HistoricalRate(LocalDateTime.now(), historicalMap, USD));
        doReturn(historicalRate).when(executor).getHistoricalBitcoinRate(USD);

        Optional<BPI> bpi = Optional.of(new BPI(date, 102.42f, USD));
        doReturn(bpi).when(executor).getCurrentBitcoinRate(USD);

        BitcoinRate bitcoinRate = executor.getBitcoinRate(USD);
        BitcoinRate.printBitcoinRate(bitcoinRate);

        Assertions.assertEquals(BitcoinRateStatus.BitcoinRateStatusCode.OK, bitcoinRate.getStatus().getStatus());
        Assertions.assertEquals(102.42f, bitcoinRate.getBpi().getRate());
        Assertions.assertEquals(91.98f, bitcoinRate.getLowesRate());
        Assertions.assertEquals(131.98f, bitcoinRate.getHighestRage());
        Assertions.assertEquals(USD, bitcoinRate.getBpi().getCurrency());

    }

    /**
     * Testing getBitcoinRate with invalid currency
     * @throws IOException
     */
    @Test
    void getBitcoinRate_InvalidCurrency() throws IOException {
        List<BitcoinDeskCurrency> bitcoinDeskCurrencies = Arrays.asList(
                new BitcoinDeskCurrency(USD, "USA"),
                new BitcoinDeskCurrency("EUR", "Portugal"),
                new BitcoinDeskCurrency("BLR", "Brazil")
        );

        doReturn(bitcoinDeskCurrencies).when(executor).getSupportedCurrencies();

        Map<LocalDate, BPI> historicalMap = new HashMap<>();
        LocalDate date = LocalDate.now();
        historicalMap.put(date, new BPI(date, 100.98f, USD));
        historicalMap.put(date.minusDays(1), new BPI(date.minusDays(1), 101.98f, USD));
        historicalMap.put(date.minusDays(2), new BPI(date.minusDays(2), 91.98f, USD));
        historicalMap.put(date.minusDays(3), new BPI(date.minusDays(3), 131.98f, USD));

        Optional<HistoricalRate> historicalRate = Optional.of(new HistoricalRate(LocalDateTime.now(), historicalMap, USD));
        doReturn(historicalRate).when(executor).getHistoricalBitcoinRate(USD);

        Optional<BPI> bpi = Optional.of(new BPI(date, 102.42f, USD));
        doReturn(bpi).when(executor).getCurrentBitcoinRate(USD);

        BitcoinRate bitcoinRate = executor.getBitcoinRate("GPR");
        BitcoinRate.printBitcoinRate(bitcoinRate);

        Assertions.assertEquals(BitcoinRateStatus.BitcoinRateStatusCode.NOK, bitcoinRate.getStatus().getStatus());

    }
}
