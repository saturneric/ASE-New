package com.codesdream.ase.validator;

import org.springframework.stereotype.Component;

import java.util.*;

//用于检查网页表单格式是否合法
@Component
public class WebFormValidator {

    /**
     *  传入标准表单所具有的属性set以及网页表单的属性set，确保map中的key和value一一对应
     * 检查的时候会将标准表单和网页表单两个map中key相同的value进行完全匹配比较
     *  返回一个map，不同表单有不同的合法情况
     *  支持一次处理多个表单，请务必保持对应表单的标准和网页map中的key一样
     * @return 封装在一个map里的不同表单对应的合法情况
    */
    public HashMap<String, Boolean> check(Map<String, HashSet<String>> stdFormMap, Map<String, String[]> webFormMap){

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
