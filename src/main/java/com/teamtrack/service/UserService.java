package com.teamtrack.service;

import com.teamtrack.entity.User;
import com.teamtrack.exception.UsermailAlreadyExistsException;
import com.teamtrack.exception.UsernameAlreadyExistsException;
import com.teamtrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void registerUser(User user) throws UsermailAlreadyExistsException, UsernameAlreadyExistsException {
        Optional<User> existingUsermail = Optional.ofNullable(userRepository.findByUserMail(user.getUserMail()));
        if(existingUsermail.isPresent()){
            throw new UsermailAlreadyExistsException("User mail already exist");
        }
        Optional<User> existingUsername = Optional.ofNullable(userRepository.findByUserName(user.getUserName()));
        if(existingUsername.isPresent()){
            throw new UsernameAlreadyExistsException("User name already exist");
        }
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
    public User findByUserMail(String userMail) {
        return userRepository.findByUserMail(userMail);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
}
