package com.downtail.wanandroid.core.http.interceptor;

/**
 * NursingWorker
 * Created by downtail on 2020/9/9
 */
public interface ProgressListener {

    void update(long bytesRead, long contentLength, boolean done);
}
