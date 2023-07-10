package com.newsmanagementsystem.exceptionhandler.exceptiontypes;

public class NewsNotFoundException extends RuntimeException{
    public NewsNotFoundException(Long id) {
        super(String.format("The news with ID %d could not be found..",id));
    }
}
