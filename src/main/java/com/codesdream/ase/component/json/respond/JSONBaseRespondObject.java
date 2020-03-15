package com.codesdream.ase.component.json.respond;

import com.codesdream.ase.component.json.JSONBaseObject;
import lombok.Data;

import java.util.Date;

// 服务端返回的JSON对象模板
@Data
public class JSONBaseRespondObject extends JSONBaseObject {
    // 请求成功状态
    String status = "fail";

    public JSONBaseRespondObject(){

    }

    public JSONBaseRespondObject(String status){
        this.status = status;
    }
}
