package com.codesdream.ase.test;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.ASESpringUtil;
import com.codesdream.ase.configure.ActivityFormConfigure;
import com.codesdream.ase.exception.innerservererror.InvalidFormFormatException;
import com.codesdream.ase.validator.JSONFormValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class JSONFormValidatorTest {
    @Resource
    ASESpringUtil aseSpringUtil;

    @Test
    public void checkTest() throws InvalidFormFormatException {
        Map<String, String> map = new HashMap<String, String>() {{
            put("creator", "tom");
            put("title", "haha");
            put("description", "h");
            put("cycle", "h");
            put("volunteers", "tom");
            put("participate", "tom");
            put("sign", "s");
            put("visible", "s");
            put("start-time", "1");
            put("remind", "1");
            put("enclosure", "1");
            put("chief-manager", "tom");
            put("assist-manager", "1");
            put("attendance", "1");
            put("type", "h");
            put("attendance", "aa");
            put("inform", "aaa");
        }};
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(map);
        Optional<JSONObject> json = Optional.of(jsonObject);
        ActivityFormConfigure activityFormConfigure = aseSpringUtil.getBean(ActivityFormConfigure.class);
        JSONFormValidator jsonFormValidator = aseSpringUtil.getBean(JSONFormValidator.class);
        if (jsonFormValidator.check(activityFormConfigure.getStdActivityForm(), json.get()).isEmpty()) {
            System.out.println("error");
        } else System.out.println("ok");
    }
}
