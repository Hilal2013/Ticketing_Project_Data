package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
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
  projectRepository.save(project);
    }

    @Override
    public void complete(String projectCode) {
//get the project based on code from db//set status complete//save again
        Project project=projectRepository.findByProjectCode(projectCode);
    project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

    }

    @Override
    public void update(ProjectDTO dto) {

        Project project = projectRepository.findByProjectCode(dto.getProjectCode());

        Project convertedProject = projectMapper.convertToEntity(dto);

        convertedProject.setId(project.getId());

        convertedProject.setProjectStatus(project.getProjectStatus());

        projectRepository.save(convertedProject);


    }


}
