package com.codesdream.ase.model.student;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class StudentCourse {
    @Id
    int id;

    @OneToOne(cascade = CascadeType.MERGE)
    Student student;

    @OneToOne(cascade = CascadeType.MERGE)
    Course course;

    @Column(nullable = false)
    float score;

    boolean isFailed;

    int term;

    Date finishedDate;

}
