package com.codesdream.ase.configure;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class ActivityFormConfigure {

    private final HashSet<String> stdActivityForm = new HashSet<String>(){{
       add("title");
       add("creator");
       add("type");
       add("description");
       add("cycle");
       add("volunteers");
       add("participate");
       add("sign");
       add("visible");
       add("inform");
       add("start-time");
       add("remind");
       add("enclosure");
       add("chief-manager");
       add("assist-manager");
       add("attendance");
    }};


    public HashSet<String> getStdActivityForm() {
        return stdActivityForm;
    }
}
