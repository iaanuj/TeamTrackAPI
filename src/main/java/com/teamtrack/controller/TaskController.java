package com.teamtrack.controller;

import com.teamtrack.dto.TaskDTO;
import com.teamtrack.entity.Task;
import com.teamtrack.service.TaskService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Assign a task to a group member (only admins can do this)
    @PreAuthorize("@groupSecurity.isGroupAdmin(#taskDTO.groupId, authentication.name)")
    @PostMapping("/assign")
    public ResponseEntity<Task> assignTask(@RequestBody TaskDTO taskDTO) {
        // Assign the task
        Task task = taskService.assignTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    // accept task
    @PreAuthorize("@groupSecurity.isAssignedUser(#taskId, authentication.name)")
    @PutMapping("/{taskId}/accept")
    public ResponseEntity<Task> acceptTask(@PathVariable ObjectId taskId) {
        // Update the task status
        Task task = taskService.acceptTask(taskId);
        return ResponseEntity.ok(task);
    }

    //reject task
    @PreAuthorize("@groupSecurity.isAssignedUser(#taskId, authentication.name)")
    @PutMapping("/{taskId}/reject")
    public ResponseEntity<Task> rejectTask(@PathVariable ObjectId taskId) {
        // Update the task status
        Task task = taskService.rejectTask(taskId);
        return ResponseEntity.ok(task);
    }

    //start task
    @PreAuthorize("@groupSecurity.isAssignedUser(#taskId, authentication.name)")
    @PutMapping("/{taskId}/start")
    public ResponseEntity<Task> startTask(@PathVariable ObjectId taskId) {
        // Update the task status
        Task task = taskService.startTask(taskId);
        return ResponseEntity.ok(task);
    }

    //complete task
    @PreAuthorize("@groupSecurity.isAssignedUser(#taskId, authentication.name)")
    @PutMapping("/{taskId}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable ObjectId taskId) {
        // Update the task status
        Task task = taskService.completeTask(taskId);
        return ResponseEntity.ok(task);
    }

    // Get all tasks for a group
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Task>> getTasksByGroup(@PathVariable ObjectId groupId) {
        List<Task> tasks = taskService.getTasksByGroup(groupId);
        return ResponseEntity.ok(tasks);
    }
}
