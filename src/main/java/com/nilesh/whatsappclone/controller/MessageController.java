package com.nilesh.whatsappclone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nilesh.whatsappclone.expection.ChatException;
import com.nilesh.whatsappclone.expection.MessageException;
import com.nilesh.whatsappclone.expection.UserException;
import com.nilesh.whatsappclone.model.Message;
import com.nilesh.whatsappclone.model.User;
import com.nilesh.whatsappclone.request.SendMessageRequest;
import com.nilesh.whatsappclone.response.ApiResponse;
import com.nilesh.whatsappclone.service.MessageService;
import com.nilesh.whatsappclone.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest sendMessageRequest,
            @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        User reqUser = userService.findUserByProfile(jwt);
        sendMessageRequest.setUserId(reqUser.getId());
        Message message = messageService.sendMessage(sendMessageRequest);

        return new ResponseEntity<Message>(message, HttpStatus.CREATED);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessagesHandler(@PathVariable Integer chatId,
            @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        User reqUser = userService.findUserByProfile(jwt);
        List<Message> chatsMessages = messageService.getChatsMessages(chatId, reqUser);

        return new ResponseEntity<List<Message>>(chatsMessages, HttpStatus.OK);
    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity<ApiResponse> deleteMessageByIdHandler(@PathVariable Integer chatId,
            @RequestHeader("Authorization") String jwt) throws UserException, ChatException, MessageException {

        User reqUser = userService.findUserByProfile(jwt);
        messageService.deleteMessageById(chatId, reqUser);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Message deleted successfully");
        apiResponse.setStatus(true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

}
