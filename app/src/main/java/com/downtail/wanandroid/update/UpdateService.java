package com.downtail.wanandroid.update;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.downtail.wanandroid.R;
import com.downtail.wanandroid.app.App;
import com.downtail.wanandroid.core.DataManager;
import com.downtail.wanandroid.core.http.DefaultObserver;
import com.downtail.wanandroid.core.http.Error;
import com.downtail.wanandroid.core.http.ServerException;
import com.downtail.wanandroid.entity.event.ProgressEvent;
import com.downtail.wanandroid.entity.event.UnbindEvent;
import com.downtail.wanandroid.utils.UriUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class UpdateService extends Service {

    private static final String channelId = "com.zhijiesong.nursingworker.action.update";
    public static final String APK_URL = "apkUrl";
    private static final int NOTIFICATION_ID = 1001;
    private static final int INSTALLATION_ID = 1002;
    private UpdateBinder binder = new UpdateBinder();
    private NotificationManager notificationManager;
    private Notification notification;
    private RemoteViews remoteViews;

    public class UpdateBinder extends Binder {
        public UpdateService getService() {
            return UpdateService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = getResources().getString(R.string.app_name);
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(false);
            notificationChannel.enableVibration(false);
            notificationChannel.setVibrationPattern(new long[]{0});
            notificationChannel.setSound(null, null);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
            notification = createNotification();
            startForeground(NOTIFICATION_ID, notification);
        } else {
            notification = createNotification();
            startForeground(NOTIFICATION_ID, notification);
        }
        return START_STICKY;
    }

    private Notification createNotification() {
        remoteViews = new RemoteViews(getPackageName(), R.layout.layout_update_notification);
        remoteViews.setProgressBar(R.id.loading_progress, 100, 0, false);
        remoteViews.setTextViewText(R.id.tvSpeed, "正在下载");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setCustomContentView(remoteViews)
                .setChannelId(channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                .setVibrate(new long[]{0})
                .setSound(null)
                .setOngoing(true);
        return builder.build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String apkUrl = intent.getStringExtra(APK_URL);
        if (!TextUtils.isEmpty(apkUrl)) {
            downloadLatestApk(apkUrl);
        }
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        stopForeground(true);
    }

    private void downloadLatestApk(String apkUrl) {
        DataManager dataManager = App.getInstance().getAppComponent().getDataManager();
        dataManager.downloadLatestApk(apkUrl)
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody body) throws Exception {
                        File file = createFile();
                        if (file == null) {
                            throw new ServerException(Error.FILE_NOT_EXIST.getErrCode(), Error.FILE_NOT_EXIST.getErrMsg());
                        }
                        byte[] buffer = new byte[2048];
                        InputStream inputStream = null;
                        FileOutputStream fileOutputStream = null;
                        try {
                            fileOutputStream = new FileOutputStream(file);
                            inputStream = body.byteStream();
                            int length;
                            while ((length = inputStream.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, length);
                            }
                            fileOutputStream.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return file;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<File>() {
                    @Override
                    public void onSuccess(File data) {
                        Intent fileIntent = getFileIntent(data);
                        if (onFront()) {
                            notificationManager.cancel(NOTIFICATION_ID);
                            startActivity(fileIntent);
                        } else {
                            EventBus.getDefault().post(new ProgressEvent(100, "点击安装"));
                            notification.contentIntent = PendingIntent.getActivity(UpdateService.this, 0, fileIntent, 0);
                            notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONLY_ALERT_ONCE;
                            notificationManager.notify(INSTALLATION_ID, notification);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        onDoneDownload();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        onDoneDownload();
                    }
                });
    }

    private File createFile() {
        String path = getExternalCacheDir().getPath();
        File file = new File(path, System.currentTimeMillis() + ".apk");
        if (file.exists()) {
            boolean deleted = file.delete();
        }
        try {
            boolean created = file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //activity是否运行在前台
    private boolean onFront() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses != null && !runningAppProcesses.isEmpty()) {
                for (ActivityManager.RunningAppProcessInfo appProcessInfo : runningAppProcesses) {
                    if (appProcessInfo.processName.equals(getPackageName()) && appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Intent getFileIntent(File file) {
        Uri uri = UriUtil.getUriForFile(getBaseContext(), file);
        String type = getMIMEType(file);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        //下面的flags不添加，在部分手机会安装失败
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }

    public String getMIMEType(File file) {
        String type;
        String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length());
        if (suffix.equals("apk")) {
            type = "application/vnd.android.package-archive";
        } else {
            // /*如果无法直接打开，就跳出软件列表给用户选择 */
            type = "*/*";
        }
        return type;
    }

    private void onDoneDownload() {
        //下载完成发通知关闭下载服务
        EventBus.getDefault().post(new UnbindEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveProgressEvent(ProgressEvent event) {
        int value = event.getValue();
        if (value <= 100) {
            remoteViews.setProgressBar(R.id.loading_progress, 100, value, false);
            remoteViews.setTextViewText(R.id.tvSpeed, event.getMessage());
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
