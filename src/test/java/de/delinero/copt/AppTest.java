package de.delinero.copt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class AppTest {

    private ByteArrayOutputStream outputStream;


    @Before
    public void setUp() {
        outputStream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }


    @Test
    public void testApp() {
        String[] args = {
            "src/test/resources/builders/cart.json",
            "src/test/resources/builders/coupon/input.json",
            "true"
        };

        App.main(args);

        assertEquals(
            "Result: The coupon is valid and could be applied to the cart. (Validation: true, Application: true)",
            stripNewline(outputStream.toString())
        );

    }

    private String stripNewline(String input) {
        return input.replace("\n", "").replace("\r", "");
    }

}
