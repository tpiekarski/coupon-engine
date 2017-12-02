package de.delinero.copt.engines;

import de.delinero.copt.exceptions.UnknownRuleException;
import de.delinero.copt.models.*;
import de.delinero.copt.models.carts.Cart;
import de.delinero.copt.models.coupons.CouponRule;
import de.delinero.copt.models.coupons.CouponRuleSet;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.RulesEngineBuilder;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;

public class CouponEngine {

    private final RulesEngine rulesEngine;
    private final CouponExpressionEngine expressionEngine;

    public static final String RULE_LOADING_TEMPLATE = "de.delinero.copt.rules.%s";

    public static final String CART = "cart";
    public static final String RULE_SET = "ruleSet";
    public static final String RESULTS = "results";

    public CouponEngine() {
        this(true);
    }

    public CouponEngine(Boolean silent) {
        this.rulesEngine = RulesEngineBuilder.aNewRulesEngine().withSilentMode(silent).build();
        this.expressionEngine = new CouponExpressionEngine(new SpelExpressionParser());
    }

    public Boolean evaluate(Cart cart, CouponRuleSet ruleSet) throws UnknownRuleException {
        Rules rules = new Rules();
        registerRules(rules, ruleSet.getRules());

        List<EvaluatedResult> results = initializeResults(rules);
        Facts facts = establishFacts(cart, ruleSet, results);

        rulesEngine.fire(rules, facts);

        return expressionEngine.parse(ruleSet.getExpression(), results);
    }

    private Facts establishFacts(Cart cart, CouponRuleSet ruleSet, List<EvaluatedResult> results) {
        Facts facts = new Facts();

        facts.put(CART, cart);
        facts.put(RULE_SET, ruleSet);
        facts.put(RESULTS, results);

        return facts;
    }

    private void registerRules(Rules rules, List<CouponRule> couponRules) throws UnknownRuleException {
        for (CouponRule rule : couponRules) {
            try {
                Class<?> ruleClass = Class.forName(String.format(RULE_LOADING_TEMPLATE, rule.getRuleName()));
                rules.register(ruleClass.newInstance());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException exception) {
                throw new UnknownRuleException(rule.getRuleName());
            }
        }
    }

    private List<EvaluatedResult> initializeResults(Rules rules) {
        List<EvaluatedResult> results = new ArrayList<>();
        rules.forEach((rule -> results.add(new EvaluatedResult(rule.getName(), false))) );

        return results;
    }

}
