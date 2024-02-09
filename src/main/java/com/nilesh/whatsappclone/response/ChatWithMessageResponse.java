package com.nilesh.whatsappclone.response;

import com.nilesh.whatsappclone.model.Chat;
import com.nilesh.whatsappclone.model.Message;

public class ChatWithMessageResponse {
    private Message message;
    private Chat chat;
    private Integer messageCount = 0;

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public ChatWithMessageResponse() {
    }

    @Override
    public String toString() {
        return "ChatWithMessageResponse [message=" + message + ", chat=" + chat + "]";
    }

}
