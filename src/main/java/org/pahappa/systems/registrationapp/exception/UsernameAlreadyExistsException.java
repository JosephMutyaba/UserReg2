package org.pahappa.systems.registrationapp.exception;

public class UsernameAlreadyExistsException extends Exception{
    public UsernameAlreadyExistsException(String message){
        super(message);
    }
}
