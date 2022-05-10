package com.assigment.suretime.exceptions;


public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String name){
        super("Object: <" + name + "> already exist.");
    }
    public AlreadyExistsException(String name, String helpMessage){
        super("Object: <" + name + "> already exist.\n"+helpMessage);
    }
}
