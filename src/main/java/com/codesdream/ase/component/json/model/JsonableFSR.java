package com.codesdream.ase.component.json.model;

import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import com.codesdream.ase.model.permission.FunctionalScopeRelation;
import com.codesdream.ase.model.permission.ScopePermissionContainer;
import com.codesdream.ase.service.IPermissionService;
import com.codesdream.ase.service.PermissionService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Optional;

@Data
@NoArgsConstructor
@ApiModel("单项权力")
public class JsonableFSR {
    private Integer id;

    private String name;

    private String description;

    private Integer fpcId;

    private Integer spcId;

    @Resource
    @ApiModelProperty(hidden = true)
    private IPermissionService permissionService;

    public JsonableFSR(FunctionalScopeRelation fsr){
        this.id = fsr.getId();
        this.name = fsr.getName();
        this.description = fsr.getDescription();
        this.fpcId = fsr.getFunctionalPermissionContainer().getId();
        this.spcId = fsr.getScopePermissionContainer().getId();
    }

    public FunctionalScopeRelation parseObject(FunctionalScopeRelation fsr){
        fsr.setName(this.name);
        fsr.setDescription(this.description);
        if(this.fpcId != null){
            Optional<FunctionalPermissionContainer> fpc = permissionService.findFPC(this.fpcId);
            if(!fpc.isPresent()) throw new NotFoundException(this.fpcId.toString());
            fsr.setFunctionalPermissionContainer(fpc.get());
        }

        if(this.spcId != null){
            Optional<ScopePermissionContainer> spc = permissionService.findSPC(this.spcId);
            if(!spc.isPresent()) throw new NotFoundException(this.spcId.toString());
            fsr.setScopePermissionContainer(spc.get());
        }

        return fsr;

    }
}
