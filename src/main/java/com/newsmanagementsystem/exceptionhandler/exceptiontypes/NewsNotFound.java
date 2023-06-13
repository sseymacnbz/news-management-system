package com.newsmanagementsystem.exceptionhandler.exceptiontypes;

public class NewsNotFound extends RuntimeException{
    public NewsNotFound(Long id) {
        super(String.format("The news with ID %d could not be found..",id));
    }
}
