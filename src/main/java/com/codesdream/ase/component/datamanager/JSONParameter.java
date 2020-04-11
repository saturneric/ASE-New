package com.codesdream.ase.component.datamanager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.json.respond.JSONBaseRespondObject;
import com.codesdream.ase.exception.innerservererror.HandlingErrorsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 处理JSON相关数据
 */
@Component
public class JSONParameter {

    // 处理Request Body
    public  String getRequestBody(HttpServletRequest request){
        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = request.getReader();
            reader.reset();
            String line;
            while ((line = reader.readLine()) != null)
                stringBuilder.append(line);
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 提取Request中的JSON数据
    public Optional<JSONObject> getJSONByRequest(HttpServletRequest request){
        try {
            JSONObject jsonParam = null;
            String content = getRequestBody(request);
            jsonParam = JSONObject.parseObject(content);
            return Optional.ofNullable(jsonParam);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

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
    public <T> Optional<T> getJavaObjectByRequest(HttpServletRequest request, Class<T> type){
        Optional<JSONObject> json = getJSONByRequest(request);
        return json.map(jsonObject -> getJavaObject(jsonObject, type));
    }

    public <T> T parsePathToObject(JsonPatch patch, T object){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode patched = patch.apply(mapper.convertValue(object, JsonNode.class));
            return (T) mapper.treeToValue(patched, object.getClass());
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new HandlingErrorsException(e.getMessage());
        }

    }

}
