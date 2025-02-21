package com.teamtrack.service;

import com.teamtrack.dto.TaskDTO;
import com.teamtrack.entity.Group;
import com.teamtrack.entity.Task;
import java.util.List;
import com.teamtrack.repository.GroupRepository;
import com.teamtrack.repository.TaskRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.teamtrack.enumModel.TaskStatus;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GroupRepository groupRepository;

    //assign task
    public Task assignTask(TaskDTO taskDTO) {
        // fetching group
        Group group = groupRepository.findById(taskDTO.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        // Check if the assignedTo user is a member of the group
        if (group.getGroupMembers().stream().noneMatch(member -> member.getUserName().equals(taskDTO.getAssignedToUserName()))) {
            throw new RuntimeException("User is not a member of the group");
        }

        // Create and save the task
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setGroupId(taskDTO.getGroupId());
        task.setAssignedToUserName(taskDTO.getAssignedToUserName());
        task.setDeadline(taskDTO.getDeadline());
        task.setTaskStatus(TaskStatus.ASSIGNED);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    // Accept task
    public Task acceptTask(ObjectId taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));

        // Check if the task is in ASSIGNED state
        if (task.getTaskStatus()!=TaskStatus.ASSIGNED) {
            throw new IllegalStateException("Task cannot be accepted in its current state");
        }
        // Update the task status
        task.setTaskStatus(TaskStatus.ACCEPTED);
        return taskRepository.save(task);
    }

    //Reject Task
    public Task rejectTask(ObjectId taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new NoSuchElementException(("Task not found")));
        //check if task is in ASSIGNED state
        if(task.getTaskStatus()!=TaskStatus.ASSIGNED){
            throw new IllegalStateException("Task cannot be rejected in its current state");
        }
        task.setTaskStatus(TaskStatus.DECLINED);
        return taskRepository.save(task);
    }

    //Start Task
    public Task startTask(ObjectId taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new NoSuchElementException(("Task not found")));
        //check if task is in ACCEPTED state
        if(task.getTaskStatus()!=TaskStatus.ACCEPTED){
            throw new IllegalStateException("Task cannot be started in its current state");
        }
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        return taskRepository.save(task);
    }

    //Complete Task
    public Task completeTask(ObjectId taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new NoSuchElementException(("Task not found")));
        //check if task is IN PROGRESS state
        if(task.getTaskStatus()!=TaskStatus.IN_PROGRESS){
            throw new IllegalStateException("Task cannot be completed in its current state");
        }
        task.setTaskStatus(TaskStatus.COMPLETED);
        return taskRepository.save(task);
    }


    // Get all tasks for a group (transparent within the group)
    public List<Task> getTasksByGroup(ObjectId groupId) {
        return taskRepository.findByGroupId(groupId);
    }
}
