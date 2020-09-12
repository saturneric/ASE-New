package com.codesdream.ase.repository.parent;

import com.codesdream.ase.model.parent.Parent;
import com.codesdream.ase.model.student.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent,Integer> {
    List<Parent> findByStudentId(String studentId);
    List<Parent> findByStudentId(String studentId, Sort sort);

}
