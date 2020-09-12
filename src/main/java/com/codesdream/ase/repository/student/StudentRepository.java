package com.codesdream.ase.repository.student;

import com.codesdream.ase.model.student.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

        Student findByParentId(int parentId);
        Student findByParentId(int parentId, Sort sort);

}
