package com.codesdream.ase.model.student;

import com.codesdream.ase.model.file.File;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table
@Data
public class Notification {
    @Id
    int id;

    String context;

    String title;

    @ManyToMany
    List<File> files;

}
