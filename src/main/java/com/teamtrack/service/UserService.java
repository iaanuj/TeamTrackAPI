package com.teamtrack.service;

import com.teamtrack.entity.Group;
import com.teamtrack.entity.User;
import com.teamtrack.exception.EmailSendException;
import com.teamtrack.exception.UsermailAlreadyExistsException;
import com.teamtrack.exception.UsernameAlreadyExistsException;
import com.teamtrack.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User user){
        userRepository.save(user);
    }

        public void registerUser(User user) throws UsermailAlreadyExistsException, UsernameAlreadyExistsException, EmailSendException {
            Optional<User> existingUsermail = Optional.ofNullable(userRepository.findByUserMail(user.getUserMail()));
            if(existingUsermail.isPresent()){
                throw new UsermailAlreadyExistsException("User mail already exist");
            }
            Optional<User> existingUsername = Optional.ofNullable(userRepository.findByUserName(user.getUserName()));

            //delete if existing username is not verified
            if(existingUsername.isPresent()){
                if(!existingUsername.get().isActive()){
                    userRepository.delete(existingUsername.get());
                }else {
                    throw new UsernameAlreadyExistsException("User name already exist");
                }
            }
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            String plainOtp = generateConfirmationCode();
            user.setUserOtp(passwordEncoder.encode(plainOtp));

            user.setActive(false);
            user.setRegistrationDate(LocalDateTime.now());
            userRepository.save(user);
            emailService.sendConfirmationMail(user.getUserMail(), plainOtp);
        }

    public String generateConfirmationCode(){
        Random random = new Random();
        return String.format("%04d",random.nextInt(10000));
    }

    public void activateUser(User user){
        user.setActive(true);
        user.setUserOtp(null);
        userRepository.save(user);
    }

    public void deleteUnverifiedUsers(){
        LocalDateTime threshold = LocalDateTime.now().minusDays(1);
        userRepository.deleteByRegistrationDateBeforeAndActiveFalse(threshold);
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
