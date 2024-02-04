package com.nilesh.whatsappclone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nilesh.whatsappclone.expection.ChatException;
import com.nilesh.whatsappclone.expection.UserException;
import com.nilesh.whatsappclone.model.Chat;
import com.nilesh.whatsappclone.model.User;
import com.nilesh.whatsappclone.request.GroupChatRequest;
import com.nilesh.whatsappclone.request.SingleChatRequest;
import com.nilesh.whatsappclone.response.ApiResponse;
import com.nilesh.whatsappclone.service.ChatService;
import com.nilesh.whatsappclone.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @PostMapping("/single")
    public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,
            @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserByProfile(jwt);
        Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());
        return new ResponseEntity<Chat>(chat, HttpStatus.CREATED);
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest,
            @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserByProfile(jwt);
        Chat chat = chatService.createGroup(groupChatRequest, reqUser);
        return new ResponseEntity<Chat>(chat, HttpStatus.CREATED);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatByIdHandler(@PathVariable Integer chatId,
            @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        Chat chat = chatService.findChatById(chatId);
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findChatByUserIdHandler(@RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {
        User reqUser = userService.findUserByProfile(jwt);
        List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());
        return new ResponseEntity<List<Chat>>(chats, HttpStatus.OK);
    }

    @PostMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId,
            @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserByProfile(jwt);
        Chat chat = chatService.addUserToGroup(userId, chatId, reqUser);
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @PostMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeFromGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId,
            @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserByProfile(jwt);
        Chat chat = chatService.removeFromGroup(chatId, userId, reqUser);
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId,
            @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserByProfile(jwt);
        chatService.deleteChat(chatId, reqUser.getId());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Chat has been deleted successfully");
        apiResponse.setStatus(true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

}
