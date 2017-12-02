package de.delinero.copt.rules;

import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.models.carts.Cart;
import de.delinero.copt.models.coupons.CouponRule;
import de.delinero.copt.models.coupons.CouponRuleSet;
import org.jeasy.rules.api.Facts;

import java.util.Optional;

public class Exclude extends AbstractCouponRule {

    public Exclude() {
        super("Exclude");
    }

    @Override
    public boolean evaluate(Facts facts) {
        CouponRuleSet ruleSet = (CouponRuleSet) facts.get(CouponEngine.RULE_SET);
        Cart cart = (Cart) facts.get(CouponEngine.CART);

        Optional<CouponRule> rule = ruleSet.getRuleByName(this.name);

        return
            rule.isPresent() &&
            cart.getItems().stream().noneMatch(item -> item.getSku().equals(rule.get().getOption()));

    }

}
