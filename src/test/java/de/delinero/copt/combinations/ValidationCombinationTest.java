package de.delinero.copt.combinations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.utils.CouponRuleBuilder;
import de.delinero.copt.utils.FixtureLoader;
import de.delinero.copt.models.Cart;
import de.delinero.copt.models.Coupon;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ValidationCombinationTest {

    private ObjectMapper objectMapper;
    private CouponEngine couponEngine;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        couponEngine = new CouponEngine();
    }

    @Test
    public void testANDValidationRulesCombination() throws IOException {
        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/emptyCoupon.json"), Coupon.class
        );

        List<String> testRulesFixtures = ImmutableList.of(
            "MinimumCartValue", "Expiration", "ValidCode"
        );

        CouponRuleBuilder.buildCouponRulesAndExpression(objectMapper, testCoupon, testRulesFixtures);

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    @Test
    public void testORValidationCombination() throws IOException {
        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/emptyCoupon.json"), Coupon.class
        );

        List<String> testRulesFixtures = ImmutableList.of("ValidCode", "MinimumCartValue");
        testCoupon.getValidationRules().setExpression("#ValidCode or #MinimumCartValue");

        CouponRuleBuilder.buildCouponRules(objectMapper, testCoupon, testRulesFixtures);

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    @Test
    public void testANDandORValidationRulesCombination() throws IOException {
        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/emptyCoupon.json"), Coupon.class
        );

        List<String> testRulesFixtures = ImmutableList.of("ValidCode", "Expiration", "MinimumCartValue");
        testCoupon.getValidationRules().setExpression("#ValidCode and #Expiration or #MinimumCartValue");

        CouponRuleBuilder.buildCouponRules(objectMapper, testCoupon, testRulesFixtures);

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

    @Test
    public void testANDandNOTValidationRulesCombination() throws IOException {
        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/emptyCoupon.json"), Coupon.class
        );

        List<String> testRulesFixtures = ImmutableList.of("ValidCode", "MinimumCartValue");
        testCoupon.getValidationRules().setExpression("#ValidCode and not #MinimumCartValue");

        CouponRuleBuilder.buildCouponRules(objectMapper, testCoupon, testRulesFixtures);

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getValidationRules()));
    }

}
