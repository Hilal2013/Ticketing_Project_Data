package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isDeleted=false;
    @Column(nullable = false,updatable = false)
    private LocalDateTime insertDateTime;
    @Column(nullable = false,updatable = false)
    private Long insertUserId;
    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    private Long lastUpdateUserId;


    //this method needs to be executed whenever we create the object=>a new user/project/manager
    @PrePersist//trying save//
    private void onPrePersist(){
        //I created  a user is gonna current time
        this.insertDateTime = LocalDateTime.now();
        //in the beginning is gonna be current time too
        this.lastUpdateDateTime=LocalDateTime.now();
        this.insertUserId=1L;
        //security is not connected yet//this is now hard code
        //when we start the security this will be connected with security
        this.lastUpdateUserId=1L;
    }
    //this method executed whenever we update the object
    @PreUpdate
    private void onPreUpdate(){
        //is gonna be update
        this.lastUpdateDateTime=LocalDateTime.now();
        //whoever is updating this user//is gonna take that primary key
        this.lastUpdateUserId=1L;
    }

}
