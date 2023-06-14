package com.newsmanagementsystem.utilities;

import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Component
public class LogUtil {

    private ResourceBundle bundle = ResourceBundle.getBundle("messages");

    public String getMessage(String methodName,String key, int statusCode){
        return "Method-> "+methodName+", Message-> "+bundle.getString(key)+ ", Status Code-> "+statusCode +", Date-> "+LocalDateTime.now();
    }

    public String getMessageWithId(String methodName, String key,Long id, int statusCode){

        String message = bundle.getString(key);
        String result = MessageFormat.format(message, id);
        return "Method-> "+methodName+", Message-> "+result+ ", Status Code-> "+ statusCode +", Date-> "+LocalDateTime.now();
    }
}
