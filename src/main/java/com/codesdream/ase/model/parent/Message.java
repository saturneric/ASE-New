package com.codesdream.ase.model.parent;

import com.codesdream.ase.model.file.File;
import com.codesdream.ase.model.mark.Tag;
import com.codesdream.ase.model.permission.User;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class Message {
    @Id
    int id;

    String title;
    String text;
    Boolean have_read;

    // 重要性  值为0-1
    int type;

    @OneToMany
    User Receiver;

}