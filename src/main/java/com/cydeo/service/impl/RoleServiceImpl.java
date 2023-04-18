package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;


    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
//controler call me and requesting all RolDTO// so it can show in the UI
//I need to make a call to db and get all the roles from table(business for database)
        //go to repository and find a service (method) with gives the roles  from db
        //how i will call any service here
//we are gonna call method from another class so injection
List<Role> roleList=roleRepository.findAll();//it comes from Jpa//implementation sql=>derived query
// your method is looking for DTO //how we are gonna convert we have mappers
//im trying bunch of Role and bunch of roledto=>stream
//I need to convert each entity each object one by one =>stream
     return   roleList.stream()
                      //  .map(entity -> roleMapper.convertToDto(entity))
                .map(roleMapper::convertToDto)
             .collect(Collectors.toList());

    }

    @Override
    public RoleDTO findById(Long id) {

        return roleMapper.convertToDto( roleRepository.findById(id).get());
    }
}
