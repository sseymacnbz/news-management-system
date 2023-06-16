package com.newsmanagementsystem.exceptionhandler;


import com.newsmanagementsystem.exceptionhandler.exceptiontypes.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TIMESTAMP = "timestamp";
    private static final String MESSAGE = "message";
    private static final String PATH = "path";

    @ExceptionHandler(MainEditorNotFoundException.class)
    public ResponseEntity<Object> mainEditorNotFoundException(MainEditorNotFoundException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE,ex.getMessage());
        body.put(PATH,request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotAssignedError(UserNotFoundException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE,ex.getMessage());
        body.put(PATH,request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NewsNotFoundException.class)
    public ResponseEntity<Object> newsNotAssignedError(NewsNotFoundException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE,ex.getMessage());
        body.put(PATH,request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContentNotFoundException.class)
    public ResponseEntity<Object> contentNotFoundException(ContentNotFoundException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE,ex.getMessage());
        body.put(PATH,request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContentNotCreatedException.class)
    public ResponseEntity<Object> contentNotCreatedException(ContentNotCreatedException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE,ex.getMessage());
        body.put(PATH,request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContentsNotFoundException.class)
    public ResponseEntity<Object> contentsNotFoundException(ContentsNotFoundException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE,ex.getMessage());
        body.put(PATH,request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Object> unauthorizedAccessException(UnauthorizedAccessException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE,ex.getMessage());
        body.put(PATH,request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
