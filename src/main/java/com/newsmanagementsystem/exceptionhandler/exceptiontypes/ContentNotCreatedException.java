package com.newsmanagementsystem.exceptionhandler.exceptiontypes;

public class ContentNotCreatedException extends RuntimeException{
    public ContentNotCreatedException() {
        super("Failed to create content.");
    }
}
