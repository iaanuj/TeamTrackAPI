package com.teamtrack.controller;

import com.teamtrack.dto.InvitationRequest;
import com.teamtrack.entity.Invitation;
import com.teamtrack.service.InvitationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invitation")
public class InvitationController {

    @Autowired
    private InvitationService invitationService;


    @PostMapping("/send")
    public ResponseEntity<Invitation> sendInvitation(@RequestBody InvitationRequest invitationRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String inviterUserName = authentication.getName();

        // Send the invitation
        Invitation invitation = invitationService.sendInvitation(invitationRequest.getGroupId(),
                invitationRequest.getInvitedUserName(), inviterUserName);
        return ResponseEntity.status(HttpStatus.CREATED).body(invitation);
    }

    // Accept invitation
    @PutMapping("/{invitationId}/accept")
    @PreAuthorize("@groupSecurity.isInvitedUser(#invitationId, authentication.name)")
    public ResponseEntity<Void> acceptInvitation(@PathVariable ObjectId invitationId) {

        invitationService.acceptInvitation(invitationId);
        return ResponseEntity.noContent().build();
    }


    //decline invitation
    @PutMapping("/{invitationId}/reject")
    @PreAuthorize("@groupSecurity.isInvitedUser(#invitationId, authentication.name)")
    public ResponseEntity<Void> rejectInvitation(@PathVariable ObjectId invitationId){
        invitationService.rejectInvitation(invitationId);
        return ResponseEntity.noContent().build();
    }
}
