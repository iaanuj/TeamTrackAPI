package com.teamtrack.controller;

import com.teamtrack.dto.ApiResponse;
import com.teamtrack.dto.ConfirmationRequest;
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
@CrossOrigin(origins = {"${FRONTEND_URL}", "${DEVELOPER_URL}"})
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
            return new ResponseEntity<>(new ApiResponse("User registered successfully. Please check your email for the confirmation code.",true),HttpStatus.CREATED);
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

            User currentUser = userService.findByUserName(user.getUserName());
            if(!currentUser.isActive()){
                return new ResponseEntity<>(new ApiResponse("Account is not verified.",false),HttpStatus.UNAUTHORIZED);
            }

            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(new ApiResponse(jwt,true), HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>(new ApiResponse("wrong username or password",false),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verify-confirmation")
    public ResponseEntity<ApiResponse> verifyConfirmationCode(@RequestBody ConfirmationRequest confirmationRequest){
        try{
            User user = userService.findByUserMail(confirmationRequest.getEmail());

            if(user == null){
                return new ResponseEntity<>(new ApiResponse("User not found.",false),HttpStatus.NOT_FOUND);
            }

            if(user.getConfirmationCode().equals(confirmationRequest.getConfirmationCode())){
                userService.activateUser(user);
                return new ResponseEntity<>(new ApiResponse("User verified successfully.",true),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse("Incorrect confirmation code",false),HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("Exception during confirmation verification", e);
            return new ResponseEntity<>(new ApiResponse("An error occurred during verification.",false),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/resend-confirmation")
//    public ResponseEntity<ApiResponse> resendConfirmationCode(@RequestBody ConfirmationRequest confirmationRequest){
//
//    }
}
