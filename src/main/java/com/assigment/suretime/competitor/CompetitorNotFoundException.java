package com.assigment.suretime.competitor;

public class CompetitorNotFoundException extends RuntimeException {
    public CompetitorNotFoundException(String email) {
        super("Could not find competitor: "+ email);
    }
}
