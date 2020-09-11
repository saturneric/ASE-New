package com.codesdream.ase.component.student;

import lombok.Data;

import java.util.Date;

@Data
public class SubjectScore {

    private int studentId;

    private String subject;

    private float credit;

    private float score;

    private Date finishedDate;

}
