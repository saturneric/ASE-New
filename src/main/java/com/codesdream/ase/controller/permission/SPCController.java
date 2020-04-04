package com.codesdream.ase.controller.permission;

import com.codesdream.ase.component.json.model.JsonableSPC;
import com.codesdream.ase.component.json.model.JsonableTag;
import com.codesdream.ase.exception.badrequest.AlreadyExistException;
import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.permission.ScopePermissionContainer;
import com.codesdream.ase.model.permission.Tag;
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
@Api(tags = "范围性权限容器接口")
public class SPCController {

    @Resource
    private PermissionService permissionService;

    @GetMapping(value = "spc")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获得范围性权限容器信息")
    public JsonableSPC getSPC(@RequestParam(value = "id") Integer id){
        Optional<ScopePermissionContainer> spc = permissionService.findSPC(id);
        if(!spc.isPresent()) throw new NotFoundException(id.toString());

        return new JsonableSPC(spc.get());

    }

    @GetMapping(value = "spcs")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获得所有的范围性权限容器信息")
    public Set<JsonableSPC> listSPC(){
        Iterable<ScopePermissionContainer> spcs = permissionService.findALLSPC();
        Set<JsonableSPC> jsonableSPCS = new HashSet<>();
        for(ScopePermissionContainer spc : spcs){
            jsonableSPCS.add(new JsonableSPC(spc));
        }
        return jsonableSPCS;
    }

    @GetMapping(value = "spc/tag")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查询范围性权限容器下的所有标签集合")
    public Set<JsonableTag> listSPCTag(@RequestParam(value = "id") Integer id){
        Optional<ScopePermissionContainer> spc = permissionService.findSPC(id);
        if(!spc.isPresent()) throw new NotFoundException(id.toString());

        Set<JsonableTag> tags = new HashSet<>();
        for(Tag tag : spc.get().getTags()){
            tags.add(new JsonableTag(tag));
        }
        return tags;
    }

    @PostMapping(value = "spc")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "创建范围性权限容器")
    public JsonableSPC createSPC(@RequestBody JsonableSPC spc) {
        if(permissionService.findSPC(spc.getName()).isPresent()) throw new AlreadyExistException(spc.getName());

        ScopePermissionContainer defaultSPC = permissionService.getDefaultSPC(spc.getName());
        defaultSPC.setDeleted(spc.isDeleted());
        defaultSPC.setEnabled(spc.isEnabled());
        defaultSPC.setDescription(spc.getDescription());

        return new JsonableSPC(permissionService.save(defaultSPC));
    }

}
