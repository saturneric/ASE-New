package com.codesdream.ase.model.activity;

import com.codesdream.ase.model.file.File;
import com.codesdream.ase.model.mark.Tag;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
@Data
public class Activity {
    @Id
    int id;

    String title;

    int creatorId;

    @ElementCollection
    List<Integer> managerIds = new ArrayList<>();

    @ElementCollection
    List<Integer> participantIds = new ArrayList<>();

    @ElementCollection
    List<Integer> absentees = new ArrayList<>();

    // 活动出勤情况，对应人员是否出勤
    @ElementCollection
    Map<Integer, Boolean> attendance = new HashMap<>();

    @OneToMany(cascade = CascadeType.MERGE)
    List<File> appendixes = new ArrayList<>();


    Date registrationDDL;

    Date creationTime = new Date();

    Date realBeginDate;

    Date realEndDate;

    @ManyToMany(cascade = CascadeType.MERGE)
    List<Tag> tags = new ArrayList<>();

    @OneToOne(cascade = CascadeType.MERGE)
    Report report;
}
