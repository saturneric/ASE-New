package com.codesdream.ase.model.message;

import com.codesdream.ase.model.file.File;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class Notification {
    @Id
    int id;

    String context;

    String title;

    Date creationDate = new Date();

    Date announcementDate;

    @ManyToMany
    List<File> files;

}
