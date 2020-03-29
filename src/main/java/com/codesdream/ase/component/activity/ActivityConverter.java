package com.codesdream.ase.component.activity;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.exception.DataInvalidFormatException;
import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.activity.Attendance;
import com.codesdream.ase.model.activity.Period;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.activity.ActivityRepository;
import com.codesdream.ase.service.ActivityService;
import com.codesdream.ase.service.AttendanceService;
import com.codesdream.ase.service.PeriodService;
import com.codesdream.ase.service.UserService;
import javafx.util.converter.LocalDateTimeStringConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//将合法的JSON对象转化为Activity对象
@Component
public class ActivityConverter {

    @Resource
    UserService userService;

    @Resource
    PeriodService periodService;

    @Resource
    AttendanceService attendanceService;

    public Activity convertToActivity(Optional<JSONObject> json) {
        if (!json.isPresent()) {
            throw new NullPointerException();
        }
        Activity activity = new Activity();
        JSONObject jsonObject = json.get();

        int userId = (int) jsonObject.get("creator");
        Optional<User> creator = userService.findUserById(userId);
        activity.setCreator(creator.get());

        List<Integer> participateGroupFromJson = (List<Integer>) jsonObject.get("participate-group");
        Set<User> participateGroup = new HashSet<>();
        for (int id : participateGroupFromJson) {
            Optional<User> user = userService.findUserById(id);
            participateGroup.add(user.get());
        }
        activity.setParticipateGroup(participateGroup);

        String title = (String) jsonObject.get("title");
        activity.setTitle(title);

        int chiefManagerId = (int) jsonObject.get("chief-manager");
        Optional<User> chiefManager = userService.findUserById(chiefManagerId);
        activity.setChiefManager(chiefManager.get());

        List<Integer> assistManagersFromJSON = (List<Integer>) jsonObject.get("assist-managers");
        Set<User> assistManager = new HashSet<>();
        for (int id : assistManagersFromJSON) {
            Optional<User> user = userService.findUserById(id);
            assistManager.add(user.get());
        }
        activity.setAssistManagers(assistManager);

        String type = (String) jsonObject.get("type");
        activity.setType(type);

        String startTimeFromJSON = (String) jsonObject.get("start-time");
        String endTimeFromJSON = (String) jsonObject.get("end-time");
        LocalDateTime startTime = LocalDateTime.parse(startTimeFromJSON, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(endTimeFromJSON, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Period period = new Period(startTime, endTime);
        period.setEnabled(false);
        period = periodService.save(period);
        activity.setPlanPeriod(period);

        String cycle = (String) jsonObject.get("cycle");
        activity.setCycle(cycle);

        String description = (String) jsonObject.get("description");
        activity.setDescription(description);

        List<Integer> signGroupFromJSON = (List<Integer>) jsonObject.get("sign-group");
        Set<User> signGroup = new HashSet<>();
        for (int id : signGroupFromJSON) {
            Optional<User> user = userService.findUserById(id);
            signGroup.add(user.get());
        }
        activity.setSignGroup(signGroup);

        List<Integer> informGroupFromJSON = (List<Integer>) jsonObject.get("inform-group");
        if (informGroupFromJSON == null) {
            participateGroupFromJson.removeAll(signGroupFromJSON);
            participateGroupFromJson.addAll(signGroupFromJSON);
            informGroupFromJSON = participateGroupFromJson;
        }
        Set<User> informGroup = new HashSet<>();
        for (int id : informGroupFromJSON) {
            Optional<User> user = userService.findUserById(id);
            informGroup.add(user.get());
        }
        activity.setInformGroup(informGroup);

        List<Integer> visibleGroupFromJSON = (List<Integer>) jsonObject.get("visible-group");
        Set<User> visibleGroup = new HashSet<>();
        for (int id : visibleGroupFromJSON) {
            Optional<User> user = userService.findUserById(id);
            visibleGroup.add(user.get());
        }
        activity.setVisibleGroup(informGroup);

        String remindTimeFromJSON = (String) jsonObject.get("remind-time");
        String numStr = remindTimeFromJSON.substring(0, remindTimeFromJSON.length() - 1);
        int num = Integer.parseInt(numStr);
        char unit = remindTimeFromJSON.charAt(remindTimeFromJSON.length() - 1);
        switch (unit) {
            case 'w': {
                activity.setRemindTime(activity.getPlanPeriod().getStartTime().minusWeeks(num));
                break;
            }
            case 'd': {
                activity.setRemindTime(activity.getPlanPeriod().getStartTime().minusDays(num));
                break;
            }
            case 'm': {
                activity.setRemindTime(activity.getPlanPeriod().getStartTime().minusMinutes(num));
                break;
            }
            case 'h': {
                activity.setRemindTime(activity.getPlanPeriod().getStartTime().minusHours(num));
                break;
            }
            case 's': {
                activity.setRemindTime(activity.getPlanPeriod().getStartTime().minusSeconds(num));
            }
        }

        Set<Period> periods = new HashSet<>();
        String[] attendanceTimes = (String[]) jsonObject.get("attendance");
        boolean attendanceOnLine = (boolean) jsonObject.get("attendance-online");
        if ((attendanceTimes.length & 1) == 1) {
            throw new DataInvalidFormatException();
        }
        for (int i = 0; i < attendanceTimes.length; i += 2) {
            LocalDateTime start = LocalDateTime.parse(attendanceTimes[i], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime end = LocalDateTime.parse(attendanceTimes[i + 1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Period period1 = new Period(start, end);
            periods.add(period1);
        }
        Attendance attendance = new Attendance();
        attendance.setClockInPeriods(periods);
        attendance.setOnline(attendanceOnLine);
        /**
         * 二维码模块未完成
         */
        attendance = attendanceService.save(attendance);
        activity.setAttendance(attendance);

        activity.setOn(false);
        activity.setOff(false);

        return activity;

    }
}
