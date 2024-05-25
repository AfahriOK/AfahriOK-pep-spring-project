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

    /**
     * This saves a message to the database
     * @param message the message to be saved
     * @return the saved message including its id
     */
    public Message createMessage(Message message){
        return messageRepository.save(message);
    }

    /**
     * This retrieves all messages in the database
     * @return a list of all messages
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * This retrieves a message based on its id
     * @param message_id the message id to be searched for
     * @return an optional of a message. Will not be present if message id not found
     */
    public Optional<Message> findById(Integer message_id) {
        return messageRepository.findById(message_id);
    }

    /**
     * This deletes a message based on its message id
     * @param message_id the id of the message to be deleted
     * @return 1 if message successfully deleted or 0 otherwise
     */
    public int deleteById(Integer message_id){
        if (messageRepository.findById(message_id).isPresent()){
            messageRepository.deleteById(message_id);
            return 1;
        }
        return 0;
    }

    /**
     * This updates a specific message based on its message id with the supplied message
     * @param message_id the id of the message to be updated
     * @param message the message information to be updated
     * @return 1 if message successfully updated or 0 otherwise
     */
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

    /**
     * This returns all the messages of a specified user
     * @param account_id the account id of the specified user
     * @return a list of messages by the user
     */
    public List<Message> getAllMessagesByUser(Integer account_id){
        return messageRepository.findByPostedBy(account_id);
    }
}
