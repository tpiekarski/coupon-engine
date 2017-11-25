package de.delinero.copt.rules;

import de.delinero.copt.models.Cart;
import de.delinero.copt.models.CouponRule;
import de.delinero.copt.models.CouponRuleSet;
import org.jeasy.rules.api.Facts;

import java.util.Optional;

public class ValidCode extends AbstractCouponRule {

    public ValidCode() {
        super("ValidCode");
    }

    @Override
    public boolean evaluate(Facts facts) {
        Cart cart = (Cart) facts.get("cart");
        CouponRuleSet ruleSet = (CouponRuleSet) facts.get("ruleSet");

        Optional<CouponRule> rule = ruleSet.getRuleByName(this.name);

        return rule.isPresent() && rule.get().getOption().equals(cart.getCode());
    }

}
