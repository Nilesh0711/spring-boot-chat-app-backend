package com.nilesh.whatsappclone.service;


import java.util.List;

import com.nilesh.whatsappclone.expection.ChatException;
import com.nilesh.whatsappclone.expection.UserException;
import com.nilesh.whatsappclone.model.Chat;
import com.nilesh.whatsappclone.model.User;
import com.nilesh.whatsappclone.request.GroupChatRequest;

public interface ChatService {
    public Chat createChat(User reqUser, Integer userId2) throws UserException;

    public Chat findChatById(Integer chatId) throws ChatException;

    public List<Chat> findAllChatByUserId(Integer userId) throws UserException;

    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException;

    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws ChatException, UserException;

    public Chat renameGroup(Integer chatId, User reqUser, String groupName) throws ChatException, UserException;

    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws ChatException, UserException;

    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException;

}
