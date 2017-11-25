package de.delinero.copt.combinations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.delinero.copt.builders.CartBuilder;
import de.delinero.copt.builders.CouponBuilder;
import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.models.Cart;
import de.delinero.copt.models.Coupon;
import de.delinero.copt.models.Scope;
import de.delinero.copt.utils.CouponRuleBuilder;
import de.delinero.copt.utils.FixtureLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ApplicationTest {

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
    public void testANDApplicationRulesCombination() throws IOException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/emptyCoupon.json"));

        List<String> testRulesFixtures = ImmutableList.of("Category", "Exclude");
        testCoupon.getApplicationRules().setExpression("#Category and #Exclude");

        CouponRuleBuilder.buildCouponRulesAndExpression(
            objectMapper, testCoupon, testRulesFixtures, Scope.APPLICATION
        );

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testORApplicationCombination() throws IOException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/emptyCoupon.json"));

        List<String> testRulesFixtures = ImmutableList.of("Category", "Exclude");
        testCoupon.getApplicationRules().setExpression("#Category or #Exclude");

        CouponRuleBuilder.buildCouponRules(
            objectMapper, testCoupon, testRulesFixtures, Scope.APPLICATION
        );

        assertTrue(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

    @Test
    public void testANDandNOTApplicationRulesCombination() throws IOException {
        Cart testCart = cartBuilder.build(FixtureLoader.loadAsString("/carts/cart.json"));
        Coupon testCoupon = couponBuilder.build(FixtureLoader.loadAsString("/coupons/emptyCoupon.json"));

        List<String> testRulesFixtures = ImmutableList.of("Category", "Exclude");
        testCoupon.getApplicationRules().setExpression("#Category and not #Exclude");

        CouponRuleBuilder.buildCouponRules(
            objectMapper, testCoupon, testRulesFixtures, Scope.APPLICATION
        );

        assertFalse(couponEngine.evaluate(testCart, testCoupon.getApplicationRules()));
    }

}
