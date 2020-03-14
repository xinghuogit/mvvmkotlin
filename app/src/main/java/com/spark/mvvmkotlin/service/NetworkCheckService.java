package com.spark.mvvmkotlin.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.library.common.base.CommonBaseApplication;

/*************************************************************************************************
 * 日期：2019/12/17 14:47
 * 作者：网络状态监测服务
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class NetworkCheckService extends Service {
    private static final String TAG = "NetworkCheckService";

    public static final String CHANNEL_ID_STRING = "nyd001";

    private ConnectionChangeReceiver connectionChangeReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //适配8.0service
        NotificationManager notificationManager = (NotificationManager) CommonBaseApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID_STRING, "App", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
            Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID_STRING).build();
            startForeground(1, notification);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setNetworkCheck(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        setNetworkCheck(true);
        return START_STICKY;
    }


    private void setNetworkCheck(boolean isOpen) {
        if (isOpen) {
            if (connectionChangeReceiver == null) {
                connectionChangeReceiver = new ConnectionChangeReceiver();
                IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                registerReceiver(connectionChangeReceiver, filter);
            }
        } else {
            if (connectionChangeReceiver != null) {
                unregisterReceiver(connectionChangeReceiver);
            }
        }
    }

    public static class ConnectionChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            AppConfig.isConnected = NetworkUtils.isConnected(context);
//            LogUtils.i(TAG, "onReceive: " + AppConfig.isConnected);
        }
    }
}
