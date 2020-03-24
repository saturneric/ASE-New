package com.codesdream.ase.controller;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.datamanager.QuickJSONRespond;
import com.codesdream.ase.component.json.respond.PermissionJSONRespond;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.service.IUserService;
import com.codesdream.ase.service.PermissionService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("pmt")
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
    @PostMapping("tag/create")
    public String createTag(HttpServletRequest request){
        Optional<JSONObject> jsonObjectOptional = jsonParameter.getJSONByRequest(request);
        if(!jsonObjectOptional.isPresent()) return jsonRespond.getRespond400("Illegal JSON Format");
        JSONObject jsonObject = jsonObjectOptional.get();

        String tagName = jsonObject.getString("name");
        if(tagName == null) return jsonRespond.getRespond406("Missing Tag Name");
        // 检查JSON是否合法
        Optional<Tag> tagOptional = permissionService.findTag(tagName);
        if(tagOptional.isPresent()) return jsonRespond.getRespond409("Tag Name Already Exist");

        Tag newTag = permissionService.getDefaultTag(tagName);
        newTag = permissionService.save(newTag);

        PermissionJSONRespond respond = new PermissionJSONRespond();
        respond.setActionSuccess(true);
        respond.setTagId(newTag.getId());
        respond.setTagName(newTag.getName());

        return jsonRespond.getRespond200(null, respond);
    }


    // 根据名字搜索标签的简要信息
    @PostMapping("tag/search")
    public String checkTag(HttpServletRequest request){
        Optional<JSONObject> jsonObjectOptional = jsonParameter.getJSONByRequest(request);
        if(!jsonObjectOptional.isPresent()) return jsonRespond.getRespond400("Illegal JSON Format");

        JSONObject jsonObject = jsonObjectOptional.get();
        String tagName = jsonObject.getString("name");
        if(tagName == null) return jsonRespond.getRespond406("Problem With Tag Name");
        Optional<Tag> tagOptional = permissionService.findTag(tagName);

        PermissionJSONRespond respond = new PermissionJSONRespond();
        respond.setActionSuccess(true);

        if(tagOptional.isPresent()){
            respond.setTagExist(true);
            respond.setTagId(tagOptional.get().getId());
            respond.setTagName(tagOptional.get().getName());
        }
        else respond.setTagExist(false);

        return jsonRespond.getRespond200(null, respond);
    }

    // 由标签ID找到用户ID列表
    @PostMapping("tag/get/users")
    public String getUsersTag(HttpServletRequest request){
        Optional<JSONObject> jsonObjectOptional = jsonParameter.getJSONByRequest(request);
        if(!jsonObjectOptional.isPresent()) return jsonRespond.getRespond400("Illegal JSON Format");

        JSONObject jsonObject = jsonObjectOptional.get();
        Integer tagId = jsonObject.getInteger("id");
        if(tagId == null) return jsonRespond.getRespond406("Problem With Tag ID");
        Optional<Tag> tagOptional = permissionService.findTag(tagId);

        PermissionJSONRespond respond = new PermissionJSONRespond();
        respond.setActionSuccess(true);

        if(tagOptional.isPresent()){
            respond.setTagExist(true);
            respond.setTagId(tagOptional.get().getId());
            Set<Integer> userIds = new HashSet<>();
            for(User user : permissionService.getUsersFromTag(tagOptional.get())) {
                userIds.add(user.getId());
            }
            respond.setUsers(userIds);
        }
        else respond.setTagExist(false);

        return jsonRespond.getRespond200(null, respond);
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
