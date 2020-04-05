package com.codesdream.ase.controller.permission;

import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.model.JsonableFPC;
import com.codesdream.ase.component.json.model.JsonableFunction;
import com.codesdream.ase.component.json.model.JsonableRoleList;
import com.codesdream.ase.exception.badrequest.AlreadyExistException;
import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.permission.Function;
import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import com.codesdream.ase.service.PermissionService;
import com.github.fge.jsonpatch.JsonPatch;
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
@Api(tags = "功能接口")
public class FunctionController {
    @Resource
    private PermissionService permissionService;

    @Resource
    private JSONParameter jsonParameter;

    @GetMapping("func")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查找功能")
    public JsonableFunction getFunction(@RequestParam(value = "id") Integer id){
        Optional<Function> function = permissionService.findFunction(id);
        if(!function.isPresent()) throw new NotFoundException(id.toString());
        return new JsonableFunction(function.get());
    }

    @PostMapping("func")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "创建新的功能")
    public JsonableFunction createFunction(@RequestBody JsonableFunction function){
        if(permissionService.findFunction(function.getName()).isPresent())
            throw new AlreadyExistException(function.getName());

        return new JsonableFunction(permissionService.save(function.parseObject(new Function())));
    }

    @GetMapping("funcs")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获得所有功能的信息")
    public Set<JsonableFunction> listFunction(){
        Iterable<Function> functions = permissionService.findAllFunction();
        Set<JsonableFunction> jsonableFunctions = new HashSet<>();
        for(Function function : functions){
            jsonableFunctions.add(new JsonableFunction(function));
        }
        return jsonableFunctions;
    }

    @PatchMapping("func")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "更新标功能内容")
    public JsonableFunction patchFunction(@RequestParam(value = "id") Integer id, @RequestBody JsonPatch patch){
        Optional<Function> function = permissionService.findFunction(id);
        if(!function.isPresent()) throw new NotFoundException(id.toString());

        JsonableFunction jsonableFunction = new JsonableFunction(function.get());

        jsonableFunction = jsonParameter.parsePathToObject(patch, jsonableFunction);

        return new JsonableFunction(permissionService.update(jsonableFunction.parseObject(function.get())));
    }
}
