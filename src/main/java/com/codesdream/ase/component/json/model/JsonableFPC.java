package com.codesdream.ase.component.json.model;

import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.spring.web.json.Json;

@Data
@NoArgsConstructor
@ApiModel("功能性权限容器")
public class JsonableFPC {
    private Integer id;

    private String name;

    private String description;

    private boolean enabled;

    private boolean deleted;

    public JsonableFPC(FunctionalPermissionContainer fpc){
        this.id = fpc.getId();
        this.name = fpc.getName();
        this.description = fpc.getDescription();
        this.enabled = fpc.isEnabled();
        this.deleted = fpc.isDeleted();
    }

    public FunctionalPermissionContainer parseObject(FunctionalPermissionContainer fpc){
        fpc.setName(this.name);
        fpc.setDescription(this.description);
        fpc.setEnabled(this.enabled);
        fpc.setDeleted(this.deleted);
        return fpc;
    }
}
