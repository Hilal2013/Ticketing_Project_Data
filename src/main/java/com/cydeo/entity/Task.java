package com.cydeo.entity;

import com.cydeo.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tasks")
@Where(clause = "is_deleted=false")
//we dont wanna do hard deleted from database//I wanna soft deleted in the database by changing field parameter
//we wont write one by one
//whatever repository is using task entity automatically will conconate with this statement
public class Task extends BaseEntity {

    private String taskSubject;
    private String taskDetail;
    @Enumerated(EnumType.STRING)
    private Status taskStatus;
    @Column(columnDefinition = "DATE")
    private LocalDate assignedDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_employee_id")
    private User assignedEmployee;//Every task assigned an employee//one user/one employee can have many tasks

    @ManyToOne(fetch = FetchType.LAZY)
//@JoinColumn(name = "project_id")
    private Project project; //task has project//one project has many tasks

}
