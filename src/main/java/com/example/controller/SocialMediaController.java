package com.example.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * This is a register handler for the register endpoint
     * @param account the account to be registered
     * @return the new account record including its id if successful. If unsuccessful then a bad request code or conflict code
     */
    @PostMapping("register")
    public ResponseEntity<Account> registerHandler(@RequestBody Account account) {
        if (accountService.exists(account.getUsername())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else if (account.getUsername().length() <= 0 ||  account.getPassword().length() <= 3) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Account>(accountService.register(account), HttpStatus.OK);
        }
    }

    /**
     * This is a login handler for the login endpoint
     * @param account the account to be authenticated
     * @return the account information if authenticated or an unauthorized code otherwise
     */
    @PostMapping("login")
    public ResponseEntity<Optional<Account>> loginHandler(@RequestBody Account account) {
        Optional<Account> verificationAccount = accountService.login(account.getUsername(), account.getPassword());
        if (!verificationAccount.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } 

        return new ResponseEntity<Optional<Account>>(verificationAccount, HttpStatus.OK);
        
    }

    /**
     * This is create message handler for the messages endpoint
     * @param message the message to be created
     * @return the new message is created successfully or a bad request error otherwise
     */
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if (message.getMessageText().length() <= 0 || message.getMessageText().length() > 255 || !accountService.exists(message.getPostedBy())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Message>(messageService.createMessage(message), HttpStatus.OK);
        }
    }

    /**
     * This is a message handler to retrieve all messages from the messages endpooint
     * @return a list of all messages
     */
    @GetMapping("messages")
    public ResponseEntity<List<Message>> retrieveMessages() {
        return new ResponseEntity<List<Message>>(messageService.getAllMessages(), HttpStatus.OK);
    }

    /**
     * This is a message handler to get messages by the message id
     * @param message_id the message id to be searched for
     * @return the message if it exists or an  OK status otherwise
     */
    @GetMapping("messages/{message_id}")
    public ResponseEntity<Optional<Message>> getMessageById(@PathVariable Integer message_id) {
        Optional<Message> message = messageService.findById(message_id);
        if(message.isPresent()){
            return new ResponseEntity<Optional<Message>>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * This is a delete message handler to delete a message based on its id
     * @param message_id the id of the message to be deleted
     * @return 1 if the message was deleted or just an ok code otherwise
     */
    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteById(@PathVariable Integer message_id){
        if(messageService.deleteById(message_id) == 1){
            return new ResponseEntity<Integer>(1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * This is a patch message handler to update a message based on its id
     * @param message_id the id of the message to be updated
     * @param message the new information to be persisted to  the message
     * @return 1 if the message was successfully updated or a bad request code otherwise
     */
    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> updateById(@PathVariable Integer message_id, @RequestBody Message message){
        if (messageService.updateById(message_id, message) == 1){
            return new ResponseEntity<Integer>(1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This is a message hanbdler to retrieve all messages from a user based on the account id
     * @param account_id the account id of the user to be searched for
     * @return a list of all messages made by the specified user
     */
    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer account_id){
        return new ResponseEntity<List<Message>>(messageService.getAllMessagesByUser(account_id), HttpStatus.OK);
    }
}

