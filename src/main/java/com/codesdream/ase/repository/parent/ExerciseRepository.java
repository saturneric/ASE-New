package com.codesdream.ase.repository.parent;

import com.codesdream.ase.model.parent.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    List<Exercise> findByStudentId(int studentId);
    Optional<Exercise> findById(int id);

}
