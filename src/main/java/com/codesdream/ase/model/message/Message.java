package com.codesdream.ase.model.message;

import com.codesdream.ase.model.permission.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table
@Data
public class Message {
    @Id
    int id;

    String title;

    String text;

    boolean isRead = false;

    // 重要性  值为0-1
    int type;

    @OneToMany
    List<User> Receiver;

}