package com.alv.bitcoin.rate.client.service.utils;
/*
 * Created by alysonlv - 2019-03-01
 *
{
	"time": {
		"updated": "Mar 1, 2019 20:15:00 UTC",
		"updatedISO": "2019-03-01T20:15:00+00:00",
		"updateduk": "Mar 1, 2019 at 20:15 GMT"
	},
	"disclaimer": "This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org",
	"bpi": {
		"USD": {
			"code": "USD",
			"rate": "3,864.6800",
			"description": "United States Dollar",
			"rate_float": 3864.68
		}
	}
}
 */

import com.alv.bitcoin.rate.service.domain.BPI;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.alv.bitcoin.rate.client.service.utils.Parser.*;
import static com.alv.bitcoin.rate.service.utils.DateParser.parseToLocalDateTime;

/**
 * This class is responsible for parse the JSON to a domain,
 * will navigate on the hierarchy and build the domain properly
 * Used to CurrentPrice
 */
public class BPIParser {

    private static final String RATE_FLOAT_FIELD = "rate_float";

    private BPIParser() {
    }

    public static final Optional<BPI> parse(Optional<String> json, String currency) {
        Optional<BPI> optionalBPI = Optional.empty();

        if (!json.isPresent()) {
            return optionalBPI;
        }

        try {
            JsonObject jsonObject = getJsonObject(json.get());

            float rate = getRate(jsonObject, currency);
            LocalDateTime date = getDate(jsonObject);

            return Optional.of(new BPI(date.toLocalDate(), rate, currency));
        } catch (Exception e) {
            e.printStackTrace();
            return optionalBPI;
        }
    }

    private static final float getRate(JsonObject jsonObject, String currency) {
        return jsonObject.get(BPI_FIELD).getAsJsonObject().get(currency).getAsJsonObject().get(RATE_FLOAT_FIELD).getAsFloat();
    }

    private static final LocalDateTime getDate(JsonObject jsonObject) {
        return parseToLocalDateTime(jsonObject.get(TIME_FIELD).getAsJsonObject().get(UPDATED_ISO_FIELD).getAsString());
    }
}
