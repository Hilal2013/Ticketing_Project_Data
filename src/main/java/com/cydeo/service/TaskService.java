package com.cydeo.service;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;

import java.util.List;

public interface TaskService {//this is service coming DTO and returning DTO
List<TaskDTO> listAllTasks();
    void save(TaskDTO task);//give me TaskDTO i can save it
    void  update(TaskDTO task);
    void delete(Long id);//we dont have any unique// actually id is for database but here no unique
    // if you have unique field like username use them//actually we should have put task code but now it is ok
    TaskDTO findById(Long id);
}
