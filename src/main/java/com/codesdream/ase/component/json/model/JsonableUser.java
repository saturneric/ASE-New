package com.codesdream.ase.component.json.model;

import com.codesdream.ase.model.permission.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("用户")
@NoArgsConstructor
public class JsonableUser {
    private Integer id;
    private String username;

    public JsonableUser(User user){
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
