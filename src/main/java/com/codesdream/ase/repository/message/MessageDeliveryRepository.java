package com.codesdream.ase.repository.message;

import com.codesdream.ase.model.message.MessageDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDeliveryRepository extends JpaRepository<MessageDelivery, Integer> {
    List<MessageDelivery> findByReceiverId(int id);

    List<MessageDelivery> findBySenderId(int id);

    List<MessageDelivery> findBySenderIdAndIsReadTrue(int id);
}
