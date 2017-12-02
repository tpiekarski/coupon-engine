package de.delinero.copt.rules;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.delinero.copt.builders.CartBuilder;
import de.delinero.copt.builders.CouponBuilder;
import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.exceptions.UnknownRuleException;
import de.delinero.copt.utils.FixtureLoader;
import de.delinero.copt.models.carts.Cart;
import de.delinero.copt.models.coupons.Coupon;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class RulesTest {

    private CartBuilder cartBuilder;
    private CouponBuilder couponBuilder;
    private CouponEngine couponEngine;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        cartBuilder = new CartBuilder(objectMapper);
        couponBuilder = new CouponBuilder(objectMapper);
        couponEngine = new CouponEngine();
    }

    @Test
    public void testCategoryRule() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/application/categoryCoupon.json"));

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testExcludeRule() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/application/excludeCoupon.json"));

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testUnknownApplicationRuleThrowsUnknownRuleException() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/application/unknownCoupon.json"));

        exception.expect(UnknownRuleException.class);
        couponEngine.evaluate(testCart, testCoupon.getApplicationRules());
    }

    @Test
    public void testMinimumCartValueRule() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/validation/minimumCartValueCoupon.json"));

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    @Test
    public void testExpirationRule() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/validation/expirationCoupon.json"));

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    @Test
    public void testValidCodeRule() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/validation/validCodeCoupon.json"));

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    @Test
    public void testUnknownValidationRuleThrowsUnknownRuleException() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/validation/unknownCoupon.json"));

        exception.expect(UnknownRuleException.class);
        couponEngine.evaluate(testCart, testCoupon.getValidationRules());
    }
}
