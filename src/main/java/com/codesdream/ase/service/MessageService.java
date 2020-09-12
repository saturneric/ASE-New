package com.codesdream.ase.service;

import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.file.File;
import com.codesdream.ase.model.message.Message;
import com.codesdream.ase.model.message.Notification;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.message.MessageRepository;
import com.codesdream.ase.repository.message.NotificationRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Resource
    MessageRepository messageRepository;


    @Resource
    NotificationRepository notificationRepository;

    /**
     * 创建新消息
     * @see Message
     * @param title 消息标题
     * @param text 消息内容
     * @return 持久化的消息
     */
    public Message createMessage(String title, String text, int userId){
        Message message = new Message();

        message.setText(text);
        message.setTitle(title);

        return messageRepository.save(message);
    }

    /**
     * 创建新消息
     * @see Message
     * @param title 消息标题
     * @param text 消息内容
     * @param type 消息类型（紧急程度）
     * @return 持久化的消息
     */
    public Message createMessage(String title, String text, int userId, int type){
        Message message = createMessage(title, text, userId);
        message.setType(type);
        return messageRepository.save(message);
    }

    /**
     * 推送消息到指定用户名下
     * @Todo
     * @param message 待传递的消息
     * @param targets 目标用户
     * @return 是否推送成功
     */
    public boolean sendMessage(Message message, List<User> targets){

        return true;
    }

    /**
     * 删除信息
     * @exception NotFoundException 如果messageId无效
     * @param messageId 信息id
     * @return 是否删成功
     */
    public boolean deleteMessage(int messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        try{
            if(!checkMessageExistence(messageId)){
                throw new NotFoundException("No such message.");
            }
        }catch (Exception e){
            return false;
        }
        Message message = optionalMessage.get();
        messageRepository.delete(message);
        return true;
    }

    /**
     * 根据message的id寻找对应的message，若不存在则抛出{@code NotFoundException}异常
     * @exception NotFoundException 若id无效
     * @param messageId 消息id
     * @return 寻找到的message
     */
    public Message searchMessageById(int messageId){
        if(!checkMessageExistence(messageId)){
            throw new NotFoundException("No such message.");
        }
        return messageRepository.findById(messageId).get();
    }

    /**
     * 根据消息标题寻找消息，若无此标题，返回空列表
     * @param messageTitle 消息标题
     * @return 消息列表
     */
    public List<Message> searchMessageByTitle(String messageTitle){
        return messageRepository.findByTitle(messageTitle);
    }

    /**
     * 把消息发送给指定用户，若消息id不存在，则抛出{@code NotFoundException}异常
     * @exception NotFoundException 若消息id不存在
     * @param messageId 消息id
     * @param users 用户列表
     * @return 是否发送成功
     */
    public boolean sendMessageToGroup(int messageId, List<User> users){
        if(!checkMessageExistence(messageId)){
            throw new NotFoundException("No such message.");
        }
        Message message = messageRepository.findById(messageId).get();
        return sendMessage(message, users);
    }

    /**
     * 用于创建一个公告
     * @see Notification
     * @param title 公告标题
     * @param description 公告内容
     * @param files 公告所需附件
     * @return 已经持久化的公告
     */
    public Notification createNotification(String title, String description, List<File> files){
        Notification notification = new Notification();

        notification.setTitle(title);
        notification.setText(description);
        notification.setFiles(files);

        return notificationRepository.save(notification);
    }

    /**
     * 在数据库中删除指定公告
     * @param notificationId 需要删除的公告id，如果此id不存在，则会抛出异常，删除失败
     * @return 是否删除成功
     */
    public boolean cancelNotification(int notificationId){
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if(notification.isPresent()){
            notificationRepository.delete(notification.get());
            return true;
        }
        else{
            throw new NotFoundException("No such notification.");
        }
    }

    /**
     * 私有方法用于判断消息id是否存在
     * @param messageId 消息id
     * @return id存在与否
     */
    private boolean checkMessageExistence(int messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(!optionalMessage.isPresent()){
            return false;
        }
        return true;
    }

}
