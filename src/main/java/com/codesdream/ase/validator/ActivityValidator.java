package com.codesdream.ase.validator;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.model.activity.Activity;
import org.springframework.stereotype.Component;

import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//检查当前活动各属性值是否合法（存在）
@Component
public class ActivityValidator {

    public String[] check(Optional<JSONObject> json) {
        return null;
    }
}
