package com.codesdream.ase.component.json.model;

import com.codesdream.ase.model.permission.Function;
import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@ApiModel("功能性权限容器所属角色集合")
public class JsonableRoleList {
    private Integer id;
    private Set<Integer> functions = new HashSet<>();

    public JsonableRoleList(FunctionalPermissionContainer fpc){
        this.id = fpc.getId();
        if(fpc.getFunctions() != null) {
            for(Function function : fpc.getFunctions())
            this.functions.add(function.getId());
        }
    }
}
