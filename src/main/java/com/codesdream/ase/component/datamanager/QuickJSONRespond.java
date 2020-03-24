package com.codesdream.ase.component.datamanager;

import com.codesdream.ase.component.json.respond.EmptyDataObjectRespond;
import com.codesdream.ase.component.json.respond.JSONBaseRespondObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class QuickJSONRespond {
    @Resource
    private JSONParameter jsonParameter;

    // 根据对象构造获得标准的JSON响应字符串返回
    public String getJSONStandardRespond(Integer status, String msg, String info, Object dataObject){
        JSONBaseRespondObject respondObject = new JSONBaseRespondObject(status, msg);
        if(info != null) respondObject.setInfo(info);
        else  respondObject.setInfo(null);

        respondObject.setData(dataObject);
        return jsonParameter.getJSONString(respondObject);
    }

    // 获得标准的JSON响应字符串返回特定状态码的和解释息
    public String getJSONStandardRespond(Integer code, String msg, String info){
        JSONBaseRespondObject respondObject = new JSONBaseRespondObject(code, msg);
        if(info != null) respondObject.setInfo(info);
        else  respondObject.setInfo(null);
        respondObject.setData(null);
        return jsonParameter.getJSONString(respondObject);
    }

    // 获得标准的JSON响应字符串返回(404状态)
    public String getRespond404(String info){
        return getJSONStandardRespond(404, "Not Found", info);
    }

    // 获得标准的JSON响应字符串返回(500状态)
    public String getRespond500(String info){
        return getJSONStandardRespond(500, "Internal Server Error", info);
    }

    // 获得标准的JSON响应字符串返回(200状态)
    public String getRespond200(String info){
        return getJSONStandardRespond(200, "Ok", info);
    }

    // 获得标准的JSON响应字符串返回(200状态)
    public String getRespond200(String info, Object object){
        return getJSONStandardRespond(200, "Ok", info, object);
    }

    // 获得标准的JSON响应字符串返回(403状态)
    public String getRespond403(String info){
        return getJSONStandardRespond(403, "Forbidden", info);
    }

    // 获得标准的JSON响应字符串返回(403状态)
    public String getRespond406(String info){
        return getJSONStandardRespond(406, "Not Acceptable", info);
    }

    // 获得标准的JSON响应字符串返回(501态)
    public String getRespond501(String info){
        return getJSONStandardRespond(501, "Not Implemented", info)  ;
    }

    // 获得标准的JSON响应字符串返回(401状态)
    public String getRespond401(String info){
        return getJSONStandardRespond(401, "Unauthorized", info);
    }

    // 获得标准的JSON响应字符串返回(400状态)
    public String getRespond400(String info){
        return getJSONStandardRespond(400, "Bad Request", info);
    }

    // 获得标准的JSON响应字符串返回(400状态)
    public String getRespond409(String info){
        return getJSONStandardRespond(409, "Conflict", info);
    }


}
