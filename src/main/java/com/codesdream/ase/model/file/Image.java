package com.codesdream.ase.model.file;

import javax.persistence.*;

@Entity
@Table
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    byte[] data;

    String description;
}
