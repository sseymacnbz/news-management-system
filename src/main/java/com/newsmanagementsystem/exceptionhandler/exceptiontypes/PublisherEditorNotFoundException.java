package com.newsmanagementsystem.exceptionhandler.exceptiontypes;

public class PublisherEditorNotFoundException extends RuntimeException{
    public PublisherEditorNotFoundException(Long id) {
        super(String.format("The publisher editor with ID %d could not be found..",id));
    }
}
