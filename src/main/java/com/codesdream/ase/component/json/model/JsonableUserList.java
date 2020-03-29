package com.codesdream.ase.component.json.model;

import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("标签所属用户集合")
public class JsonableUserList {

    @ApiModelProperty(name = "用户列表")
    private List<Integer> users;


    public JsonableUserList(Tag tag){
        for(User user : tag.getUsers()){
            users.add(user.getId());
        }
    }
}
