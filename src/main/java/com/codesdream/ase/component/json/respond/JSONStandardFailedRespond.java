package com.codesdream.ase.component.json.respond;

// 请求失败返回JSON
public class JSONStandardFailedRespond extends JSONBaseRespondObject {
    public JSONStandardFailedRespond(){
        super(500, "failed");
    }
}
