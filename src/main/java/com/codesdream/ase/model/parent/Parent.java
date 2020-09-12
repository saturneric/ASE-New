package com.codesdream.ase.model.parent;

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
@EqualsAndHashCode(callSuper = false)
public class Parent extends User {

    private int studentId;
}
