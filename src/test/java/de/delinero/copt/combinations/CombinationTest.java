package de.delinero.copt.combinations;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.delinero.copt.builders.CartBuilder;
import de.delinero.copt.builders.CouponBuilder;
import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.exceptions.UnknownRuleException;
import de.delinero.copt.models.carts.Cart;
import de.delinero.copt.models.coupons.Coupon;
import de.delinero.copt.utils.FixtureLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CombinationTest {

    private CartBuilder cartBuilder;
    private CouponEngine couponEngine;

    private Coupon testCoupon;
    
    @Before
    public void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        cartBuilder = new CartBuilder(objectMapper);
        couponEngine = new CouponEngine();

        CouponBuilder couponBuilder = new CouponBuilder(objectMapper);
        testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/combination/coupon.json"));
    }
    
    @Test
    public void testCombinationOfValidAndApplicable() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/combination/valid-applicable/cart.json"));

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
        assertTrue(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testCombinationOfInvalidButApplicable() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/combination/invalid-applicable/cart.json"));

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
        assertTrue(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testCombinationOfValidButNotApplicable() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/combination/valid-not-applicable/cart.json"));

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
        assertFalse(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testCombinationOfInvalidAndNotApplicable() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/combination/invalid-not-applicable/cart.json"));

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
        assertFalse(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

}
