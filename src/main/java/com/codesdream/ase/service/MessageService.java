package com.codesdream.ase.service;

import com.codesdream.ase.model.message.Message;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.message.MessageRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

}
