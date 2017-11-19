package de.delinero.copt.rules;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.delinero.copt.CouponEngine;
import de.delinero.copt.FixtureLoader;
import de.delinero.copt.models.Cart;
import de.delinero.copt.models.Coupon;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

public class RulesTest extends TestCase {

    public RulesTest(String testName )
    {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( RulesTest.class );
    }

    public void testCategoryRule() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/categoryCoupon.json"), Coupon.class
        );

        CouponEngine couponEngine = new CouponEngine();

        assertTrue(couponEngine.evaluate(testCart, testCoupon, "ABC"));
    }

    public void testExcludeRule() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/excludeCoupon.json"), Coupon.class
        );

        CouponEngine couponEngine = new CouponEngine();

        assertFalse(couponEngine.evaluate(testCart, testCoupon, "ABC"));
    }

    public void testMinimumCartValueRule() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/minimumCartValueCoupon.json"), Coupon.class
        );

        CouponEngine couponEngine = new CouponEngine();

        assertTrue(couponEngine.evaluate(testCart, testCoupon, "ABC"));
    }

    public void testTimeFrameRule() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/timeFrameCoupon.json"), Coupon.class
        );

        CouponEngine couponEngine = new CouponEngine();

        assertFalse(couponEngine.evaluate(testCart, testCoupon, "ABC"));
    }

    public void testValidCodeRule() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/validCodeCoupon.json"), Coupon.class
        );

        CouponEngine couponEngine = new CouponEngine();

        assertTrue(couponEngine.evaluate(testCart, testCoupon, "ABC"));
    }
}
