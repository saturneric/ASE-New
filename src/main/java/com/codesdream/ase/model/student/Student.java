package com.codesdream.ase.model.student;

import com.codesdream.ase.model.mark.Tag;
import com.codesdream.ase.model.permission.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Student extends User {

    String profilePicture;

    Boolean isShowGrade = true;

    @ElementCollection
    Map<String, Boolean> privacy = new HashMap<String, Boolean>(){{
        put("score", true);
        put("attendance", true);
        put("step", true);
        put("honor", true);
    }};

}
