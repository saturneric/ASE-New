package com.codesdream.ase.repository.parent;

import com.codesdream.ase.model.parent.Parent;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent,Integer> {
    List<Parent> findByStudentId(int studentId);
    List<Parent> findByStudentId(int studentId, Sort sort);

}
