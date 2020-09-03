package com.codesdream.ase.model.student;

import com.codesdream.ase.model.file.Image;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class Honor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    // 荣誉描述
    String description;

    // 证明材料
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    List<Image> images;

    // 创建时间
    Date creationDate;

    // 上一次修改时间
    Date lastModification;
}
