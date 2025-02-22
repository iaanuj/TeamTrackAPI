package com.teamtrack.service;


import ch.qos.logback.core.LayoutBase;
import com.teamtrack.dto.GroupDTO;
import com.teamtrack.entity.Group;
import com.teamtrack.entity.Member;
import com.teamtrack.entity.User;
import com.teamtrack.repository.InvitationRepository;
import com.teamtrack.repository.TaskRepository;
import com.teamtrack.repository.UserRepository;
import com.teamtrack.repository.GroupRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    public void createGroup(GroupDTO groupDTO, String creatorUserName){
        Group group = new Group();
        group.setGroupName(groupDTO.getGroupName());
        group.setGroupDescription(groupDTO.getGroupDescription());

        //adding admin
        group.setGroupAdmins(Collections.singletonList(creatorUserName));

        //adding member admin
        Member member = new Member();
        member.setUserName(creatorUserName);
        member.setGroupRole("ADMIN");
        group.setGroupMembers(Collections.singletonList(member));

        groupRepository.save(group);
    }

    public void deleteGroup(ObjectId groupId) {
        // Fetch the group
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found"));

        // Delete all tasks associated with the group
        taskRepository.deleteByGroupId(groupId);

        // Delete all invitations associated with the group
        invitationRepository.deleteByGroupId(groupId);

        // Delete the group
        groupRepository.delete(group);
    }
}
