package com.teamtrack.repository;

import com.teamtrack.entity.Invitation;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InvitationRepository extends MongoRepository<Invitation, ObjectId> {
    Optional<Invitation> findByGroupIdAndInvitedUserName(ObjectId groupId, String invitedUserName);
}
