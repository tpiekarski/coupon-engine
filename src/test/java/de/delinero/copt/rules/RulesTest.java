package de.delinero.copt.rules;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.utils.FixtureLoader;
import de.delinero.copt.models.Cart;
import de.delinero.copt.models.Coupon;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class RulesTest {

    private ObjectMapper objectMapper;
    private CouponEngine couponEngine;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        couponEngine = new CouponEngine();
    }

    @Test
    public void testCategoryRule() throws IOException {
        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/application/categoryCoupon.json"), Coupon.class
        );

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testExcludeRule() throws IOException {
        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/application/excludeCoupon.json"), Coupon.class
        );

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testMinimumCartValueRule() throws IOException {
        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/validation/minimumCartValueCoupon.json"), Coupon.class
        );

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    @Test
    public void testExpirationRule() throws IOException {
        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/validation/expirationCoupon.json"), Coupon.class
        );

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    @Test
    public void testValidCodeRule() throws IOException {
        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/validation/validCodeCoupon.json"), Coupon.class
        );

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }
}
