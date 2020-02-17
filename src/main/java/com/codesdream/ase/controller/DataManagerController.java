package com.codesdream.ase.controller;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Entity;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/database")
public class DataManagerController {

    @RequestMapping(value = "{subSystem}/{dataModel}/query")
    private String queryView(Model model, @PathVariable String dataModel, @PathVariable String subSystem)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String dataModelFullName = "com.codesdream.ase.model." + subSystem + "." + dataModel;
        String repositoryFullName = "com.codesdream.ase.repository." + subSystem + "." + dataModel + "Repository";
        try {
            Class<?> entityModelClass = Class.forName(dataModelFullName);
            Collection<String> paramArgs = new ArrayList<>();
            for(Field field :entityModelClass.getDeclaredFields()){
                paramArgs.add(field.getName());
            }
            model.addAttribute("paramArgs", paramArgs);
            Object entityModel = entityModelClass.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw e;
        }
        return null;
    }
}
