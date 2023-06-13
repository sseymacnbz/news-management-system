package com.newsmanagementsystem.exceptionhandler.exceptiontypes;

public class UserNotFound extends RuntimeException{
    public UserNotFound(Long id) {
        super(String.format("The user with ID %d could not be found..",id));
    }
}
