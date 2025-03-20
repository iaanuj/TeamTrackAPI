package com.teamtrack.service;

import com.teamtrack.entity.Group;
import com.teamtrack.entity.Invitation;
import com.teamtrack.entity.Member;
import com.teamtrack.enumModel.InvitationStatus;
import com.teamtrack.exception.DuplicateRequestException;
import com.teamtrack.exception.NotFoundException;
import com.teamtrack.exception.UnauthorizedException;
import com.teamtrack.repository.GroupRepository;
import com.teamtrack.repository.InvitationRepository;
import com.teamtrack.repository.UserRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class InvitationService {
    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    // Send an invitation
    @PreAuthorize("@groupSecurity.isGroupAdmin(#groupId, authentication.name)")
    public Invitation sendInvitation(ObjectId groupId, String invitedUserName, String inviterUserName){

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found"));

        //check if member already
        if(group.getGroupMembers().stream().anyMatch(member -> member.getUserName().equals(invitedUserName))){
            throw new IllegalStateException("User already a member of the group");
        }

        //check if invitation already sent
        if(invitationRepository.findByGroupIdAndInvitedUserName(groupId, invitedUserName).isPresent()){
            throw new DuplicateRequestException("Invitation already sent");
        }

        Invitation invitation = new Invitation();
        invitation.setGroupId(groupId);
        invitation.setInviterUserName(inviterUserName);
        invitation.setInvitedUserName(invitedUserName);
        invitation.setStatus(InvitationStatus.PENDING);
        invitation.setCreatedAt(LocalDateTime.now());
        invitationRepository.save(invitation);

        notificationService.sendInvitationNotification(invitedUserName, "You have a new group notification from " + inviterUserName);

        return invitation;
    }

    //accept
    public void acceptInvitation(ObjectId invitationId){
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NotFoundException("Invitation not found"));

        //check if already accepted
        if(invitation.getStatus()!=InvitationStatus.PENDING){
            throw new IllegalStateException("Invitation already been processed");
        }

        // Add  user to the group
        Group group = groupRepository.findById(invitation.getGroupId())
                .orElseThrow(() -> new NotFoundException("Group not found"));
        Member newMember = new Member();
        newMember.setUserName(invitation.getInvitedUserName());
        newMember.setGroupRole("MEMBER");
        group.getGroupMembers().add(newMember);
        groupRepository.save(group);

        // Update the invitation status
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitationRepository.save(invitation);
    }

    // reject
    public void rejectInvitation(ObjectId invitationId){
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NotFoundException("Invitation not found"));

        //check if invitation is still pending(not decline nor accepted)
        if(invitation.getStatus()==InvitationStatus.ACCEPTED){
            throw new IllegalStateException("Invitation is no longer pending");
        }

        //decline invitation
        invitation.setStatus(InvitationStatus.REJECTED);
        invitationRepository.save(invitation);
    }
}
