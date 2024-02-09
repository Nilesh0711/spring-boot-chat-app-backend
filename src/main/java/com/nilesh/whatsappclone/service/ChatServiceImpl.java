package com.nilesh.whatsappclone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nilesh.whatsappclone.expection.ChatException;
import com.nilesh.whatsappclone.expection.UserException;
import com.nilesh.whatsappclone.model.Chat;
import com.nilesh.whatsappclone.model.Message;
import com.nilesh.whatsappclone.model.User;
import com.nilesh.whatsappclone.repository.ChatRepository;
import com.nilesh.whatsappclone.request.GroupChatRequest;
import com.nilesh.whatsappclone.response.ChatWithMessageResponse;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Override
    public Chat createChat(User reqUser, Integer userId2) throws UserException {
        User user = userService.findUserById(userId2);
        Chat isChatExist = chatRepository.findSingleChatByUserIds(user, reqUser);
        if (isChatExist != null)
            return isChatExist;
        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);
        chatRepository.save(chat);
        return chat;

    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (chat.isPresent())
            return chat.get();
        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public List<ChatWithMessageResponse> findAllChatByUserId(Integer userId) throws UserException, ChatException {
        User user = userService.findUserById(userId);
        List<Chat> chatByUserId = chatRepository.findChatByUserId(user.getId());
        List<ChatWithMessageResponse> chatWithMessages = new ArrayList<>();
        for (Chat chat : chatByUserId) {
            ChatWithMessageResponse chatWithMessageResponse = new ChatWithMessageResponse();
            Message message = messageService.getChatsLastMessages(chat.getId(), user);
            chatWithMessageResponse.setChat(chat);
            chatWithMessageResponse.setMessage(message);
            chatWithMessages.add(chatWithMessageResponse);
        }
        return chatWithMessages;
        // return chatByUserId;

    }

    @Override
    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {
        Chat groupChat = new Chat();
        groupChat.setGroup(true);
        groupChat.setChat_image(req.getChat_image());
        groupChat.setChat_name(req.getChat_name());
        groupChat.setCreatedBy(reqUser);
        groupChat.getAdmins().add(reqUser);
        for (Integer iterable : req.getUserIds()) {
            User userById = userService.findUserById(iterable);
            groupChat.getUsers().add(userById);
        }
        chatRepository.save(groupChat);
        return groupChat;
    }

    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws ChatException, UserException {
        Optional<Chat> chatById = chatRepository.findById(chatId);
        User userById = userService.findUserById(userId);

        if (chatById.isPresent()) {
            Chat chat = chatById.get();
            if (chat.getAdmins().contains(reqUser)) {
                chat.getUsers().add(userById);
                chatRepository.save(chat);
                return chat;
            }
            throw new UserException("You are not a admin of this group");
        }
        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public Chat renameGroup(Integer chatId, User reqUser, String groupName) throws ChatException, UserException {
        Optional<Chat> chatById = chatRepository.findById(chatId);
        if (chatById.isPresent()) {
            Chat chat = chatById.get();
            if (chat.getUsers().contains(reqUser)) {
                chat.setChat_name(groupName);
                return chatRepository.save(chat);
            }
            throw new ChatException("You are not a member of this group");
        }
        throw new ChatException("Chat not found with id " + chatId);

    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws ChatException, UserException {
        Optional<Chat> chatById = chatRepository.findById(chatId);
        User userById = userService.findUserById(userId);

        if (chatById.isPresent()) {
            Chat chat = chatById.get();
            if (chat.getAdmins().contains(reqUser) || reqUser.getId().equals(userById.getId())) {
                chat.getUsers().remove(userById);
                chatRepository.save(chat);
                return chat;
            }
            throw new UserException("You can't remove this user");
        }
        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        Optional<Chat> chatById = chatRepository.findById(chatId);
        if (chatById.isPresent()) {
            Chat chat = chatById.get();
            chatRepository.deleteById(chat.getId());
        }
    }

}
