package com.newsmanagementsystem.exceptionhandler.exceptiontypes;

public class ContentsNotFoundException extends RuntimeException{
    public ContentsNotFoundException() {
        super("The contents of the news could not be found.");
    }
}
