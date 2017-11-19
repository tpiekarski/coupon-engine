package de.delinero.copt.combinations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.delinero.copt.CouponEngine;
import de.delinero.copt.FixtureLoader;
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

    public void testCategoryRule() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Cart testCart = objectMapper.readValue(FixtureLoader.loadAsString("/carts/cart.json"), Cart.class);
        Coupon testCoupon = objectMapper.readValue(
            FixtureLoader.loadAsString("/coupons/emptyCoupon.json"), Coupon.class
        );

        List<String> testRulesFixtures = ImmutableList.of(
            "category", "exclude", "minimumCartValue", "timeFrame", "validCode"
        );

        testRulesFixtures.forEach((ruleFixture) -> {
            try {
                testCoupon.addRule(objectMapper.readValue(FixtureLoader.loadAsString(
                    String.format("/rules/%sRule.json", ruleFixture)), CouponRule.class
                ));
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        });

        CouponEngine couponEngine = new CouponEngine();

        assertTrue(couponEngine.evaluate(testCart, testCoupon, "ABC"));
    }

}
