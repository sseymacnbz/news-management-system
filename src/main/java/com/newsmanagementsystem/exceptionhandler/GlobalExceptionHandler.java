package com.newsmanagementsystem.exceptionhandler;

import com.newsmanagementsystem.exceptionhandler.exceptiontypes.HttpMessageNotReadableException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.MainEditorNotFoundException;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.NewsNotFound;
import com.newsmanagementsystem.exceptionhandler.exceptiontypes.UserNotFound;
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> httpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message",ex.getMessage());
        body.put("path",request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MainEditorNotFoundException.class)
    public ResponseEntity<Object> mainEditorNotFoundException(MainEditorNotFoundException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message",ex.getMessage());
        body.put("path",request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<Object> userNotAssignedError(UserNotFound ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message",ex.getMessage());
        body.put("path",request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NewsNotFound.class)
    public ResponseEntity<Object> newsNotAssignedError(NewsNotFound ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message",ex.getMessage());
        body.put("path",request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
