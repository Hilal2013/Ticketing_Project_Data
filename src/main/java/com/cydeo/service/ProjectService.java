package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    ProjectDTO getByProjectCode(String code);
    List<ProjectDTO> listAllProjects();//I have a table and I wanna show all projects
void save(ProjectDTO dto);
void update(ProjectDTO dto);
void delete(String projectCode);
void complete(String projectCode);


}
