package com.codesdream.ase.repository.student;

import com.codesdream.ase.model.student.StudentCourse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, Integer> {
    List<StudentCourse> findByStudentId(int studentId);
    List<StudentCourse> findByStudentId(int studentId, Sort sort);
    List<StudentCourse> findByStudentIdAndTerm(int studentId,int term);
    List<StudentCourse> findByStudentIdAndTerm(int studentId,int term,Sort sort);

}
