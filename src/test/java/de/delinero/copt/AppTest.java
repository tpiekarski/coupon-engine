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
    public final ExpectedException exception = ExpectedException.none();

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
            "--cart", "src/test/resources/builders/cart.json",
            "--coupon", "src/test/resources/builders/coupon/input.json"
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
            "Usage: java -cp <classpath> de.delinero.copt.App [-v] -c cart.json -p coupon.json",
            stripNewline(outputStream.toString())
        );
    }

    @Test
    public void testAppIsThrowingPayloadFileException() {
        String[] args = { "--cart", "nowhere-to-be-found.json", "--coupon", "hidden-in-plain-sight.json" };

        exception.expect(PayloadFileException.class);
        App.main(args);
    }

    private String stripNewline(String input) {
        return input.replace("\n", "").replace("\r", "");
    }

}
