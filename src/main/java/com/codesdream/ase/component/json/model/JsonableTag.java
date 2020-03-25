package com.codesdream.ase.component.json.model;

import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.DefaultMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ApiModel("标签")
public class JsonableTag {
    @ApiModelProperty(value = "标签id")
    private Integer id = null;
    @ApiModelProperty(value = "标签名", example = "系统管理员")
    private String name;
    @ApiModelProperty(value = "标签说明", example = "该系统的管理员")
    private String description;
    @ApiModelProperty(value = "用户列表", hidden = true)
    private Set<Integer> users = new HashSet<>();

    public JsonableTag(){

    }

    public JsonableTag(Tag tag){
        this.id = tag.getId();
        this.name = tag.getName();
        this.description = tag.getDescription();
        // 构建用户的ID列表
        for(User user : tag.getUsers()) {
            users.add(user.getId());
        }
    }

}
