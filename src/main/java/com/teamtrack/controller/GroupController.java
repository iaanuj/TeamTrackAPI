package com.teamtrack.controller;

import com.teamtrack.dto.ApiResponse;
import com.teamtrack.dto.GroupDTO;
import com.teamtrack.entity.Group;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.teamtrack.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createGroup(@RequestBody GroupDTO groupDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String creatorUserName = authentication.getName();
        groupService.createGroup(groupDTO, creatorUserName);

        return new ResponseEntity<>(new ApiResponse("group created successfully",true), HttpStatus.CREATED);
    }

    @DeleteMapping("/{groupId}/delete")
    @PreAuthorize("@groupSecurity.isGroupAdmin(#groupId, authentication.principal.username)")
    public ResponseEntity<Void> deleteGroup(@PathVariable ObjectId groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{groupId}/make-admin")
    @PreAuthorize("@groupSecurity.isGroupAdmin(#groupId, authentication.principal.username)")
    public ResponseEntity<Void> makeAdmin(@PathVariable ObjectId groupId, @RequestParam String userName){
         groupService.makeAdmin(groupId, userName);
         return ResponseEntity.ok().build();
    }

    @GetMapping("/all-groups")
    public List<Group> getAllGroups(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return groupService.getGroupsForUser(userName);
    }
}
