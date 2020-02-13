package com.codesdream.ase.model.permission;

import jdk.jfr.DataAmount;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import sun.security.x509.DNSName;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "activity_container")
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //活动标题
    @Column(nullable = false)
    private String title;

    //创建人
    @Column(nullable = false)
    private String creator;

    //活动类型1qa
    @Column(nullable = false)
    private String type;

    //活动描述
    @Column(nullable = true)
    private String description;

    //自愿参与人列表
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "act_volunteer")
    private Set<User> volunteers;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "act_participate", joinColumns = {
            @JoinColumn(name = "act_id")
    })
    private Set<User> participateGroup;

    private Set<User> participatedGroup;
    private Set<User> signGroup;
    private Set<User> signedGroup;
    private Set<User> visibleGroup;
    private Set<User> informGroup;
    private Set<User> informedGroup;
    private Period planPeriod;
    private Period realPe
    /*
participated_group	list<long>
sign_group	list<long>
signed_group	list<long>
visible_group	list<long>
inform_group	list<long>
informed_group	list<long>
plan_t_id	long
real_t_id	long
remind_time	string
enclosure_id	list<long>
chief_manager	long
assist_manager	list<long>
is_on	bool
is_finished	bool
c_id	long

    */

}