package de.delinero.copt.rules;

import de.delinero.copt.models.Cart;
import de.delinero.copt.models.Coupon;
import de.delinero.copt.models.CouponRule;
import org.jeasy.rules.api.Facts;

import java.util.Optional;

public class Category extends AbstractCouponRule {

    public Category() {
        super("Category");
    }

    @Override
    public boolean evaluate(Facts facts) {
        Coupon coupon = (Coupon) facts.get("coupon");
        Cart cart = (Cart) facts.get("cart");

        Optional<CouponRule> rule = coupon.getRuleByName(this.name);

        if (! rule.isPresent()) {
            return false;
        }

        return cart.getItems().stream().allMatch(
            (item) -> item.getCategory().equals(rule.get().getOption())
        );
    }

}
