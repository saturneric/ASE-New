package com.codesdream.ase.model.file;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
@Data
public class File {
    @Id
    int id;

    String title;

    String url;

    Date creationTime;
}
