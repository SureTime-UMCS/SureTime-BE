package com.assigment.suretime.club;

class ClubNotFoundException extends RuntimeException {

    ClubNotFoundException(String name) {
        super("Could not find club " + name);
    }
}