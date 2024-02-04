package com.nilesh.whatsappclone.expection;

import java.time.LocalDateTime;

public class ErrorDetial {
    private String error;
    private String detail;
    private LocalDateTime timestamp;

    public ErrorDetial() {
    }

    public ErrorDetial(String error, String detail, LocalDateTime timestamp) {
        this.error = error;
        this.detail = detail;
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ErrorDetial [error=" + error + ", detail=" + detail + ", timestamp=" + timestamp + "]";
    }
}
