package com.teamtrack.service;

import com.teamtrack.entity.User;
import com.teamtrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void registerUser(User user){
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
}
