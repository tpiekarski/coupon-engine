package de.delinero.copt.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FixtureLoader {

    public static String loadAsString(String filename) {
        try {
            return IOUtils.toString(loadAsStream(filename), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.println("Could not load fixture " + filename);

            throw new RuntimeException(exception);
        }
    }

    private static InputStream loadAsStream(String filename) {
        return FixtureLoader.class.getResourceAsStream(filename);
    }

}

