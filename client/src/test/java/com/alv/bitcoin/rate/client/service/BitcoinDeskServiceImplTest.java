package com.alv.bitcoin.rate.client.service;

import com.alv.bitcoin.rate.service.domain.BPI;
import com.alv.bitcoin.rate.service.domain.BitcoinDeskCurrency;
import com.alv.bitcoin.rate.service.domain.HistoricalRate;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.alv.bitcoin.rate.client.service.FileMockReader.getFile;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

class BitcoinDeskServiceImplTest  {

    private static BitcoinDeskServiceImpl service;

    @BeforeAll
    static void setUp() {
        service = (BitcoinDeskServiceImpl) Mockito.spy(BitcoinDeskServiceImpl.getInstace());
    }

    /**
     * Test recover SupportedCurrencies with success
     * @throws IOException
     */
    @Test
    void getSupportedCurrencies_Success() throws IOException {
        Optional<String> json = Optional.of(getFile("supported-currencies.json"));
        doReturn(json).when(service).execute(any(HttpGet.class));

        List<BitcoinDeskCurrency> bitcoinDeskCurrencies = service.getSupportedCurrencies();

        Assertions.assertEquals(3, bitcoinDeskCurrencies.size());
        Assertions.assertEquals("EUR", bitcoinDeskCurrencies.get(0).getCurrency());
        Assertions.assertEquals("USD", bitcoinDeskCurrencies.get(1).getCurrency());
        Assertions.assertEquals("BRL", bitcoinDeskCurrencies.get(2).getCurrency());

    }

    /**
     * Test recover SupportedCurrencies empty
     * @throws IOException
     */
    @Test
    void getSupportedCurrencies_Empty() throws IOException {
        Optional<String> json = Optional.of(getFile("supported-currencies-empty.json"));
        doReturn(json).when(service).execute(any(HttpGet.class));

        List<BitcoinDeskCurrency> bitcoinDeskCurrencies = service.getSupportedCurrencies();

        Assertions.assertEquals(0, bitcoinDeskCurrencies.size());

    }

    /**
     * Test recover CurrentBitcoinRate with success
     * @throws IOException
     */
    @Test
    void getCurrentBitcoinRate() throws IOException {
        Optional<String> json = Optional.of(getFile("current-price-usd.json"));
        doReturn(json).when(service).execute(any(HttpGet.class));

        Optional<BPI> bpi = service.getCurrentBitcoinRate("USD");

        Assertions.assertTrue(bpi.isPresent());
        Assertions.assertEquals("USD", bpi.get().getCurrency());
        Assertions.assertEquals(3864.68f, bpi.get().getRate());
    }

    /**
     * Test recover CurrentBitcoinRate with empty
     * @throws IOException
     */
    @Test
    void getCurrentBitcoinRate_Empty() throws IOException {
        Optional<String> json = Optional.of(getFile("current-price-usd-empty.json"));
        doReturn(json).when(service).execute(any(HttpGet.class));

        Optional<BPI> bpi = service.getCurrentBitcoinRate("USD");

        Assertions.assertFalse(bpi.isPresent());
    }

    /**
     * Test recover HistoricalBitcoinRate with success
     * @throws IOException
     */
    @Test
    void getHistoricalBitcoinRate() throws IOException {
        Optional<String> json = Optional.of(getFile("historical.json"));
        doReturn(json).when(service).execute(any(HttpGet.class));

        Optional<HistoricalRate> historicalRate = service.getHistoricalBitcoinRate("USD");

        Assertions.assertTrue(historicalRate.isPresent());
        Assertions.assertEquals(30, historicalRate.get().getRates().size());
        Assertions.assertEquals(3385.97f, historicalRate.get().getLowestRate().get().getRate());
        Assertions.assertEquals(4145.4385f, historicalRate.get().getHighestRate().get().getRate());
    }

    /**
     * Test recover HistoricalBitcoinRate with success
     * @throws IOException
     */
    @Test
    void getHistoricalBitcoinRate_Empty() throws IOException {
        Optional<String> json = Optional.of(getFile("historical-empty.json"));
        doReturn(json).when(service).execute(any(HttpGet.class));

        Optional<HistoricalRate> historicalRate = service.getHistoricalBitcoinRate("USD");

        Assertions.assertFalse(historicalRate.isPresent());
    }
}