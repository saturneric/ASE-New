package com.codesdream.ase.model.mark;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Tag {
    @Id
    int id;

    String tagName;
}
