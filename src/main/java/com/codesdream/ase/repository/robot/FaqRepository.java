package com.codesdream.ase.repository.robot;


import com.codesdream.ase.model.robot.Faq;
import com.codesdream.ase.model.student.Honor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Integer> {

    Faq findByAnswer(String answer);
    Faq findByAnswer(String answer, Sort sort);
}
