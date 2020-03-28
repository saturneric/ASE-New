package com.codesdream.ase.service;

import com.codesdream.ase.model.activity.Attendance;
import com.codesdream.ase.repository.activity.AttendanceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AttendanceService {

    @Resource
    AttendanceRepository attendanceRepository;

    public Attendance save(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }
}
