package com.alv.bitcoin.rate.client.service;
/*
 * Created by alysonlv - 2019-03-01
 */

import com.alv.bitcoin.rate.client.service.utils.BPIParser;
import com.alv.bitcoin.rate.client.service.utils.HistoricalRateParser;
import com.alv.bitcoin.rate.service.BitcoinDeskService;
import com.alv.bitcoin.rate.service.domain.BPI;
import com.alv.bitcoin.rate.service.domain.BitcoinDeskCurrency;
import com.alv.bitcoin.rate.service.domain.HistoricalRate;
import com.alv.bitcoin.rate.service.utils.BitcoinRateMarshaller;
import com.google.common.collect.ImmutableList;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.alv.bitcoin.rate.client.service.utils.BitcoinDeskEnpointResolver.*;

public class BitcoinDeskServiceImpl implements BitcoinDeskService {

    private final HttpClient httpClient;

    private static BitcoinDeskService service;

    private BitcoinDeskServiceImpl() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        this.httpClient = httpClientBuilder.build();
    }

    public static BitcoinDeskService getInstace() {
        if (service == null) {
            service = new BitcoinDeskServiceImpl();
        }

        return service;
    }

    /**
     * Get all supported currencies
     * @return
     * @throws IOException
     */
    @Override
    public List<BitcoinDeskCurrency> getSupportedCurrencies() throws IOException {
        HttpGet request = new HttpGet(SUPPORTED_CURRENCY_ENDPOINT);

        Optional<Object> optional = BitcoinRateMarshaller.unmarshal(execute(request), BitcoinDeskCurrency[].class);
        if (optional.isPresent()) {
            BitcoinDeskCurrency[] bitcoinDeskSupportedCurrencies = (BitcoinDeskCurrency[]) optional.get();
            return ImmutableList.copyOf(bitcoinDeskSupportedCurrencies);
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Get the current rate for the currency informed
     * @param currency
     * @return
     * @throws IOException
     */
    @Override
    public Optional<BPI> getCurrentBitcoinRate(String currency) throws IOException {
        HttpGet request = new HttpGet(getCurrentPriceCurrencyEndpoint(currency));
        return BPIParser.parse(execute(request), currency);
    }

    /**
     * Get the historical of the last 30 days based on the currency informed
     * @param currency
     * @return
     * @throws IOException
     */
    @Override
    public Optional<HistoricalRate> getHistoricalBitcoinRate(String currency) throws IOException {
        HttpGet request = new HttpGet(getHistoricalPriceCurrencyEndpoint(currency));
        return HistoricalRateParser.parser(execute(request), currency);
    }

    /**
     * Client that execute the call to the API
     * will return an Optional<String> with the json provided by API
     * In case of any problem an empty optional will be returned
     * @param request
     * @return
     */
    protected Optional<String> execute(HttpGet request) {
        Optional<String> optional = Optional.empty();
        try {
            HttpEntity entity = null;
            CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(request);
            try {
                entity = response.getEntity();
                return Optional.of(EntityUtils.toString(response.getEntity()));
            } finally {
                EntityUtils.consume(entity);
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return optional;
        }
    }

}
