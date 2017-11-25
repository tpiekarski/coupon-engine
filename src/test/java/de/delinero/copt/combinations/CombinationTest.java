package de.delinero.copt.combinations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.models.Cart;
import de.delinero.copt.models.Coupon;
import de.delinero.copt.utils.FixtureLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CombinationTest {

    private ObjectMapper objectMapper;
    private CouponEngine couponEngine;

    private Coupon testCoupon;

    @Before
    public void setUp() throws IOException {
        objectMapper = new ObjectMapper();
        couponEngine = new CouponEngine();

        testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/combination/coupon.json"), Coupon.class
        );
    }

    @Test
    public void testCombinationOfValidAndApplicable() throws IOException {
        Cart testCart = objectMapper.readValue(
            FixtureLoader.loadAsString("/combination/valid-applicable/cart.json"), Cart.class
        );

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
        assertTrue(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testCombinationOfInvalidButApplicable() throws IOException {
        Cart testCart = objectMapper.readValue(
            FixtureLoader.loadAsString("/combination/invalid-applicable/cart.json"), Cart.class
        );

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
        assertTrue(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testCombinationOfValidButNotApplicable() throws IOException {
        Cart testCart = objectMapper.readValue(
            FixtureLoader.loadAsString("/combination/valid-not-applicable/cart.json"), Cart.class
        );

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
        assertFalse(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testCombinationOfInvalidAndNotApplicable() throws IOException {
        Cart testCart = objectMapper.readValue(
            FixtureLoader.loadAsString("/combination/invalid-not-applicable/cart.json"), Cart.class
        );

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
        assertFalse(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

}
