package com.alv.bitcoin.rate.client.service;
/*
 * Created by alysonlv - 2019-03-02
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileMockReader {

    private static final String FILE_PATH = "src/test/resources/json/";

    public static String getFile(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(FILE_PATH + file)));
    }
}
