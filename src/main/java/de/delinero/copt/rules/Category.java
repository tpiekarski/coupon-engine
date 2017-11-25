package de.delinero.copt.rules;

import de.delinero.copt.models.Cart;
import de.delinero.copt.models.Coupon;
import de.delinero.copt.models.CouponRule;
import de.delinero.copt.models.CouponRuleSet;
import org.jeasy.rules.api.Facts;

import java.util.Optional;

public class Category extends AbstractCouponRule {

    public Category() {
        super("Category");
    }

    @Override
    public boolean evaluate(Facts facts) {
        CouponRuleSet ruleSet = (CouponRuleSet) facts.get("ruleSet");
        Cart cart = (Cart) facts.get("cart");

        Optional<CouponRule> rule = ruleSet.getRuleByName(this.name);

        return
            rule.isPresent() &&
            cart.getItems().stream().allMatch(
                item -> item.getCategory().equals(rule.get().getOption())
            );

    }

}
