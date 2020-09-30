package com.downtail.wanandroid.entity;

/**
 * NursingWorker
 * Created by downtail on 2020/9/3
 */
public class LogoutEvent {

    private String message;

    public LogoutEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
