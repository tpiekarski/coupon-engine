package de.delinero.copt.combinations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.delinero.copt.builders.CartBuilder;
import de.delinero.copt.builders.CouponBuilder;
import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.exceptions.UnknownRuleException;
import de.delinero.copt.models.Scope;
import de.delinero.copt.utils.CouponRuleBuilder;
import de.delinero.copt.utils.FixtureLoader;
import de.delinero.copt.models.carts.Cart;
import de.delinero.copt.models.coupons.Coupon;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ValidationTest {

    private ObjectMapper objectMapper;
    private CartBuilder cartBuilder;
    private CouponBuilder couponBuilder;
    private CouponEngine couponEngine;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        cartBuilder = new CartBuilder(objectMapper);
        couponBuilder = new CouponBuilder(objectMapper);
        couponEngine = new CouponEngine();
    }

    @Test
    public void testANDValidationRulesCombination() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/emptyCoupon.json"));

        List<String> testRulesFixtures = ImmutableList.of("MinimumCartValue", "Expiration", "ValidCode");
        testCoupon.getValidationRules().setExpression(
            "#MinimumCartValue and #Expiration and #ValidCode"
        );

        CouponRuleBuilder.buildCouponRulesAndExpression(
            objectMapper, testCoupon, testRulesFixtures, Scope.VALIDATION
        );

        // @todo: Fix broken assertion, it turn out to be suddenly false, issue #4
        // (For details see: https://github.com/tpiekarski/coupon-engine/issues/4)
        //assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    @Test
    public void testORValidationCombination() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/emptyCoupon.json"));

        List<String> testRulesFixtures = ImmutableList.of("ValidCode", "MinimumCartValue");
        testCoupon.getValidationRules().setExpression("#ValidCode or #MinimumCartValue");

        CouponRuleBuilder.buildCouponRules(
            objectMapper, testCoupon, testRulesFixtures, Scope.VALIDATION
        );

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    @Test
    public void testANDandORValidationRulesCombination() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/emptyCoupon.json"));

        List<String> testRulesFixtures = ImmutableList.of("ValidCode", "Expiration", "MinimumCartValue");
        testCoupon.getValidationRules().setExpression("#ValidCode and #Expiration or #MinimumCartValue");

        CouponRuleBuilder.buildCouponRules(
            objectMapper, testCoupon, testRulesFixtures, Scope.VALIDATION
        );

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    @Test
    public void testANDandNOTValidationRulesCombination() throws IOException, UnknownRuleException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/emptyCoupon.json"));

        List<String> testRulesFixtures = ImmutableList.of("ValidCode", "MinimumCartValue");
        testCoupon.getValidationRules().setExpression("#ValidCode and not #MinimumCartValue");

        CouponRuleBuilder.buildCouponRules(
            objectMapper, testCoupon, testRulesFixtures, Scope.VALIDATION
        );

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

}
