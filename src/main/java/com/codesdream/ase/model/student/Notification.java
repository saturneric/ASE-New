package com.codesdream.ase.model.student;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Notification {
    @Id
    int id;

    String context;

    String title;


}
