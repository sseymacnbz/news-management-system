package com.newsmanagementsystem.exceptionhandler.exceptiontypes;

public class UnauthorizedAccessException extends RuntimeException{
    public UnauthorizedAccessException(){super("You are trying to access an unauthorized location");}
}
