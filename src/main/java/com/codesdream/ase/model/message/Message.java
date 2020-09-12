package com.codesdream.ase.model.message;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
@Data
public class Message {
    @Id
    int id;

    String title;

    String text;

    Date creationDate = new Date();;

    // 重要性  值为0-1
    int type;

}