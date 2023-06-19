package com.newsmanagementsystem.exceptionhandler.exceptiontypes;

public class PublisherEditorHasContentsException extends RuntimeException{
    public PublisherEditorHasContentsException() { super("There is content linked to the publisher editor you are trying to delete.");}
}
