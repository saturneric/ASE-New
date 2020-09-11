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

    int studentId;

    int courseId;

    @Column(nullable = false)
    float score;

    @Column(nullable = false)
    boolean isFailed;

    int term;

    Date finishedDate;

}
