package com.codesdream.ase.controller;

import com.codesdream.ase.component.ASESpringUtil;
import com.codesdream.ase.component.datamanager.DataModelRepositorySearcher;
import com.codesdream.ase.component.datamanager.DataModelSearcher;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Entity;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

@Controller
@RequestMapping(value = "/database")
public class DataManagerController {

    @Resource
    ASESpringUtil springUtil;

    @RequestMapping(value = "{subSystem}/{dataModel}/query")
    private String queryView(Model model, @PathVariable String dataModel, @PathVariable String subSystem) {
        DataModelRepositorySearcher dataModelRepositorySearcher = springUtil.getBean(DataModelRepositorySearcher.class);
        DataModelSearcher dataModelSearcher = springUtil.getBean(DataModelSearcher.class);

        dataModelSearcher.getDataModelClass(subSystem, dataModel);
        if(!dataModelSearcher.isPresent()){
            throw new RuntimeException("Data Model Not Found");
        }
        dataModelRepositorySearcher.getDataModelRepositoryClass(subSystem, dataModel);
        if(!dataModelRepositorySearcher.isPresent()){
            throw new RuntimeException("Data Model Repository Not Found");
        }

        Map<String, Object> data;
        return "query";
    }
}
