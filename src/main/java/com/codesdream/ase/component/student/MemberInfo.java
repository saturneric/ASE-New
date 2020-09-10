package com.codesdream.ase.component.student;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberInfo {

    private int memberId;

    // -1：创建人 0：管理员 1：普通参与者
    private int memberType;

    public MemberInfo(int memberId, int memberType){
        this.memberId = memberId;
        this.memberType = memberType;
    }
}
