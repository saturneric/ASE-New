package com.codesdream.ase.model.student;

import com.codesdream.ase.model.mark.Tag;
import com.codesdream.ase.model.permission.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Student extends User {
    @Column(nullable = false)
    String studentId;

    @Column(nullable = false)
    String name;

    String profilePicture;

}
