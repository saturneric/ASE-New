package com.codesdream.ase.repository.student;

import com.codesdream.ase.model.student.Student;
import com.codesdream.ase.model.student.StudentCourse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

        Student findByParentId(int parentId);
        Student findByStudentId(int studentId);
}
