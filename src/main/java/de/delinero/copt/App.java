package de.delinero.copt;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.delinero.copt.builders.CartBuilder;
import de.delinero.copt.builders.CouponBuilder;
import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.models.Cart;
import de.delinero.copt.models.Coupon;
import de.delinero.copt.modules.CouponEngineModule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {

    private static Boolean silent;

    public static void main(String[] args) {
        if (! checkArguments(args)) {
            System.out.printf(
                "Usage: java -cp <classpath> de.delinero.copt.App " +
                "<cart.json> <coupon.json> <coupon code> [<silent>]%n"
            );
            return;
        }

        Injector injector = Guice.createInjector(new CouponEngineModule());

        CartBuilder cartBuilder = injector.getInstance(CartBuilder.class);
        CouponBuilder couponBuilder = injector.getInstance(CouponBuilder.class);
        CouponEngine couponEngine = new CouponEngine(silent);

        Cart cart = cartBuilder.build(getPayloadFile(args[0]));
        Coupon coupon = couponBuilder.build(getPayloadFile(args[1]));

        Boolean result = couponEngine.evaluate(cart, coupon, args[2]);

        System.out.printf("%nThe result of the coupon evaluation is %s.%n", result);
    }

    private static String getPayloadFile(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static Boolean checkArguments(String[] args) {
        if (args.length == 3) {
            silent = true;
        } else if (args.length == 4) {
            silent = Boolean.valueOf(args[3]);
        } else {
            return false;
        }

        return true;
    }

}
