package com.teamtrack.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.teamtrack.entity.Group;

public interface GroupRepository extends MongoRepository<Group, ObjectId> {

}
