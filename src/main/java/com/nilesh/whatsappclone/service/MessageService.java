package com.nilesh.whatsappclone.service;

import java.util.List;

import com.nilesh.whatsappclone.expection.ChatException;
import com.nilesh.whatsappclone.expection.MessageException;
import com.nilesh.whatsappclone.expection.UserException;
import com.nilesh.whatsappclone.model.Message;
import com.nilesh.whatsappclone.model.User;
import com.nilesh.whatsappclone.request.SendMessageRequest;

public interface MessageService {
    
    public Message sendMessage(SendMessageRequest sendMessageRequest) throws UserException, ChatException;

    public List<Message> getChatsMessages(Integer chatId, User reqUser) throws UserException, ChatException;

    public Message findMessageById(Integer messageId) throws MessageException;

    public void deleteMessageById(Integer messageId, User reqUser) throws UserException, MessageException;

}
