package com.codesdream.ase.validator;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.*;

//用于检查JSON格式是否合法
@Component
public class JSONFormValidator {

    /**
     * @param stdForm 标准表单格式，根据需求自定义
     * @param json    待检验的json对象
     * @return 空列表或者缺失的表单信息列表
     */
    public List<String> check(Set<String> stdForm, JSONObject json) {

        List<String> res = new ArrayList<>();
        Set<String> jsonForm = json.keySet();
        for (String str : stdForm) {
            if (!jsonForm.contains(str)) {
                res.add(str);
            }
        }
        return res;
    }
}
