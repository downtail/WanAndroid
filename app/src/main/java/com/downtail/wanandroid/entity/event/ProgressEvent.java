package com.downtail.wanandroid.entity.event;

/**
 * NursingWorker
 * Created by downtail on 2020/9/11
 */
public class ProgressEvent {

    private int value;
    private String message;

    public ProgressEvent(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
