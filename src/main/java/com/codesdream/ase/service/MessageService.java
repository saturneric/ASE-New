package com.codesdream.ase.service;

import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.message.Message;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.message.MessageRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Resource
    MessageRepository messageRepository;

    /**
     * 创建新消息
     * @see Message
     * @param title 消息标题
     * @param text 消息内容
     * @return 持久化的消息
     */
    public Message createMessage(String title, String text){
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
    public Message createMessage(String title, String text, int type){
        Message message = createMessage(title, text);
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

    public Message searchMessageById(int messageId){
        if(!checkMessageExistence(messageId)){
            throw new NotFoundException("No such message.");
        }
        return messageRepository.findById(messageId).get();
    }

    public List<Message> searchMessageByTitle(String messageTitle){
        return messageRepository.findByTitle(messageTitle);
    }

    public boolean sendMessageToGroup(int messageId, List<User> users){
        if(!checkMessageExistence(messageId)){
            throw new NotFoundException("No such message.");
        }
        Message message = messageRepository.findById(messageId).get();
        return sendMessage(message, users);
    }

    private boolean checkMessageExistence(int messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(!optionalMessage.isPresent()){
            return false;
        }
        return true;
    }

}
