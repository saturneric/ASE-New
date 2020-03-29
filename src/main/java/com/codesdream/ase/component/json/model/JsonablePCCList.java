package com.codesdream.ase.component.json.model;

import com.codesdream.ase.model.permission.PermissionContainersCollection;
import com.codesdream.ase.model.permission.Tag;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("权限容器集合列表")
public class JsonablePCCList {
    List<Integer> pccIdList;

    public JsonablePCCList(Tag tag){
        for(PermissionContainersCollection pcc : tag.getPermissionContainersCollections()){
            pccIdList.add(pcc.getId());
        }
    }
}
