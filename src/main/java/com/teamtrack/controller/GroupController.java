package com.teamtrack.controller;

import com.teamtrack.dto.ApiResponse;
import com.teamtrack.dto.GroupDTO;
import com.teamtrack.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teamtrack.service.GroupService;

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
}
