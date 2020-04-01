package com.codesdream.ase.controller;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.api.QuickJSONRespond;
import com.codesdream.ase.component.auth.ASEUsernameEncoder;
import com.codesdream.ase.component.datamanager.JSONParameter;

import com.codesdream.ase.component.json.request.UserLeaveAuth;
import com.codesdream.ase.component.json.request.UserLeaveRequest;
import com.codesdream.ase.component.json.request.UserSGettudentLeaveListRequest;
import com.codesdream.ase.component.json.respond.JSONStandardFailedRespond;
import com.codesdream.ase.exception.innerservererror.InvalidFormFormatException;
import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.leaves.Leave;
import com.codesdream.ase.service.LeavesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


/**
 * 登录界面控制器类
 */
@Slf4j
@Controller
public class LeavesController {

    @Resource
    private JSONParameter jsonParameter;

    @Resource
    private QuickJSONRespond quickJSONRespond;

    @Resource
    private LeavesService leavesService;

    @Resource
    private ASEUsernameEncoder usernameEncoder;

/*    @RequestMapping(value = "/")
    String printLeave(Model model) {
        return "Leave";
    }*/

    //提交请假申请
    @RequestMapping(value = "/Leave/check", method = RequestMethod.POST)
    @ResponseBody
    String leaveRequest(HttpServletRequest request){

        // 检查是否为JSON
        Optional<JSONObject> json = jsonParameter.getJSONByRequest(request);
        if(!json.isPresent()) return jsonParameter.getJSONString(new JSONStandardFailedRespond());
        UserLeaveRequest userLeaveRequest=json.get().toJavaObject(UserLeaveRequest.class);
        Leave newleave=new Leave();
        newleave.setUserFrom(userLeaveRequest.getUserId());
        newleave.setType(userLeaveRequest.getType());
        newleave.setReasonToLeave(userLeaveRequest.getReason());
        //newleave.set
        newleave.setStartTime(userLeaveRequest.getStarttime());
        newleave.setEndTime(userLeaveRequest.getEndTime());
        Calendar calendar =Calendar.getInstance();
        Date time = calendar.getTime();
        newleave.setApplyTime(time);

        leavesService.save(newleave);
        //eturn quickJSONRespond.getRespond200(null, respond);

        return quickJSONRespond.getRespond200("申请已提交");
        // 检查类型

    }
    @RequestMapping(value = "/Leave/auth", method = RequestMethod.POST)
    @ResponseBody
    String getLeaveAuth(HttpServletRequest request){

        // 检查是否为JSON
        Optional<JSONObject> json = jsonParameter.getJSONByRequest(request);
        if(!json.isPresent()) return jsonParameter.getJSONString(new JSONStandardFailedRespond());
        UserLeaveAuth userLeaveAuth=json.get().toJavaObject(UserLeaveAuth.class);
        Optional<Leave> leave =leavesService.findLeaveById(userLeaveAuth.getId());
        if(!leave.isPresent()){
            return quickJSONRespond.getRespond500("No such entry");
        }
        Leave newLeave=leave.get();
        newLeave.setComment(userLeaveAuth.getComment());
        newLeave.setAuthentication(userLeaveAuth.getNewStat());
        return quickJSONRespond.getRespond200("Authentication status updated");
    }

    @RequestMapping(value = "/Leave/getStuLeaveList", method = RequestMethod.POST)
    @ResponseBody
    Leave getStuLeavelist(HttpServletRequest request) throws InvalidFormFormatException {
        Optional<JSONObject> json = jsonParameter.getJSONByRequest(request);
        if(!json.isPresent()) throw new InvalidFormFormatException("json request not recognized");
        UserSGettudentLeaveListRequest userSGettudentLeaveListRequest=json.get().toJavaObject(UserSGettudentLeaveListRequest.class);
        if(leavesService.findLeaveById(userSGettudentLeaveListRequest.getStudentId()).isPresent()){
            Leave leave =leavesService.findLeaveById(userSGettudentLeaveListRequest.getStudentId()).get();
            return leave;
        }else{
            throw new NotFoundException("Student Does not exist");
        }

    }


}
