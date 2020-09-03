package com.codesdream.ase.model.student;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Notification {
    @Id
    int id;

    String context;

    String title;


}
