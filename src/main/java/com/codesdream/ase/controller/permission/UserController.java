package com.codesdream.ase.controller.permission;

import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.model.JsonableUser;
import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.service.UserService;
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
@Api(tags = "用户管理接口")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private JSONParameter jsonParameter;

    @GetMapping("user")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "查找用户")
    public JsonableUser getUser(@RequestParam(value = "id") Integer id){
        Optional<User> user = userService.findUserById(id);
        if(!user.isPresent()) throw new NotFoundException(id.toString());
        return new JsonableUser(user.get());
    }

    @GetMapping("users")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "获得所有用户的信息")
    public Set<JsonableUser> listUser(){
        Iterable<User> users = userService.findAll();
        Set<JsonableUser> jsonableUsers = new HashSet<>();
        for(User user : users){
            jsonableUsers.add(new JsonableUser(user));
        }
        return jsonableUsers;
    }

    @PatchMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "更新标用户的部分信息")
    public JsonableUser patchUser(@RequestParam(value = "id") Integer id, @RequestBody JsonPatch patch){
        Optional<User> userOptional = userService.findUserById(id);
        if(!userOptional.isPresent()) throw new NotFoundException(id.toString());

        JsonableUser jsonableUser = new JsonableUser(userOptional.get());

        jsonableUser = jsonParameter.parsePathToObject(patch, jsonableUser);

        return new JsonableUser(userService.update(jsonableUser.parseObject(userOptional.get())));
    }
}
