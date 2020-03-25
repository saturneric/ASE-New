package com.codesdream.ase.controller;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.api.QuickJSONRespond;
import com.codesdream.ase.component.json.model.JsonableTag;
import com.codesdream.ase.component.json.model.JsonableTagUserList;
import com.codesdream.ase.component.json.model.JsonableUser;
import com.codesdream.ase.component.json.respond.PermissionJSONRespond;
import com.codesdream.ase.exception.badrequest.AlreadyExistException;
import com.codesdream.ase.exception.conflict.RelatedObjectsExistException;
import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.exception.notfound.TagNotFoundException;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.service.IUserService;
import com.codesdream.ase.service.PermissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("pmt")
@Api(tags = "权限管理接口")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @Resource
    private IUserService userService;

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
    @ApiImplicitParam(name = "name", value = "标签名")
    public JsonableTag checkTag(@RequestParam(value = "name") String name){
        Optional<Tag> tagOptional = permissionService.findTag(name);
        if(tagOptional.isPresent()){
            return new JsonableTag(tagOptional.get());
        }
        else throw new NotFoundException(name);
    }

    // 根据名字搜索标签的简要信息
    @GetMapping("tags")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("列出所有的标签信息")
    @ApiImplicitParam(name = "name", value = "标签名")
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
    @ApiImplicitParam(name = "name", value = "标签名")
    public void deleteTag(@RequestParam(value = "name") String name){
        Optional<Tag> tag = permissionService.findTag(name);
        if(!tag.isPresent()) throw new NotFoundException(name);

        // 检查外键关联
        if(tag.get().getUsers().size() > 0) throw new RelatedObjectsExistException();
        if(tag.get().getPermissionContainersCollections().size() > 0) throw new RelatedObjectsExistException();

        permissionService.delete(tag.get());
    }


    @GetMapping("tag/users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("搜索单个标签所属用户集合信息")
    public JsonableTagUserList getUserTag(@RequestParam(value = "name") String name){
        Optional<Tag> tag = permissionService.findTag(name);
        if(!tag.isPresent()) throw new NotFoundException(name);
        return new JsonableTagUserList(tag.get());
    }

    @PutMapping("tag/users")
    @ApiOperation("更新索单个标签所属用户集合信息")
    public JsonableTagUserList setUserTag(@RequestParam String name, @RequestBody JsonableTagUserList userList){
        Optional<Tag> tag = permissionService.findTag(name);
        if(!tag.isPresent()) throw new NotFoundException(name);

        Set<Integer> userSet = new HashSet<>(userList.getUsers());
        tag.get().setUsers(userService.findUsersById(userSet));

        return new JsonableTagUserList(permissionService.save(tag.get()));
    }

    @PostMapping("tag/users")
    @ApiOperation("更新单个标签所属用户集合中添加一个或多个用户")
    public JsonableTagUserList addUserTag(@RequestParam String name, @RequestBody JsonableTagUserList userList){
        Optional<Tag> tag = permissionService.findTag(name);
        if(!tag.isPresent()) throw new NotFoundException(name);
        Set<User> newUserSet = userService.findUsersById(new HashSet<>(userList.getUsers()));

        Set<User> userSet = tag.get().getUsers();

        userSet.addAll(newUserSet);
        tag.get().setUsers(userSet);

        return new JsonableTagUserList(permissionService.save(tag.get()));
    }

    @DeleteMapping("tag/users")
    @ApiOperation("从单个标签所属用户集合中删除一个或多个用户")
    @ApiImplicitParam(name = "name", value = "标签名")
    public JsonableTagUserList deleteUserTag(@RequestParam String name, @RequestBody JsonableTagUserList userList){
        Optional<Tag> tag = permissionService.findTag(name);
        if(!tag.isPresent()) throw new NotFoundException(name);
        Set<User> userSet = tag.get().getUsers();
        Set<User> deleteUserSet = userService.findUsersById(new HashSet<>(userList.getUsers()));

        userSet.removeAll(deleteUserSet);
        tag.get().setUsers(userSet);

        return new JsonableTagUserList(permissionService.save(tag.get()));
    }

    @GetMapping("tags/users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("搜索多个标签所属用户集合信息")
    public Set<JsonableUser> getUserTags(@RequestParam(value = "name") List<String> names){
        Set<Tag> tagSet = permissionService.findTags(names);
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



}
