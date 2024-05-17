package com.example.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        if (accountService.exists(account.getUsername())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else if (account.getUsername().length() <= 0 ||  account.getPassword().length() <= 3) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Account>(accountService.register(account), HttpStatus.OK);
        }
    }

    @PostMapping("login")
    public ResponseEntity<Optional<Account>> login(@RequestBody Account account) {
        Optional<Account> verificationAccount = accountService.login(account.getUsername(), account.getPassword());
        if (!verificationAccount.isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } 

        return new ResponseEntity<Optional<Account>>(verificationAccount, HttpStatus.OK);
        
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if (message.getMessageText().length() <= 0 || message.getMessageText().length() > 255 || !accountService.exists(message.getPostedBy())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Message>(messageService.createMessage(message), HttpStatus.OK);
        }
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> retrieveMessages() {
        return new ResponseEntity<List<Message>>(messageService.getAllMessages(), HttpStatus.OK);
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Optional<Message>> getMessageById(@PathVariable Integer message_id) {
        Optional<Message> message = messageService.findById(message_id);
        if(message.isPresent()){
            return new ResponseEntity<Optional<Message>>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteById(@PathVariable Integer message_id){
        Optional<Message> message = messageService.findById(message_id);
        if(messageService.deleteById(message_id) == 1){
            return new ResponseEntity<Integer>(1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> updateById(@PathVariable Integer message_id, @RequestBody Message message){
        if (messageService.updateById(message_id, message) == 1){
            return new ResponseEntity<Integer>(1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer account_id){
        return new ResponseEntity<List<Message>>(messageService.getAllMessagesByUser(account_id), HttpStatus.OK);
    }
}

