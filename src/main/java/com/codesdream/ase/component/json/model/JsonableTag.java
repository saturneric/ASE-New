package com.codesdream.ase.component.json.model;

import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.DefaultMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@ApiModel("标签")
public class JsonableTag {
    @ApiModelProperty(value = "标签id")
    private Integer id = null;
    @ApiModelProperty(value = "标签名", example = "系统管理员")
    private String name;
    @ApiModelProperty(value = "标签说明", example = "该系统的管理员")
    private String description;


    public JsonableTag(Tag tag){
        this.id = tag.getId();
        this.name = tag.getName();
        this.description = tag.getDescription();
    }

}
