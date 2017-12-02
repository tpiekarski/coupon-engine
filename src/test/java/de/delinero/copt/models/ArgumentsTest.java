package de.delinero.copt.models;

import de.delinero.copt.exceptions.InvalidArgumentsException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArgumentsTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testArgumentsMakeProcessedArgumentsAvailable() {
        String[] testArgs = { "cart.json", "coupon.json", "true" };

        Arguments testArguments = new Arguments(testArgs);

        assertEquals("cart.json", testArguments.getCartFilename());
        assertEquals("coupon.json", testArguments.getCouponFilename());
        assertTrue(testArguments.getSilence());
    }

    @Test
    public void testArgumentsSetsDefaultValueForSilence() {
        String[] testArgs = { "cart.json", "coupon.json" };

        Arguments testArguments = new Arguments(testArgs);

        assertTrue(testArguments.getSilence());
    }

    @Test
    public void testArgumentsIsThrowingInvalidArgumentsExceptionWithTooFewArguments() {
        String[] testArgs = { "cart.json" };

        exception.expect(InvalidArgumentsException.class);
        Arguments testArguments = new Arguments(testArgs);
    }

    @Test
    public void testArgumentsIsThrowingInvalidArgumentsExceptionWithTooManyArguments() {
        String[] testArgs = { "cart.json", "coupon.json", "false", "another-file.json" };

        exception.expect(InvalidArgumentsException.class);
        Arguments testArguments = new Arguments(testArgs);
    }

}
