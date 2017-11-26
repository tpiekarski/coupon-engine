package de.delinero.copt.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageTest {

    @Test
    public void testMessageGetMessageByResult() {

        assertEquals(
            "Result: The coupon is valid and could be applied to the cart. (Validation: true, Application: true)",
            Message.getMessageByResults(true, true)
        );

        assertEquals(
            "Result: The coupon is valid, but unfortunately can not be applied to the cart. (Validation: true, Application: false)",
            Message.getMessageByResults(true, false)
        );

        assertEquals(
            "Result: The coupon is unfortunately invalid, but could be applied to the cart. (Validation: false, Application: true)",
            Message.getMessageByResults(false, true)
        );

        assertEquals(
            "Result: The coupon is invalid and can not be applied to the cart. (Validation: false, Application: false)",
            Message.getMessageByResults(false, false)
        );

    }

}
