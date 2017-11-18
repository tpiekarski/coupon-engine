package de.delinero.copt.rules;

import de.delinero.copt.models.Coupon;
import de.delinero.copt.models.CouponRule;
import org.jeasy.rules.api.Facts;

import java.util.Optional;

public class ValidCode extends AbstractCouponRule {

    public ValidCode() {
        super("ValidCode");
    }

    @Override
    public boolean evaluate(Facts facts) {
        Coupon coupon = (Coupon) facts.get("coupon");
        String code = (String) facts.get("code");

        Optional<CouponRule> rule = coupon.getRuleByName(this.name);

        return rule.isPresent() && rule.get().getOption().equals(code);
    }

}
