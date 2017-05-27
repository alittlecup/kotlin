package com.example.hbl.kotlin.event;

public class DownloadRepoMessageEvent {
    public String message;

    public DownloadRepoMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
