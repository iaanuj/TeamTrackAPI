package com.teamtrack.repository;

import com.teamtrack.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String user);
    User findByUserMail(String mail);
    List<User> findByRegistrationDateBeforeAndActiveFalse(LocalDateTime threshold);
    void deleteByRegistrationDateBeforeAndActiveFalse(LocalDateTime threshold);
}
