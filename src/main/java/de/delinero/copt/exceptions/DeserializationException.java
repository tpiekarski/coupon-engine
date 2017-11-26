package de.delinero.copt.exceptions;

public class DeserializationException extends RuntimeException {

    public DeserializationException(Exception exception) {
        System.err.println("Failed deserializing JSON payload, aborting.");
        System.err.println(exception.getMessage());
    }

}
