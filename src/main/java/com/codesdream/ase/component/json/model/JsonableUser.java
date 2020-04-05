package com.codesdream.ase.component.json.model;

import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.model.permission.UserAuth;
import com.codesdream.ase.model.permission.UserDetail;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("用户")
@NoArgsConstructor
public class JsonableUser {
    private Integer id;
    private String username;
    private boolean enabled;
    private boolean deleted;


    public JsonableUser(User user){
        this.id = user.getId();
        this.username = user.getUsername();

        this.enabled = user.isEnabled();
        this.deleted= user.isDeleted();
    }

    public User parseObject(User user){
        user.setEnabled(this.enabled);
        user.setDeleted(this.deleted);
        return user;
    }
}
