package com.codesdream.ase.model.activity;

import com.codesdream.ase.model.file.Image;
import com.codesdream.ase.model.permission.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class Report {
    @Id
    int id;

    int activityId;

    String title;

    @ManyToOne(cascade = CascadeType.MERGE)
    User creator;

    @ManyToOne(cascade = CascadeType.MERGE)
    User manager;

    Date realBeginDate;

    Date realEndDate;

    String context;

    @ManyToMany
    List<Image> images;
}
