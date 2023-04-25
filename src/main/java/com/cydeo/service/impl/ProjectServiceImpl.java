package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        return projectMapper.convertToDto(projectRepository.findByProjectCode(code));

    }

    @Override
    public List<ProjectDTO> listAllProjects() {

        return projectRepository.findAll(Sort.by("projectCode")).stream()
                .map(entity -> projectMapper.convertToDto(entity))
                .collect(Collectors.toList());


    }

    @Override
    public void save(ProjectDTO dto) {
        dto.setProjectStatus(Status.OPEN);
        projectRepository.save(projectMapper.convertToEntity(dto));

    }
    @Override
    public void delete(String projectCode) {

       // projectRepository.deleteByProjectCode(projectCode);//from the UI
        //go to db and get that project with projectcode
        //change the isdeleted field to true
        //save the object in database
  Project project=projectRepository.findByProjectCode(projectCode);
  project.setIsDeleted(true);
        project.setProjectCode(project.getProjectCode() + "-" + project.getId());
        //after deletion change the unique number and save// so i can use previous project code for another project
  projectRepository.save(project);
  //if the project is deleted we should delete task
        taskService.deleteByProject(projectMapper.convertToDto(project));

    }

    @Override
    public void complete(String projectCode) {
//get the project based on code from db//set status complete//save again
        Project project=projectRepository.findByProjectCode(projectCode);
    project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
//for complete task
        taskService.completeByProject(projectMapper.convertToDto(project));

    }
    @Override
    public void update(ProjectDTO dto) {

        Project project = projectRepository.findByProjectCode(dto.getProjectCode());

        Project convertedProject = projectMapper.convertToEntity(dto);

        convertedProject.setId(project.getId());

        convertedProject.setProjectStatus(project.getProjectStatus());

        projectRepository.save(convertedProject);


    }
    @Override
    public List<ProjectDTO> listAllProjectDetails() {
        //we need to get manager and I need to go tadabase bring assigned projects to manager with infos
//now it is coming UI not service//this portion is login who logged in //hard code
        UserDTO currentUserDTO = userService.findByUserName("harold@manager.com");
        // I need to go to database
        User userManager = userMapper.convertToEntity(currentUserDTO);
//hey Db give me all projects assigned to manager login in the system//I need all project belongs to this user
        List<Project> list=projectRepository.findByAssignedManager(userManager); //now I have the projects
//I dont have completed and unfinished task count fields in the table//my dto has two fields
        //get each project convertToEnty
       return list.stream()
               .map(project->{
           ProjectDTO convertedProject=projectMapper.convertToDto(project);
           convertedProject.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
           //give me the project Task service is gonna find not completed tasks
           convertedProject.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));
           return convertedProject;
       }
       ).collect(Collectors.toList());
    }

}
