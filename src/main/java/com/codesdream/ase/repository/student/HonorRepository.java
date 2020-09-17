package com.codesdream.ase.repository.student;

import com.codesdream.ase.model.parent.Exercise;
import com.codesdream.ase.model.student.Honor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HonorRepository extends JpaRepository<Honor, Integer> {

    List<Honor> findByStudentId(int studentId);
    List<Honor> findByStudentId(int studentId, Sort sort);
    Optional<Honor> findById(int id);
}
