package com.newsmanagementsystem.exceptionhandler.exceptiontypes;

public class UserIsAlreadySubscriberException extends RuntimeException{
    public UserIsAlreadySubscriberException(Long id){super(String.format("The user with ID %d is already a subscriber",id));}
}
