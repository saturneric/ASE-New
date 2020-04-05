package com.codesdream.ase.controller.permission;

import com.codesdream.ase.component.json.model.JsonableFPC;
import com.codesdream.ase.component.json.model.JsonableRoleList;
import com.codesdream.ase.exception.badrequest.AlreadyExistException;
import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import com.codesdream.ase.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "pmt")
@Api(tags = "功能性权限容器接口")
public class FPCController {

    @Resource
    private PermissionService permissionService;

    @GetMapping("fpc")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查找功能性权限容器")
    public JsonableFPC getFPC(@RequestParam(value = "id") Integer id){
        Optional<FunctionalPermissionContainer> fpc = permissionService.findFPC(id);
        if(!fpc.isPresent()) throw new NotFoundException(id.toString());
        return new JsonableFPC(fpc.get());
    }

    @PostMapping("fpc")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "创建新的功能性权限容器")
    public JsonableFPC createFPC(@RequestBody JsonableFPC fpc){
        if(permissionService.findFPC(fpc.getName()).isPresent()) throw new  AlreadyExistException(fpc.getName());
        FunctionalPermissionContainer functionalPermissionContainer = new FunctionalPermissionContainer();

        functionalPermissionContainer.setName(fpc.getName());
        functionalPermissionContainer.setDescription(fpc.getDescription());
        functionalPermissionContainer.setEnabled(fpc.isEnabled());
        functionalPermissionContainer.setDeleted(fpc.isDeleted());

        return new JsonableFPC(permissionService.save(functionalPermissionContainer));
    }

    @GetMapping("fpcs")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获得所有功能性权限容器的信息")
    public Set<JsonableFPC> listFPC(){
        Iterable<FunctionalPermissionContainer> fpcs = permissionService.findAllFPC();
        Set<JsonableFPC> jsonableFPCS = new HashSet<>();
        for(FunctionalPermissionContainer fpc : fpcs){
            jsonableFPCS.add(new JsonableFPC(fpc));
        }
        return jsonableFPCS;
    }

    @GetMapping("fpc/role")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获得功能性权限容器所属角色")
    public JsonableRoleList getFPCRoleList(@RequestParam(value = "id") Integer id){
        Optional<FunctionalPermissionContainer> functionalPermissionContainer =
                permissionService.findFPC(id);
        if(!functionalPermissionContainer.isPresent()) throw new NotFoundException(id.toString());

        return new JsonableRoleList(functionalPermissionContainer.get());
    }




}
