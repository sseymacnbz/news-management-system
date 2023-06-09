package com.newsmanagementsystem.utilities;

import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Component
public class LogUtil {

    private ResourceBundle bundle = ResourceBundle.getBundle("messages");

    public String getMessage(String methodName,String key){
        return "Method->"+methodName+", message->"+bundle.getString(key)+", date->"+LocalDateTime.now();
    }

    public String getMessageWithId(String methodName, String key,Long id){

        String message = bundle.getString(key);
        String result = MessageFormat.format(message, id);
        return "Method->"+methodName+", message->"+result+", date->"+LocalDateTime.now();
    }
}
