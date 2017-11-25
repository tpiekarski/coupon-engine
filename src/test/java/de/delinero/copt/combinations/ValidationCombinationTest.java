package de.delinero.copt.combinations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.utils.CouponRuleBuilder;
import de.delinero.copt.utils.FixtureLoader;
import de.delinero.copt.models.Cart;
import de.delinero.copt.models.Coupon;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.util.List;

public class ValidationCombinationTest extends TestCase {

    public ValidationCombinationTest(String testName )
    {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( ValidationCombinationTest.class );
    }

    public void testANDValidationRulesCombination() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/emptyCoupon.json"), Coupon.class
        );

        List<String> testRulesFixtures = ImmutableList.of(
            "MinimumCartValue", "Expiration", "ValidCode"
        );

        CouponRuleBuilder.buildCouponRulesAndExpression(objectMapper, testCoupon, testRulesFixtures);
        CouponEngine couponEngine = new CouponEngine();

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    public void testORValidationCombination() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/emptyCoupon.json"), Coupon.class
        );

        List<String> testRulesFixtures = ImmutableList.of("ValidCode", "MinimumCartValue");
        testCoupon.getValidationRules().setExpression("#ValidCode or #MinimumCartValue");

        CouponRuleBuilder.buildCouponRules(objectMapper, testCoupon, testRulesFixtures);
        CouponEngine couponEngine = new CouponEngine();

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    public void testANDandORValidationRulesCombination() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/emptyCoupon.json"), Coupon.class
        );

        List<String> testRulesFixtures = ImmutableList.of("ValidCode", "Expiration", "MinimumCartValue");
        testCoupon.getValidationRules().setExpression("#ValidCode and #Expiration or #MinimumCartValue");

        CouponRuleBuilder.buildCouponRules(objectMapper, testCoupon, testRulesFixtures);
        CouponEngine couponEngine = new CouponEngine();

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    public void testANDandNOTValidationRulesCombination() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/emptyCoupon.json"), Coupon.class
        );

        List<String> testRulesFixtures = ImmutableList.of("ValidCode", "MinimumCartValue");
        testCoupon.getValidationRules().setExpression("#ValidCode and not #MinimumCartValue");

        CouponRuleBuilder.buildCouponRules(objectMapper, testCoupon, testRulesFixtures);
        CouponEngine couponEngine = new CouponEngine();

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

}
