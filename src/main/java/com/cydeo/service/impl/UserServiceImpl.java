package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {

List<User> userList=userRepository.findAll(Sort.by("firstName"));
 return userList.stream()
         .map(entity->userMapper.convertToDto(entity))
         .collect(Collectors.toList());

    }

    @Override
    public UserDTO findByUserName(String username) {


        return userMapper.convertToDto(userRepository.findByUserName(username));
    }

    @Override
    public void save(UserDTO user) {//user is coming UI

        userRepository.save(userMapper.convertToEntity(user));

    }

    @Override
    public UserDTO update(UserDTO user) {
//convert user dto to entity object//Find current user// set id
  User user1=   userRepository.findByUserName(userMapper.convertToEntity(user).getUserName());
      //set id of entity user to the converted object
User convertedUser=userMapper.convertToEntity(user);
  convertedUser.setId(user1.getId());
userRepository.save(convertedUser);
return findByUserName(user.getUserName());
    }



    @Override
    public void deleteByUserName(String username) {
         userRepository.deleteByUserName(username);

    }
    @Override
    public void delete(String username) {

        //go to db and get that user with username
        //change the isdeleted field to true
        //save the object in database
        User user=userRepository.findByUserName(username);
        user.setIsDeleted(true);
        userRepository.save(user);


    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        //go to db //bring all users with spesific role//if I pass manager  is gonna bring manager users

        return userRepository.findByRoleDescriptionIgnoreCase(role).stream()
                .map(entity->userMapper.convertToDto(entity))
                .collect(Collectors.toList());
    }
}
