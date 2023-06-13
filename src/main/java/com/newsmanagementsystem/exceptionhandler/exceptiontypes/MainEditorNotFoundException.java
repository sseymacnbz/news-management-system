package com.newsmanagementsystem.exceptionhandler.exceptiontypes;

public class MainEditorNotFoundException extends RuntimeException{
    public MainEditorNotFoundException(Long id) {
        super(String.format("The main editor with ID %d could not be found.",id));
    }
}
