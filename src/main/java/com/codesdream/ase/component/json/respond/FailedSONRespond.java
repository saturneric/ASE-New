package com.codesdream.ase.component.json.respond;

// 请求失败返回JSON
public class FailedSONRespond extends JSONBaseRespondObject {
    public FailedSONRespond(){
        super();
        this.status = "fail";
    }
}
