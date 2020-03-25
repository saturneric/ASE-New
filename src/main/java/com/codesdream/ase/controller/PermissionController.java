package com.codesdream.ase.controller;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.api.QuickJSONRespond;
import com.codesdream.ase.component.json.model.JsonableTag;
import com.codesdream.ase.component.json.respond.PermissionJSONRespond;
import com.codesdream.ase.exception.notfound.TagNotFoundException;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.service.IUserService;
import com.codesdream.ase.service.PermissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
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

    @Resource
    private JSONParameter jsonParameter;

    @Resource
    private QuickJSONRespond jsonRespond;

    // 根据名字创建新的标签
    @PostMapping("tag")
    @ApiOperation(value = "创建新的标签", notes = "创建标签时其ID自动分配，指定ID无效")
    public JsonableTag createTag(@RequestBody JsonableTag tag){

        String tagName = tag.getName();
/*        if(tagName == null) return jsonRespond.getRespond406("Missing Tag Name");*/

        Optional<Tag> tagOptional = permissionService.findTag(tagName);
/*        if(tagOptional.isPresent()) return jsonRespond.getRespond409("Tag Name Already Exist");*/

        Tag newTag = permissionService.getDefaultTag(tagName);
        if(tag.getDescription() != null) {
            newTag.setDescription(tag.getDescription());
        }
        return new JsonableTag(permissionService.save(newTag));
    }


    // 根据名字搜索标签的简要信息
    @GetMapping("tag")
    @ApiOperation("搜索标签信息")
    @ApiImplicitParam(name = "name", value = "标签名")
    public JsonableTag checkTag(@RequestParam(value = "name") String name){
        Optional<Tag> tagOptional = permissionService.findTag(name);
        if(tagOptional.isPresent()){
            return new JsonableTag(tagOptional.get());
        }
        else throw new TagNotFoundException(name);
    }

    // 将用户添加到Tag中
    @PostMapping("tag/add/user")
    public String addUserTag(HttpServletRequest request){
        Optional<JSONObject> jsonObjectOptional = jsonParameter.getJSONByRequest(request);
        if(!jsonObjectOptional.isPresent()) return jsonRespond.getRespond400("Illegal JSON Format");

        JSONObject jsonObject = jsonObjectOptional.get();
        Integer tagId = jsonObject.getInteger("tagId");
        Integer userId = jsonObject.getInteger("userId");

        if(userId == null || tagId == null)
            return jsonRespond.getRespond406("Request Violates The Interface Protocol");

        Optional<User> user = userService.findUserById(userId);
        if(!user.isPresent()) return jsonRespond.getRespond406("User Not Exist");

        Optional<Tag> tag = permissionService.findTag(tagId);
        if(!tag.isPresent()) return jsonRespond.getRespond406("Tag Not Exist");

        // 检查用户是否已经在标签中
        if(tag.get().getUsers().contains(user.get())) return jsonRespond.getRespond409("User Already In The Tag");
        permissionService.addUserToTag(tag.get(), user.get());

        return  jsonRespond.getRespond200("Add User TO Tag Successful");
    }



}
