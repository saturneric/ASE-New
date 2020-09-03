package com.codesdream.ase.model.activity;

import com.codesdream.ase.model.file.File;
import com.codesdream.ase.model.mark.Tag;
import com.codesdream.ase.model.permission.User;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class Activity {
    @Id
    int id;

    String title;

    @ManyToOne
    User Creator;

    @ManyToOne
    User Manager;

    @ElementCollection
    List<Integer> participantIds = new ArrayList<>();

    @OneToMany(cascade = CascadeType.MERGE)
    List<File> appendixes = new ArrayList<>();

    Date registrationDDL;

    Date creationTime = new Date();

    Date realBeginDate;

    Date realEndDate;

    @ManyToMany(cascade = CascadeType.MERGE)
    List<Tag> tags = new ArrayList<>();
}
