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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//将合法的JSON对象转化为Activity对象
@Component
public class ActivityConverter {

    @Resource
    ActivityService activityService;

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

        String username = (String) jsonObject.get("creator");
        User creator = userService.findUserByUsername(username);
        activity.setCreator(creator);

        List<String> participateGroupFromJson = (List) jsonObject.get("participate-group");
        Set<User> participateGroup = new HashSet<>();
        for (String name : participateGroupFromJson) {
            User user = userService.findUserByUsername(name);
            participateGroup.add(user);
        }
        activity.setParticipateGroup(participateGroup);

        String title = (String) jsonObject.get("title");
        activity.setTitle(title);

        String chiefManagerName = (String) jsonObject.get("chief-manager");
        User chiefManager = userService.findUserByUsername(chiefManagerName);
        activity.setChiefManager(chiefManager);

        List<String> assistManagerFromJSON = (List) jsonObject.get("assist-manager");
        Set<User> assistManager = new HashSet<>();
        for (String name : assistManagerFromJSON) {
            User user = userService.findUserByUsername(name);
            assistManager.add(user);
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

        List<String> signGroupFromJSON = (List) jsonObject.get("sign-group");
        Set<User> signGroup = new HashSet<>();
        for (String name : signGroupFromJSON) {
            User user = userService.findUserByUsername(name);
            signGroup.add(user);
        }
        activity.setSignGroup(signGroup);

        List<String> informGroupFromJSON = (List) jsonObject.get("inform-group");
        Set<User> informGroup = new HashSet<>();
        for (String name : informGroupFromJSON) {
            User user = userService.findUserByUsername(name);
            informGroup.add(user);
        }
        activity.setInformGroup(informGroup);

        List<String> visibleGroupFromJSON = (List) jsonObject.get("visible-group");
        Set<User> visibleGroup = new HashSet<>();
        for (String name : visibleGroupFromJSON) {
            User user = userService.findUserByUsername(name);
            visibleGroup.add(user);
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
