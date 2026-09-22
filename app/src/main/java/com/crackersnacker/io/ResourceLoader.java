package com.crackersnacker.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceLoader {
    public static String loadTextResource(String filepath) throws IOException {
        InputStream is = ResourceLoader.class.getClassLoader().getResourceAsStream(filepath);
        return readFromInputStream(is);
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
