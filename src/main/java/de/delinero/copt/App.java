package de.delinero.copt;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.delinero.copt.builders.CartBuilder;
import de.delinero.copt.builders.CouponBuilder;
import de.delinero.copt.engines.CouponEngine;
import de.delinero.copt.exceptions.InvalidArgumentsException;
import de.delinero.copt.exceptions.UnknownRuleException;
import de.delinero.copt.exceptions.PayloadFileException;
import de.delinero.copt.models.Arguments;
import de.delinero.copt.models.carts.Cart;
import de.delinero.copt.models.coupons.Coupon;
import de.delinero.copt.models.Message;
import de.delinero.copt.modules.CouponEngineModule;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CouponEngineModule());

        try {
            Arguments arguments = new Arguments(args);
            CartBuilder cartBuilder = injector.getInstance(CartBuilder.class);
            CouponBuilder couponBuilder = injector.getInstance(CouponBuilder.class);
            CouponEngine couponEngine = new CouponEngine(arguments.getSilence());

            Cart cart = cartBuilder.build(getPayloadFile(arguments.getCartFilename()));
            Coupon coupon = couponBuilder.build(getPayloadFile(arguments.getCouponFilename()));

            Boolean validationResult = couponEngine.evaluate(cart, coupon.getValidationRules());
            Boolean applicationResult = couponEngine.evaluate(cart, coupon.getApplicationRules());

            System.out.printf(String.format("%s%n", Message.getMessageByResults(validationResult, applicationResult)));

        } catch (InvalidArgumentsException exception) {
            System.out.printf("Usage: java -cp <classpath> de.delinero.copt.App [-v] -c cart.json -p coupon.json%n");
        } catch (PayloadFileException exception) {
            System.err.printf("Failed opening payload file %s, aborting.%n", exception.getFilename());
        } catch (UnknownRuleException exception) {
            System.err.printf("The rule %s is unknown or not yet implemented, aborting.%n", exception.getRuleName());
        }
    }

    private static String getPayloadFile(String filename) throws PayloadFileException {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new PayloadFileException(filename);
        }
    }

}
