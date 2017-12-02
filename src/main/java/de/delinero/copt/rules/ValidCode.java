package de.delinero.copt.rules;

import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.models.carts.Cart;
import de.delinero.copt.models.coupons.CouponRule;
import de.delinero.copt.models.coupons.CouponRuleSet;
import org.jeasy.rules.api.Facts;

import java.util.Optional;

public class ValidCode extends AbstractCouponRule {

    public ValidCode() {
        super("ValidCode");
    }

    @Override
    public boolean evaluate(Facts facts) {
        Cart cart = (Cart) facts.get(CouponEngine.CART);
        CouponRuleSet ruleSet = (CouponRuleSet) facts.get(CouponEngine.RULE_SET);

        Optional<CouponRule> rule = ruleSet.getRuleByName(this.name);

        return rule.isPresent() && rule.get().getOption().equals(cart.getCode());
    }

}
