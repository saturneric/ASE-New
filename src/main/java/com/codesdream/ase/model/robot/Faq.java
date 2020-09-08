package com.codesdream.ase.model.robot;

import com.codesdream.ase.model.mark.Tag;
import com.codesdream.ase.model.permission.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class Faq {
    String answer;
    String question;
    List<String> tag;

}
