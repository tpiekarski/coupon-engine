package de.delinero.copt.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.delinero.copt.models.Coupon;
import de.delinero.copt.models.CouponRule;
import de.delinero.copt.models.Scope;

import java.io.IOException;
import java.util.List;

public class CouponRuleBuilder {

    public static void buildCouponRulesAndExpression(
        ObjectMapper objectMapper, Coupon testCoupon, List<String> testRulesFixtures, String scope
    ) {
        for (Integer n = 0; n < testRulesFixtures.size(); n++) {
            try {

                if (scope.equals(Scope.VALIDATION)) {
                    testCoupon.getValidationRules().addRule(objectMapper.readValue(FixtureLoader.loadAsString(
                        String.format("/rules/%s.json", testRulesFixtures.get(n))), CouponRule.class
                    ));

                    if (n == 0) {
                        testCoupon.getValidationRules().setExpression(String.format("#%s", testRulesFixtures.get(n)));
                    } else {
                        testCoupon.getValidationRules().setExpression(String.format(
                            "%s and #%s",
                            testCoupon.getValidationRules().getExpression(),
                            testRulesFixtures.get(n)
                        ));
                    }
                }
                else if (scope.equals(Scope.APPLICATION)) {
                    testCoupon.getApplicationRules().addRule(objectMapper.readValue(FixtureLoader.loadAsString(
                        String.format("/rules/%s.json", testRulesFixtures.get(n))), CouponRule.class
                    ));

                    if (n == 0) {
                        testCoupon.getApplicationRules().setExpression(String.format("#%s", testRulesFixtures.get(n)));
                    } else {
                        testCoupon.getApplicationRules().setExpression(String.format(
                            "%s and #%s",
                            testCoupon.getApplicationRules().getExpression(),
                            testRulesFixtures.get(n)
                        ));
                    }
                }

            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public static void buildCouponRules(
        ObjectMapper objectMapper, Coupon testCoupon, List<String> testRulesFixtures, String scope
    ) {
        for (String testRulesFixture : testRulesFixtures) {
            try {

                if (scope.equals(Scope.VALIDATION)) {
                    testCoupon.getValidationRules().addRule(objectMapper.readValue(FixtureLoader.loadAsString(
                        String.format("/rules/%s.json", testRulesFixture)), CouponRule.class
                    ));
                } else if (scope.equals(Scope.APPLICATION)){
                    testCoupon.getApplicationRules().addRule(objectMapper.readValue(FixtureLoader.loadAsString(
                        String.format("/rules/%s.json", testRulesFixture)), CouponRule.class
                    ));
                }


            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

}
