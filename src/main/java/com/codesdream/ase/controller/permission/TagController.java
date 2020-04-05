package com.codesdream.ase.controller.permission;

import com.codesdream.ase.component.datamanager.JsonPathParameter;
import com.codesdream.ase.component.json.model.JsonablePCCList;
import com.codesdream.ase.component.json.model.JsonableTag;
import com.codesdream.ase.component.json.model.JsonableUserList;
import com.codesdream.ase.component.json.model.JsonableUser;
import com.codesdream.ase.exception.badrequest.AlreadyExistException;
import com.codesdream.ase.exception.conflict.RelatedObjectsExistException;
import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.permission.PermissionContainersCollection;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.service.IUserService;
import com.codesdream.ase.service.PermissionService;

import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("pmt")
@Api(tags = "标签管理接口")
public class TagController {

    @Resource
    private PermissionService permissionService;

    @Resource
    private IUserService userService;

    @Resource
    private JsonPathParameter pathParameter;

    // 根据名字创建新的标签
    @PostMapping("tag")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "创建新的标签", notes = "创建标签时其ID自动分配，指定ID无效")
    public JsonableTag createTag(@RequestBody JsonableTag tag){
        String tagName = tag.getName();
        Optional<Tag> tagOptional = permissionService.findTag(tagName);
        if(tagOptional.isPresent()) throw new AlreadyExistException(tagName);
        Tag newTag = permissionService.getDefaultTag(tagName);
        if(tag.getDescription() != null) {
            newTag.setDescription(tag.getDescription());
        }
        return new JsonableTag(permissionService.save(newTag));
    }

    // 根据名字搜索标签的简要信息
    @GetMapping("tag")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("搜索标签信息")
    public JsonableTag checkTag(@RequestParam(value = "id") Integer id){
        Optional<Tag> tagOptional = permissionService.findTag(id);
        if(tagOptional.isPresent()){
            return new JsonableTag(tagOptional.get());
        }
        else throw new NotFoundException(id.toString());
    }

    // 根据名字搜索标签的简要信息
    @GetMapping("tags")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("列出所有的标签信息")
    public Set<JsonableTag> listTag(){
        Iterable<Tag> tagIterable = permissionService.findAllTag();
        Set<JsonableTag> jsonableTagSet = new HashSet<>();
        for(Tag tag : tagIterable){
            jsonableTagSet.add(new JsonableTag(tag));
        }
        return jsonableTagSet;
    }

    // 根据名字搜索标签的简要信息
    @DeleteMapping("tag")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("删除标签")
    public void deleteTag(@RequestParam(value = "id") Integer id){
        Optional<Tag> tag = permissionService.findTag(id);
        if(!tag.isPresent()) throw new NotFoundException(id.toString());

        // 检查外键关联
        if(tag.get().getUsers().size() > 0) throw new RelatedObjectsExistException();
        if(tag.get().getPermissionContainersCollections().size() > 0) throw new RelatedObjectsExistException();

        permissionService.delete(tag.get());
    }

    // 根据名字搜索标签的简要信息
    @PatchMapping(path = "tag")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("修改标签属性")
    public JsonableTag updateTag(@RequestParam(value = "id") Integer id, @RequestBody JsonPatch patch){
        Optional<Tag> tag = permissionService.findTag(id);
        if(!tag.isPresent()) throw new NotFoundException(id.toString());

        JsonableTag jsonableTag = new JsonableTag(tag.get());
        jsonableTag  = pathParameter.parsePathToObject(patch, jsonableTag);

        tag.get().setName(jsonableTag.getName());
        tag.get().setDescription(jsonableTag.getDescription());

        return new JsonableTag(permissionService.save(tag.get()));

    }

    @GetMapping("tag/users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("搜索单个标签所属用户集合信息")
    public Set<JsonableUser> getUserTag(@RequestParam(value = "id") Integer id){
        Optional<Tag> tag = permissionService.findTag(id);
        if(!tag.isPresent()) throw new NotFoundException(id.toString());
        Set<JsonableUser> jsonableUsers = new HashSet<>();
        for(User user : tag.get().getUsers()){
            jsonableUsers.add(new JsonableUser(user));
        }
        return jsonableUsers;
    }

    @PutMapping("tag/users")
    @ApiOperation("更新索单个标签所属用户集合信息")
    public Set<JsonableUser> setUserTag(@RequestParam(value = "id") Integer id,
                                        @RequestBody JsonableUserList userList){
        Optional<Tag> tag = permissionService.findTag(id);
        if(!tag.isPresent()) throw new NotFoundException(id.toString());

        Set<Integer> userSet = new HashSet<>(userList.getUsers());
        tag.get().setUsers(userService.findUsersById(userSet));

        Set<JsonableUser> jsonableUsers = new HashSet<>();
        for(User user : tag.get().getUsers()){
            jsonableUsers.add(new JsonableUser(user));
        }
        return jsonableUsers;
    }

    @PostMapping("tag/users")
    @ApiOperation("更新单个标签所属用户集合中添加一个或多个用户")
    public Set<JsonableUser> addUserTag(@RequestParam(value = "id") Integer id,
                                        @RequestBody JsonableUserList userList){
        Optional<Tag> tag = permissionService.findTag(id);
        if(!tag.isPresent()) throw new NotFoundException(id.toString());
        Set<User> newUserSet = userService.findUsersById(new HashSet<>(userList.getUsers()));

        Set<User> userSet = tag.get().getUsers();

        userSet.addAll(newUserSet);
        tag.get().setUsers(userSet);

        Set<JsonableUser> jsonableUsers = new HashSet<>();
        for(User user : tag.get().getUsers()){
            jsonableUsers.add(new JsonableUser(user));
        }
        return jsonableUsers;
    }

    @DeleteMapping("tag/users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("从单个标签所属用户集合中删除一个或多个用户")
    public Set<JsonableUser> deleteUserTag(@RequestParam Integer id,
                                           @RequestBody JsonableUserList userList){
        Optional<Tag> tag = permissionService.findTag(id);
        if(!tag.isPresent()) throw new NotFoundException(id.toString());
        Set<User> userSet = tag.get().getUsers();
        Set<User> deleteUserSet = userService.findUsersById(new HashSet<>(userList.getUsers()));

        userSet.removeAll(deleteUserSet);
        tag.get().setUsers(userSet);

        Set<JsonableUser> jsonableUsers = new HashSet<>();
        for(User user : tag.get().getUsers()){
            jsonableUsers.add(new JsonableUser(user));
        }
        return jsonableUsers;
    }

    @GetMapping("tags/users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("搜索多个标签所属用户集合信息")
    public Set<JsonableUser> getUserTags(@RequestParam(value = "id") List<Integer> ids){
        Set<Tag> tagSet = permissionService.findTags(ids);
        Set<User> userSet = new HashSet<>();
        Set<JsonableUser> jsonableUsers = new HashSet<>();
        for(Tag tag : tagSet){
            userSet.addAll(tag.getUsers());
        }
        for(User user : userSet){
            jsonableUsers.add(new JsonableUser(user));
        }
        return jsonableUsers;
    }

    @GetMapping("tag/pcc")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取标签所含权限容器集合列表")
    public JsonablePCCList getPCCTag(@RequestParam(value = "id") Integer id){
        Optional<Tag> tagOptional = permissionService.findTag(id);
        if(!tagOptional.isPresent()) throw new NotFoundException(id.toString());

        return new JsonablePCCList(tagOptional.get());
    }

    @PostMapping("tag/pcc")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("在指定标签的权限列表中添加一个或多个权限容器")
    public JsonablePCCList addPCCTag(@RequestParam(value = "id") Integer id, JsonablePCCList jsonablePCCList){
        Optional<Tag> tagOptional = permissionService.findTag(id);
        if(!tagOptional.isPresent()) throw new NotFoundException(id.toString());

        Set<PermissionContainersCollection> pccs = tagOptional.get().getPermissionContainersCollections();
        pccs.addAll(permissionService.findPCCs(new HashSet<Integer>(jsonablePCCList.getPccIdList())));

        tagOptional.get().setPermissionContainersCollections(pccs);

        return new JsonablePCCList(permissionService.save(tagOptional.get()));
    }



}
