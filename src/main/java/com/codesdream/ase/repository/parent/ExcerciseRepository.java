package com.codesdream.ase.repository.parent;

import com.codesdream.ase.model.parent.Excercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcerciseRepository extends JpaRepository<Excercise, Integer> {
    List<Excercise> findByStudentId(int studentId);
}
