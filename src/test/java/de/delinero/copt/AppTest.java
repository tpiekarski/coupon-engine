package de.delinero.copt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class AppTest {

    private ByteArrayOutputStream testStream;

    @Before
    public void setUp() {
        testStream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(testStream));
        System.setErr(new PrintStream(testStream));
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
            stripNewline(testStream.toString())
        );

    }

    @Test
    public void testAppUsageOutput() {
        String[] args = { "anything" };

        App.main(args);

        assertEquals(
            "Usage: java -cp <classpath> de.delinero.copt.App [-v] -c cart.json -p coupon.json",
            stripNewline(testStream.toString())
        );
    }

    @Test
    public void testAppPayloadErrorOutput() {
        String[] args = { "--cart", "nowhere-to-be-found.json", "--coupon", "hidden-in-plain-sight.json" };

        App.main(args);

        assertEquals(
            "Failed opening payload file nowhere-to-be-found.json, aborting.",
            stripNewline(testStream.toString())
        );
    }

    @Test
    public void testAppUnknownRuleErrorOutput() {
        String[] args = {
            "--cart", "src/test/resources/builders/cart.json",
            "--coupon", "src/test/resources/coupons/validation/unknownCoupon.json"
        };

        App.main(args);

        assertEquals(
            "The rule NewRule is unknown or not yet implemented, aborting.",
            stripNewline(testStream.toString())
        );
    }

    private String stripNewline(String input) {
        return input.replace("\n", "").replace("\r", "");
    }

}
