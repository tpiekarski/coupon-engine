package de.delinero.copt;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.delinero.copt.builders.CartBuilder;
import de.delinero.copt.builders.CouponBuilder;
import de.delinero.copt.models.Cart;
import de.delinero.copt.models.Coupon;
import de.delinero.copt.modules.CouponEngineModule;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class App {

    public static void main(String[] args) {
        if (checkArguments(args)) {
            System.out.printf("Usage: copt <cart.json> <coupon.json> <coupon code>\n");
            return;
        }

        Injector injector = Guice.createInjector(new CouponEngineModule());

        CartBuilder cartBuilder = injector.getInstance(CartBuilder.class);
        CouponBuilder couponBuilder = injector.getInstance(CouponBuilder.class);
        CouponEngine couponEngine = injector.getInstance(CouponEngine.class);

        Cart cart = cartBuilder.build(getPayload(args[0]));
        Coupon coupon = couponBuilder.build(getPayload(args[1]));

        Boolean result = couponEngine.evaluate(cart, coupon, args[2]);

        System.out.printf("The result is of coupon evaluation is %s.\n", result);
    }

    private static String getPayload(String filename) {
        try {
            return IOUtils.toString(
                App.class.getResourceAsStream(String.format("/%s", filename)),
                StandardCharsets.UTF_8
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static Boolean checkArguments(String[] args) {
        return (args.length != 3);
    }

}
