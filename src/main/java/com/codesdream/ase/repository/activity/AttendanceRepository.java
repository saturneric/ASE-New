package com.codesdream.ase.repository.activity;

import com.codesdream.ase.model.activity.Attendance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends CrudRepository<Attendance, Integer> {
}
