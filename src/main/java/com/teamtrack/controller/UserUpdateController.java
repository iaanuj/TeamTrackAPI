package com.teamtrack.controller;

import com.teamtrack.dto.UserNameUpdateRequest;
import com.teamtrack.entity.User;
import com.teamtrack.exception.UsernameAlreadyExistsException;
import com.teamtrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserUpdateController {

    @Autowired
    private UserService userService;

    @PutMapping("/update")
    public ResponseEntity<?> updateUserName(@Valid @RequestBody UserNameUpdateRequest userNameUpdateRequest) throws UsernameAlreadyExistsException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();


        User userInDb = userService.findByUserName(currentUsername);
        Optional<User> existUserByUsername = Optional.ofNullable(userService.findByUserName(userNameUpdateRequest.getNewUserName()));
        if(existUserByUsername.isPresent()){
            throw new UsernameAlreadyExistsException("User name already exists");
        }
        userInDb.setUserName(userNameUpdateRequest.getNewUserName());
        userService.saveUser(userInDb);
        return new ResponseEntity<>("user name updated successfully",HttpStatus.OK);
    }
}
