package de.delinero.copt.rules;

import de.delinero.copt.models.Cart;
import de.delinero.copt.models.Coupon;
import de.delinero.copt.models.CouponRule;
import org.jeasy.rules.api.Facts;

import java.util.Optional;

public class Exclude extends AbstractCouponRule {

    public Exclude() {
        super("Exclude");
    }

    @Override
    public boolean evaluate(Facts facts) {
        Coupon coupon = (Coupon) facts.get("coupon");
        Cart cart = (Cart) facts.get("cart");

        Optional<CouponRule> rule = coupon.getRuleByName(this.name);

        return
            rule.isPresent() &&
            cart.getItems().stream().noneMatch((item) -> item.getSku().equals(rule.get().getOption()));

    }

}
