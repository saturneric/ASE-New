package com.codesdream.ase.repository.message;

import com.codesdream.ase.model.message.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByTitle(String title);
    List<Message> findByTitle(String title, Sort sort);

    List<Message> findByTitleContaining(String title);
    List<Message> findByTitleContaining(String title, Sort sort);
}
