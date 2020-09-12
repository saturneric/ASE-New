package com.codesdream.ase.model.message;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class MessageDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    boolean isRead = false;

    int messageId;

    int senderId;

    int receiverId;
}
