package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService {//this is service coming DTO and returning DTO
List<TaskDTO> listAllTasks();
    void save(TaskDTO task);//give me TaskDTO i can save it
    void  update(TaskDTO dto);
    void delete(Long id);//we dont have any unique// actually id is for database but here no unique
    // if you have unique field like username use them//actually we should have put task code but now it is ok
    TaskDTO findById(Long id);
int totalNonCompletedTask(String projectCode);
int totalCompletedTask(String projectCode);
  void   deleteByProject(ProjectDTO projectDTO);
    void completeByProject(ProjectDTO projectDTO);

    List<TaskDTO> listAllTasksByStatusIsNot(Status status);
    List<TaskDTO> listAllTasksByStatus(Status status);

    List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO assignedEmployee);


}
