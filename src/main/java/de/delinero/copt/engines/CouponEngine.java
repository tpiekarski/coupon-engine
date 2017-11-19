package de.delinero.copt.engines;

import de.delinero.copt.models.Cart;
import de.delinero.copt.models.Coupon;
import de.delinero.copt.models.CouponRule;
import de.delinero.copt.models.EvaluatedResult;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.RulesEngineBuilder;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponEngine {

    private final RulesEngine rulesEngine;
    private final CouponExpressionEngine expressionEngine;

    public CouponEngine() {
        this(true);
    }

    public CouponEngine(Boolean silent) {
        this.rulesEngine = RulesEngineBuilder.aNewRulesEngine().withSilentMode(silent).build();
        this.expressionEngine = new CouponExpressionEngine(new SpelExpressionParser());
    }

    public Boolean evaluate(Cart cart, Coupon coupon, String code) {
        Rules rulesSet = new Rules();
        registerRules(rulesSet, coupon.getRules());

        List<EvaluatedResult> results = initializeResults(rulesSet);
        Facts facts = establishFacts(cart, coupon, code, results);

        rulesEngine.fire(rulesSet, facts);

        return expressionEngine.parse(coupon.getExpression(), results);
    }

    private Facts establishFacts(Cart cart, Coupon coupon, String code, List<EvaluatedResult> results) {
        Facts facts = new Facts();

        facts.put("cart", cart);
        facts.put("coupon", coupon);
        facts.put("code", code);
        facts.put("results", results);

        return facts;
    }

    private void registerRules(Rules rulesSet, List<CouponRule> couponRules) {
        for (CouponRule rule : couponRules) {
            try {
                Class<?> ruleClass = Class.forName(String.format("de.delinero.copt.rules.%s", rule.getRuleName()));
                rulesSet.register(ruleClass.newInstance());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException exception) {
                break;
            }
        }
    }

    private List<EvaluatedResult> initializeResults(Rules rulesSet) {
        List<EvaluatedResult> results = new ArrayList<>();
        rulesSet.forEach(((rule) -> results.add(new EvaluatedResult(rule.getName(), false))) );

        return results;
    }

    private List<Boolean> extractBooleans(HashMap<String, Boolean> results) {
        List<Boolean> booleans = new ArrayList<>();

        for (Map.Entry<String, Boolean> ruleResult: results.entrySet()) {
            booleans.add(ruleResult.getValue());
        }

        return booleans;
    }

}
