package com.assigment.suretime.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String className, String email) {
        super("Could not find object " + className + ":<" + email + ">.");
    }
}
