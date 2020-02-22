package com.codesdream.ase.component.datamanager;

import lombok.Data;

import java.util.Date;

// 服务端返回的JSON对象模板
@Data
public class RespondJSONBaseObject {
    String status = "fail";
    Date time = new Date();

    public RespondJSONBaseObject(){

    }

    public RespondJSONBaseObject(String status){
        this.status = status;
    }
}
