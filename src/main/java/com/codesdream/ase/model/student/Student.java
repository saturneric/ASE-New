package com.codesdream.ase.model.student;

import com.codesdream.ase.model.mark.Tag;
import com.codesdream.ase.model.permission.User;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Student extends User {
    @Column(nullable = false)
    String studentId;

    @Column(nullable = false)
    String name;

    String profilePicture;


}
