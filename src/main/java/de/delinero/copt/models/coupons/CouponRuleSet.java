package de.delinero.copt.models.coupons;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CouponRuleSet {

    @JsonProperty("rules")
    private final List<CouponRule> rules;

    @JsonProperty("expression")
    private String expression;

    public CouponRuleSet() {
        rules = new ArrayList<>();
    }

    public List<CouponRule> getRules() {
        return rules;
    }

    public Optional<CouponRule> getRuleByName(String name) {
        return rules.stream().filter(rule -> (rule.getRuleName().equals(name))).findFirst();
    }

    public void addRule(CouponRule rule) {
        rules.add(rule);
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
