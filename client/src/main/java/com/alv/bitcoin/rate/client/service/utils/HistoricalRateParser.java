package com.alv.bitcoin.rate.client.service.utils;
/*
 * Created by alysonlv - 2019-03-01
 */

import com.alv.bitcoin.rate.service.domain.BPI;
import com.alv.bitcoin.rate.service.domain.HistoricalRate;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.alv.bitcoin.rate.client.service.utils.Parser.*;
import static com.alv.bitcoin.rate.service.utils.DateParser.parseToLocalDateTime;

/**
 * This class is responsible for parse the JSON to a domain,
 * will navigate on the hierarchy and build the domain properly
 * Used to Historical
 */
public class HistoricalRateParser {


    private HistoricalRateParser() {
    }

    public static final Optional<HistoricalRate> parser(Optional<String> json, String currency) {
        Optional<HistoricalRate> optionalHistoricalRate = Optional.empty();

        if (!json.isPresent()) {
            return optionalHistoricalRate;
        }

        try {
            JsonObject jsonObject = getJsonObject(json.get());

            LocalDateTime dateTime = getDate(jsonObject);
            Map<LocalDate, BPI> map = getHistorical(jsonObject, currency);

            return Optional.of(new HistoricalRate(dateTime, map, currency));
        } catch (Exception e) {
            e.printStackTrace();
            return optionalHistoricalRate;
        }
    }

    private static final Map<LocalDate, BPI> getHistorical(JsonObject jsonObject, String currency) {
        Map<LocalDate, BPI> historicalMap = new HashMap<>();

        jsonObject.get(BPI_FIELD).getAsJsonObject().entrySet()
                .stream()
                .forEach(entry -> historicalMap.put(LocalDate.parse(entry.getKey()), new BPI(LocalDate.parse(entry.getKey()), entry.getValue().getAsFloat(), currency)));


        return historicalMap;
    }

    private static final LocalDateTime getDate(JsonObject jsonObject) {
        return parseToLocalDateTime(jsonObject.get(TIME_FIELD).getAsJsonObject().get(UPDATED_ISO_FIELD).getAsString());
    }
}
