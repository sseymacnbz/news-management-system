package com.newsmanagementsystem.utilities;

import com.newsmanagementsystem.model.MainEditor;
import com.newsmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Autowired
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void startApp(){
        if(userRepository.findMainEditors().stream().findAny().isEmpty()){
            MainEditor mainEditor = new MainEditor();
            mainEditor.setEmail("admin@gmail");
            mainEditor.setName("admin");
            mainEditor.setSurname("admin");
            mainEditor.setPassword("admin123");
            userRepository.save(mainEditor);
        }
    }
}
