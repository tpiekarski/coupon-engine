package de.delinero.copt.models;

import de.delinero.copt.exceptions.InvalidArgumentsException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ArgumentsTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private final String cartSwitch;
    private final String couponSwitch;
    private final String verboseSwitch;

    @Parameterized.Parameters(name = "{index}: Arguments: {0}, {1}, {2}")
    public static Collection<Object[]> getSwitchCollection() {
        return Arrays.asList(
            new Object[][] {
                {"--cart", "--coupon", "--verbose"},
                {"-c", "-p", "-v"},
                {"/C", "/P", "/V"}
            }
        );
    }

    public ArgumentsTest(String cartSwitch, String couponSwitch, String verboseSwitch) {
        this.cartSwitch = cartSwitch;
        this.couponSwitch = couponSwitch;
        this.verboseSwitch = verboseSwitch;
    }

    @Test
    public void testArgumentsMakeProcessedArgumentsAvailable() {
        String[] testArgs = { cartSwitch, "cart.json", couponSwitch, "coupon.json", verboseSwitch };

        Arguments testArguments = new Arguments(testArgs);

        assertEquals("cart.json", testArguments.getCartFilename());
        assertEquals("coupon.json", testArguments.getCouponFilename());
        assertFalse(testArguments.getSilence());
    }

    @Test
    public void testArgumentsSetsDefaultValueForSilence() {
        String[] testArgs = { cartSwitch, "cart.json", couponSwitch, "coupon.json" };

        Arguments testArguments = new Arguments(testArgs);

        assertTrue(testArguments.getSilence());
    }

    @Test
    public void testArgumentsIsThrowingInvalidArgumentsExceptionWithTooFewArguments() {
        String[] testArgs = { cartSwitch, "cart.json" };

        exception.expect(InvalidArgumentsException.class);
        Arguments testArguments = new Arguments(testArgs);
    }

    @Test
    public void testArgumentsIsThrowingInvalidArgumentsExceptionWithTooManyArguments() {
        String[] testArgs = { cartSwitch, "cart.json", couponSwitch, "coupon.json", verboseSwitch, "oneTooMany" };

        exception.expect(InvalidArgumentsException.class);
        Arguments testArguments = new Arguments(testArgs);
    }

}
