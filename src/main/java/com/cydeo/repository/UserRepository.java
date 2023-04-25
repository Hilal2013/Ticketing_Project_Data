package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findAllByIsDeletedOrderByFirstNameDesc(Boolean deleted);
    //get user based on username
    //derived query
   // User findByUserName(String username);
    User findByUserNameAndIsDeleted(String username, Boolean deleted);
    @Transactional
    void deleteByUserName(String username);
    //maybe you deleted in the UI but not database/use transactional
    //you can put on class level
    //but dont forget not delete from database delete from UI

  //  List<User> findByRoleDescriptionIgnoreCase(String description);
  List<User> findByRoleDescriptionIgnoreCaseAndIsDeleted(String description, Boolean deleted);
}
