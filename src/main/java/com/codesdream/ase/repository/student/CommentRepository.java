package com.codesdream.ase.repository.student;

import com.codesdream.ase.model.student.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByUserId(String id, Sort sort);

    List<Comment> findByUserId(String id);
}
