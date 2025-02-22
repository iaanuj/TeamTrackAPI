package com.teamtrack.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.teamtrack.entity.Task;

import java.util.List;


public interface TaskRepository extends MongoRepository<Task, ObjectId> {
    List<Task> findByGroupId(ObjectId groupId);
    void deleteByGroupId(ObjectId groupId);
}
