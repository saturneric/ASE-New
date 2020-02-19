package com.codesdream.ase.validator;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WebFormValidator {

    public HashMap<String, Boolean> check(Map<String, Set<String>> stdFormMap, Map<String, String[]> webFormMap){

        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        Set<String> stdKey = stdFormMap.keySet();
        Set<String> webKey = webFormMap.keySet();

        for(String key : webKey){
            if(stdKey.contains(key)){
                Set<String> stdSet = stdFormMap.get(key);
                String[] webForm = webFormMap.get(key);
                List<String> webList = new ArrayList<>();
                Collections.addAll(webList, webForm);
                if(stdSet.containsAll(webList)){
                    result.put(key,true);
                }
                else result.put(key,false);
            }
        }

        return result;
    }
}
