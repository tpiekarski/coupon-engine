package de.delinero.copt.exceptions;

public class DeserializationException extends RuntimeException {

    public DeserializationException(Exception exception) {
        System.err.println(String.format("Failed deserializing JSON payload, aborting.%n%s", exception.getMessage()));
    }

}
