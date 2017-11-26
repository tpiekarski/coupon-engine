package de.delinero.copt.builders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.delinero.copt.exceptions.DeserializationException;
import de.delinero.copt.models.Coupon;
import de.delinero.copt.utils.FixtureLoader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CouponBuilderTest {

    private ObjectMapper objectMapper;
    private CouponBuilder couponBuilder;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        couponBuilder = new CouponBuilder(objectMapper);
    }

    @Test
    public void testCouponBuilderIsBuildingCoupons() throws JsonProcessingException {
        String payload = FixtureLoader.loadAsString("/builders/coupon/input.json");
        Coupon testCoupon = couponBuilder.build(payload);

        assertEquals(new Integer(20), testCoupon.getDiscount());
        assertEquals("percentage", testCoupon.getType());

        assertEquals(
            FixtureLoader.loadAsString("/builders/coupon/output.json"),
            objectMapper.writeValueAsString(testCoupon)
        );
    }

    @Test
    public void testCouponBuilderIsThrowingDeserializationException() {
         String invalidPayload = "{\"someField\":\"someValue\"}";

         exception.expect(DeserializationException.class);
         couponBuilder.build(invalidPayload);
    }

}
