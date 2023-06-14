package com.newsmanagementsystem.exceptionhandler.exceptiontypes;

public class ContentNotFoundException extends RuntimeException{
    public ContentNotFoundException(Long id) {
        super(String.format("The content with ID %d could not be found",id));
    }
}
