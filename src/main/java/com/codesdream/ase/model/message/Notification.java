package com.codesdream.ase.model.message;

import com.codesdream.ase.model.file.File;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Notification extends Message{

    Date announcementDate;

    @ManyToMany
    List<File> files;

}
