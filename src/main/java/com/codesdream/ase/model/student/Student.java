package com.codesdream.ase.model.student;

import com.codesdream.ase.model.permission.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Student extends User {
    int parentId;

    String profilePicture;

    Boolean isShowGrade = true;

    // 请务必确保此属性的初始化 @Todo
    @ElementCollection
    Map<String, Boolean> privacy = new HashMap<String, Boolean>(){{
        put("score", true);
        put("attendance", true);
        put("step", true);
        put("honor", true);
    }};

}
