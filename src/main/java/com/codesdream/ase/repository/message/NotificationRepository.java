package com.codesdream.ase.repository.message;

import com.codesdream.ase.model.message.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Optional<Notification> findByTitle(String title);

    List<Notification> findAllByOrderByAnnouncementDateDesc();
}