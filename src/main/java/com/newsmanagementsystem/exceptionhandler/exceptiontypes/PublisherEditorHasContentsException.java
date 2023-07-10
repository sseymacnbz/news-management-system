package com.newsmanagementsystem.exceptionhandler.exceptiontypes;

public class PublisherEditorHasContentsException extends RuntimeException{
    public PublisherEditorHasContentsException() { super("There are contents related to the publisher editor you are trying to assign.");}
}
