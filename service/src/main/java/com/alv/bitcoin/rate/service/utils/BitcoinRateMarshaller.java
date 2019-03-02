package com.alv.bitcoin.rate.service.utils;
/*
 * Created by alysonlv - 2019-03-01
 */

import com.google.gson.Gson;

import java.util.Optional;

public class BitcoinRateMarshaller {

    private static final Gson gson;

    static {
        gson = new Gson();
    }

    private BitcoinRateMarshaller() {
    }

    public static Optional<Object> unmarshal(Optional<String> json, Class clazz) {
        if (json.isPresent()) {
            return Optional.ofNullable(gson.fromJson(json.get(), clazz));
        } else {
            return Optional.empty();
        }
    }

    public static String marshal(Object o) {
        return gson.toJson(o);
    }
}
