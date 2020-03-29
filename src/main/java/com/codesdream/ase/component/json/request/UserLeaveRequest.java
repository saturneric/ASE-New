package com.codesdream.ase.component.json.request;

import lombok.Data;

import java.util.Date;
@Data
public class UserLeaveRequest {
    private String UserId;//用户名
    private String Type;//请假类型
    private String Reason;//请假原因
    private String Addon;//附件
    private Date Starttime;//开始时间
    private Date EndTime;//结束时间

}
