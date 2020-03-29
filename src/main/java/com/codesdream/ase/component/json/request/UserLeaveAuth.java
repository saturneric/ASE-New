package com.codesdream.ase.component.json.request;

import lombok.Data;

@Data
public class UserLeaveAuth {
    /*
    备注
     */
    private int id;
    private String Comment;
    /*
    审核结果
     */
    private String newStat;
}
