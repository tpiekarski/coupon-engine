package de.delinero.copt.combinations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.utils.CouponRuleBuilder;
import de.delinero.copt.utils.FixtureLoader;
import de.delinero.copt.models.Cart;
import de.delinero.copt.models.Coupon;
import de.delinero.copt.models.CouponRule;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.util.List;

public class CombinationTest extends TestCase {

    public CombinationTest(String testName )
    {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( CombinationTest.class );
    }

    public void testANDCombination() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/emptyCoupon.json"), Coupon.class
        );

        List<String> testRulesFixtures = ImmutableList.of(
            "Category", "Exclude", "MinimumCartValue", "Expiration", "ValidCode"
        );

        CouponRuleBuilder.buildCouponRulesAndExpression(objectMapper, testCoupon, testRulesFixtures);
        CouponEngine couponEngine = new CouponEngine();

        assertTrue(couponEngine.evaluate(testCart, testCoupon));
    }

    public void testORCombination() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/emptyCoupon.json"), Coupon.class
        );

        List<String> testRulesFixtures = ImmutableList.of("Category", "MinimumCartValue");
        testCoupon.setExpression("#Category or #MinimumCartValue");

        CouponRuleBuilder.buildCouponRules(objectMapper, testCoupon, testRulesFixtures);
        CouponEngine couponEngine = new CouponEngine();

        assertTrue(couponEngine.evaluate(testCart, testCoupon));
    }

    public void testANDandORCombination() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/emptyCoupon.json"), Coupon.class
        );

        List<String> testRulesFixtures = ImmutableList.of("ValidCode", "Category", "MinimumCartValue");
        testCoupon.setExpression("#ValidCode and #Category or #MinimumCartValue");

        CouponRuleBuilder.buildCouponRules(objectMapper, testCoupon, testRulesFixtures);
        CouponEngine couponEngine = new CouponEngine();

        assertTrue(couponEngine.evaluate(testCart, testCoupon));
    }

    public void testANDandNOTCombination() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/emptyCoupon.json"), Coupon.class
        );

        List<String> testRulesFixtures = ImmutableList.of("ValidCode", "Category");
        testCoupon.setExpression("#ValidCode and not #Category");

        CouponRuleBuilder.buildCouponRules(objectMapper, testCoupon, testRulesFixtures);
        CouponEngine couponEngine = new CouponEngine();

        assertFalse(couponEngine.evaluate(testCart, testCoupon));
    }

}
