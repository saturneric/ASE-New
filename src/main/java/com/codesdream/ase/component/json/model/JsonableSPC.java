package com.codesdream.ase.component.json.model;

import com.codesdream.ase.model.permission.ScopePermissionContainer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("范围性权限容器")
public class JsonableSPC {
    private Integer id;

    private String name;

    private String description;

    private boolean enabled;

    private boolean deleted;

    public JsonableSPC(ScopePermissionContainer spc){
        this.id = spc.getId();
        this.name = spc.getName();
        this.description = spc.getDescription();
        this.enabled = spc.isEnabled();
        this.deleted = spc.isDeleted();
    }

}
