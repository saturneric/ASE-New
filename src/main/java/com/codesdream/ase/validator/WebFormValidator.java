package com.codesdream.ase.validator;

import org.springframework.stereotype.Component;

import java.util.*;

//用于检查网页表单格式是否合法
@Component
public class WebFormValidator {

    public boolean check(Collection<String> stdForm, Map<String, String[]> webFormMap){

        Collection<String> webForm = webFormMap.keySet();
        if(stdForm.containsAll(webForm)){
            return true;
        }
        return false;
    }
}
