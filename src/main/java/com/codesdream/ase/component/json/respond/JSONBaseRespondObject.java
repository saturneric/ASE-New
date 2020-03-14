package com.codesdream.ase.component.json.respond;

import lombok.Data;

import java.util.Date;

// 服务端返回的JSON对象基础信息
@Data
public class JSONBaseRespondObject {
    // 请求成功状态
    String status = "fail";
    // 时间
    Date time = new Date();

    public JSONBaseRespondObject(){

    }

    public JSONBaseRespondObject(String status){
        this.status = status;
    }
}
