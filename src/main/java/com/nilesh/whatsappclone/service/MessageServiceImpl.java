package com.nilesh.whatsappclone.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nilesh.whatsappclone.expection.ChatException;
import com.nilesh.whatsappclone.expection.MessageException;
import com.nilesh.whatsappclone.expection.UserException;
import com.nilesh.whatsappclone.model.Chat;
import com.nilesh.whatsappclone.model.Message;
import com.nilesh.whatsappclone.model.User;
import com.nilesh.whatsappclone.repository.MessageRepository;
import com.nilesh.whatsappclone.request.SendMessageRequest;
import com.nilesh.whatsappclone.request.UpdateMessageRequest;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message sendMessage(SendMessageRequest sendMessageRequest) throws UserException, ChatException {
        User userById = userService.findUserById(sendMessageRequest.getUserId());
        Chat chatById = chatService.findChatById(sendMessageRequest.getChatId());

        Message message = new Message();
        message.setChat(chatById);
        message.setUser(userById);
        message.setContent(sendMessageRequest.getContent());
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
        return message;

    }

    @Override
    public List<Message> getChatsMessages(Integer chatId, User reqUser) throws UserException, ChatException {
        Chat chatById = chatService.findChatById(chatId);
        if (!chatById.getUsers().contains(reqUser)) {
            throw new UserException("You are not a part of this chat");
        }
        List<Message> messages = messageRepository.findByChatId(chatById.getId());
        return messages;
    }

    @Override
    public Message getChatsLastMessages(Integer chatId, User reqUser) throws UserException, ChatException {
        Chat chatById = chatService.findChatById(chatId);
        if (!chatById.getUsers().contains(reqUser)) {
            throw new UserException("You are not a part of this chat");
        }
        Message messages = messageRepository.findLastMessageByChatId(chatById.getId());
        return messages;
    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent())
            return message.get();
        throw new MessageException("Message not found with id " + messageId);
    }

    @Override
    public void deleteMessageById(Integer messageId, User reqUser) throws UserException, MessageException {
        Message messageById = findMessageById(messageId);
        if (messageById.getUser().getId().equals(reqUser.getId()))
            messageRepository.delete(messageById);
        throw new UserException("You are not a part of this chat");
    }

    @Override
    public Message updatedMessageContent(UpdateMessageRequest updateMessageRequest, User reqUser)
            throws MessageException, UserException {

        Integer messageId = updateMessageRequest.getMessageId();
        String content = updateMessageRequest.getContent();

        Message messageById = findMessageById(messageId);
        if (messageById.getUser().getId().equals(reqUser.getId())) {
            messageById.setContent(content);
            messageById.setIsEdited(true);
            messageRepository.save(messageById);
            return messageById;
        }
        throw new UserException("You are not a part of this chat");
    }

    @Override
    public Message updatedMessageDeleted(Integer messageId, User reqUser) throws MessageException, UserException {
        Message messageById = findMessageById(messageId);
        if (messageById.getUser().getId().equals(reqUser.getId())) {
            messageById.setIsDeleted(true);
            messageRepository.save(messageById);
            return messageById;
        }
        throw new UserException("You are not a part of this chat");
    }

}
