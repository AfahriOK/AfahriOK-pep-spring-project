package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;


@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService (MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> findById(Integer message_id) {
        return messageRepository.findById(message_id);
    }

    public int deleteById(Integer message_id){
        if (messageRepository.findById(message_id).isPresent()){
            messageRepository.deleteById(message_id);
            return 1;
        }
        return 0;
    }

    public int updateById(Integer message_id, Message message){
        Optional<Message> updatedMessage = messageRepository.findById(message_id);
        if(!updatedMessage.isPresent() || message.getMessageText().length() > 255 || message.getMessageText().length() <= 0) {
            return 0;
        } else {
            updatedMessage.get().setMessageText(message.getMessageText());
            messageRepository.save(updatedMessage.get());
            return 1;
        }
    }

    public List<Message> getAllMessagesByUser(Integer account_id){
        return messageRepository.findByPostedBy(account_id);
    }
}
