package de.delinero.copt.rules;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.delinero.copt.builders.CartBuilder;
import de.delinero.copt.builders.CouponBuilder;
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

    private CartBuilder cartBuilder;
    private CouponBuilder couponBuilder;
    private CouponEngine couponEngine;

    @Before
    public void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        cartBuilder = new CartBuilder(objectMapper);
        couponBuilder = new CouponBuilder(objectMapper);
        couponEngine = new CouponEngine();
    }

    @Test
    public void testCategoryRule() throws IOException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/application/categoryCoupon.json"));

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testExcludeRule() throws IOException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/application/excludeCoupon.json"));

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testMinimumCartValueRule() throws IOException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/validation/minimumCartValueCoupon.json"));

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    @Test
    public void testExpirationRule() throws IOException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/validation/expirationCoupon.json"));

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    @Test
    public void testValidCodeRule() throws IOException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/validation/validCodeCoupon.json"));

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }
}
