package com.codesdream.ase.model.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "appendixFile")
public class AppendixFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "type")
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "last_edit_time")//, nullable = false)
    private LocalDateTime lastEditTime = LocalDateTime.of(2020,2,18,16,36);
}
