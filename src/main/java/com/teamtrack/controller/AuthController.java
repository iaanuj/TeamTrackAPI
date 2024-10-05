package com.teamtrack.controller;

import com.teamtrack.dto.ApiResponse;
import com.teamtrack.dto.LoginRequest;
import com.teamtrack.exception.UsermailAlreadyExistsException;
import com.teamtrack.exception.UsernameAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import com.teamtrack.entity.User;
import com.teamtrack.service.UserDetailsServiceImpl;
import com.teamtrack.service.UserService;
import com.teamtrack.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody User user){
        try {
            userService.registerUser(user);
            return new ResponseEntity<>(new ApiResponse("user registered successfully.",true),HttpStatus.CREATED);
        }
        catch (UsermailAlreadyExistsException e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(),false),HttpStatus.CONFLICT);
        }
        catch (UsernameAlreadyExistsException e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(),false),HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("An error occurred during registration.",false),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/log-in")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest user){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(),user.getUserPassword()));
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(new ApiResponse(jwt), HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>(new ApiResponse("wrong username or password"),HttpStatus.BAD_REQUEST);
        }
    }
}
