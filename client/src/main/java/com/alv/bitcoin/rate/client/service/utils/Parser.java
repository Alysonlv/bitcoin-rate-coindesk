package com.alv.bitcoin.rate.client.service.utils;
/*
 * Created by alysonlv - 2019-03-01
 */

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Utilitary class to help parse the jsons to domain
 */
public class Parser {

    protected static final String BPI_FIELD = "bpi";
    protected static final String TIME_FIELD = "time";
    protected static final String UPDATED_ISO_FIELD = "updatedISO";

    private static final JsonParser parser = new JsonParser();

    public static final JsonObject getJsonObject(String json) {
        return parser.parse(json).getAsJsonObject();
    }

}
