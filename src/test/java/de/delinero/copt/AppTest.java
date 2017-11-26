package de.delinero.copt;

import de.delinero.copt.exceptions.PayloadFileException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class AppTest {

    private ByteArrayOutputStream outputStream;

    @Rule
    public ExpectedException exception = ExpectedException.none();

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
    public void testAppOutput() {
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

    @Test
    public void testAppUsageOutput() {
        String[] args = { "anything" };

        App.main(args);

        assertEquals(
            "Usage: java -cp <classpath> de.delinero.copt.App cart.json coupon.json [silent]",
            stripNewline(outputStream.toString())
        );
    }

    @Test
    public void testAppIsThrowingPayloadFileException() {
        String[] args = { "nowhere-to-be-found.json", "hidden-in-plain-sight.json" };

        exception.expect(PayloadFileException.class);
        App.main(args);
    }

    private String stripNewline(String input) {
        return input.replace("\n", "").replace("\r", "");
    }

}
