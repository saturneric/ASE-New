package com.codesdream.ase.model.mark;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Tag {
    @Id
    int id;

    String tagName;
}
