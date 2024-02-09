package com.nilesh.whatsappclone.request;

public class UpdateMessageRequest {
    private Integer messageId;
    private String content;

    public UpdateMessageRequest() {
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
