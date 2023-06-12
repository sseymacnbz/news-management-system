package com.newsmanagementsystem.utilities;

import com.newsmanagementsystem.model.MainEditor;
import com.newsmanagementsystem.model.User;
import com.newsmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(!userRepository.existsUserById(1L)){
            User user = new User();
            user.setEmail("a");
            user.setName("a");
            user.setSurname("a");
            user.setPassword("a");
            userRepository.save(user);
            userRepository.assignToMainEditor(1L);
        }

    }
}
