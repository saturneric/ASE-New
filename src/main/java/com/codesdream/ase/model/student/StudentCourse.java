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

    String studentId;

    String courseId;

    @Column(nullable = false)
    float score;

    @Column(nullable = false)
    boolean isFailed;

    Date finishedDate;

}
