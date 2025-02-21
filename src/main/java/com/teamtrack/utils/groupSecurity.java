package com.teamtrack.utils;

import com.teamtrack.entity.Group;
import com.teamtrack.entity.Invitation;
import com.teamtrack.entity.Task;
import com.teamtrack.exception.NotFoundException;
import com.teamtrack.repository.GroupRepository;
import com.teamtrack.repository.InvitationRepository;
import com.teamtrack.repository.TaskRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Component("groupSecurity")
public class groupSecurity {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    //check if user is admin of the group
    public boolean isGroupAdmin(ObjectId groupId, String userName){
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group with ID " + groupId + " not found"));
        return group.getGroupAdmins().contains(userName);
    }

    //check if user is member of the group
    public boolean isGroupMember(ObjectId groupId, String userName){
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group with ID  " + groupId + " not found"));
        return group.getGroupMembers().stream().anyMatch(member -> member.getUserName().equals(userName));
    }

    //check if user is assigned to task
    public boolean isAssignedUser(ObjectId taskId, String userName){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task with ID  " + taskId + " not found"));
        return  task.getAssignedToUserName().equals(userName);
    }

    //check if the user is invited
    public boolean isInvitedUser(ObjectId invitationId, String userName) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NotFoundException("Invitation not found"));
        return invitation.getInvitedUserName().equals(userName);
    }
}
