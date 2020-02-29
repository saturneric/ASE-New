package com.codesdream.ase.component.datamanager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 处理JSON相关数据
 */
@Component
public class JSONParameter {

    // 提取Request中的JSON数据
    public JSONObject getJSONByRequest(HttpServletRequest request){
        JSONObject jsonParam = null;
        try {
            // 获取输入流
            BufferedReader streamReader =
                    new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));

            // 写入数据到 String Builder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonParam = JSONObject.parseObject(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonParam;
    }

    // 根据JSON对象构造JSON字符串用于返回
    public String getJSONString(JSONObject json){
        return json.toJSONString();
    }

    // 根据Java对象构造JSON字符串用于返回
    public String getJSONString(Object object){
        return JSON.toJSONString(object);
    }

    // 由JSON对象获得对应的Java对象
    public <T> T getJavaObject(JSONObject json, Class<T> type){
        return json.toJavaObject(type);
    }

    // 由Request获得对应的Java对象(常用于Post请求中)
    public <T> T getJavaObjectByRequest(HttpServletRequest request, Class<T> type){
        return getJavaObject(getJSONByRequest(request), type);
    }

}
