package com.codesdream.ase.component.json.model;

import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.permission.Function;
import com.codesdream.ase.service.IPermissionService;
import com.codesdream.ase.service.PermissionService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.Optional;

@Data
@NoArgsConstructor
@ApiModel("功能")
public class JsonableFunction {

    private Integer id;

    private String name;

    private String description;

    private Integer fatherId;

    private String url;

    @Resource
    @ApiModelProperty(hidden = true)
    private IPermissionService permissionService;

    public JsonableFunction(Function function){
        this.id = function.getId();
        this.name = function.getName();
        this.description = function.getDescription();
        if(function.getFather() != null) {
            this.fatherId = function.getFather().getId();
        }
        else this.fatherId = null;
        this.url = function.getUrl();
    }

    public Function parseObject(Function function){
        function.setName(this.name);
        function.setDescription(this.description);
        if(this.fatherId != null) {
            Optional<Function> fatherFunction = permissionService.findFunction(this.fatherId);
            if (!fatherFunction.isPresent()) throw new NotFoundException(fatherId.toString());
            function.setFather(fatherFunction.get());
        }
        function.setUrl(this.url);

        return function;
    }
}
