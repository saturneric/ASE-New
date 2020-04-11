package com.codesdream.ase.controller.permission;

import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.model.JsonableFPC;
import com.codesdream.ase.component.json.model.JsonableFunction;
import com.codesdream.ase.component.json.model.JsonableRoleList;
import com.codesdream.ase.component.json.model.JsonableUser;
import com.codesdream.ase.exception.badrequest.AlreadyExistException;
import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.permission.Function;
import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.service.PermissionService;
import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "pmt")
@Api(tags = "功能性权限容器接口")
public class FPCController {

    @Resource
    private PermissionService permissionService;

    @Resource
    private JSONParameter jsonParameter;

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


        return new JsonableFPC(permissionService.save(fpc.parseObject(functionalPermissionContainer)));
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

    @GetMapping("fpc/roles")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获得功能性权限容器所属角色")
    public JsonableRoleList getFPCRoleList(@RequestParam(value = "id") Integer id){
        Optional<FunctionalPermissionContainer> functionalPermissionContainer =
                permissionService.findFPC(id);
        if(!functionalPermissionContainer.isPresent()) throw new NotFoundException(id.toString());

        return new JsonableRoleList(functionalPermissionContainer.get());
    }

    @PatchMapping("fpc")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "更新标功能性权限容器内容")
    public JsonableFPC patchFPC(@RequestParam(value = "id") Integer id, @RequestBody JsonPatch patch){
        Optional<FunctionalPermissionContainer> fpc = permissionService.findFPC(id);
        if(!fpc.isPresent()) throw new NotFoundException(id.toString());

        JsonableFPC jsonableFPC = new JsonableFPC(fpc.get());

        jsonableFPC = jsonParameter.parsePathToObject(patch, jsonableFPC);

        return new JsonableFPC(permissionService.update(jsonableFPC.parseObject(fpc.get())));
    }

    @GetMapping("fpc/funcs")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "功能性权限容器所含功能集合")
    public Set<JsonableFunction> getFunctionFPC(@RequestParam(value = "id") Integer id){
        Optional<FunctionalPermissionContainer> fpc = permissionService.findFPC(id);
        if(!fpc.isPresent()) throw new NotFoundException(id.toString());


        Set<JsonableFunction> jsonableFunctions = new HashSet<>();
        for(Function function : fpc.get().getFunctions()){
            jsonableFunctions.add(new JsonableFunction(function));
        }
        return jsonableFunctions;
    }

    @PutMapping("fpc/funcs")
    @ApiOperation("搜索单个功能性权限容器所属功能集合信息")
    public Set<JsonableFunction> setFunctionFPC(@RequestParam(value = "id") Integer id,
                                        @RequestBody List<Integer> functionIdList){
        Optional<FunctionalPermissionContainer> fpc = permissionService.findFPC(id);
        if(!fpc.isPresent()) throw new NotFoundException(id.toString());

        Set<Integer> functionSet = new HashSet<>(functionIdList);
        fpc.get().setFunctions(permissionService.findFunctions(functionSet));

        Set<JsonableFunction> jsonableFunctions = new HashSet<>();
        for(Function function : permissionService.update(fpc.get()).getFunctions()){
            jsonableFunctions.add(new JsonableFunction(function));
        }
        return jsonableFunctions;
    }

    @PostMapping("fpc/funcs")
    @ApiOperation("从单个功能性权限容器所属功能集合中添加一个或多个功能")
    public Set<JsonableFunction> addFunctionFPC(@RequestParam(value = "id") Integer id,
                                        @RequestBody List<Integer> functionIdList){
        Optional<FunctionalPermissionContainer> fpc = permissionService.findFPC(id);
        if(!fpc.isPresent()) throw new NotFoundException(id.toString());
        Set<Function> newFunctionSet = permissionService.findFunctions(new HashSet<>(functionIdList));

        Set<Function> functionSet = fpc.get().getFunctions();

        functionSet.addAll(newFunctionSet);
        fpc.get().setFunctions(functionSet);

        Set<JsonableFunction> jsonableFunctions = new HashSet<>();
        for(Function function : permissionService.update(fpc.get()).getFunctions()){
            jsonableFunctions.add(new JsonableFunction(function));
        }
        return jsonableFunctions;
    }

    @DeleteMapping("fpc/funcs")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("从单个功能性权限容器所属功能集合中删除一个或多个功能")
    public Set<JsonableFunction> deleteUserTag(@RequestParam Integer id,
                                           @RequestBody List<Integer> functionIdList){
        Optional<FunctionalPermissionContainer> fpc = permissionService.findFPC(id);
        if(!fpc.isPresent()) throw new NotFoundException(id.toString());
        Set<Function> functionSet = fpc.get().getFunctions();
        Set<Function> deleteFuncSet = permissionService.findFunctions(new HashSet<>(functionIdList));

        functionSet.removeAll(deleteFuncSet);
        fpc.get().setFunctions(functionSet);

        Set<JsonableFunction> jsonableFunctions = new HashSet<>();
        for(Function function : permissionService.update(fpc.get()).getFunctions()){
            jsonableFunctions.add(new JsonableFunction(function));
        }
        return jsonableFunctions;
    }

}
