package com.teamtrack.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.teamtrack.entity.Group;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GroupRepository extends MongoRepository<Group, ObjectId> {

    List<Group> findByGroupMembersUserName(String userName);
}
