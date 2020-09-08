package com.codesdream.ase.repository.student;

import com.codesdream.ase.model.student.StudentCourse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SCRepository extends JpaRepository<StudentCourse, Integer> {

    List<StudentCourse> findByStudentId(String studentId);
    List<StudentCourse> findByStudentId(String studentId, Sort sort);
}
