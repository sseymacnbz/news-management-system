package com.newsmanagementsystem.exceptionhandler.exceptiontypes;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super(String.format("The user with ID %d could not be found..",id));
    }
}
