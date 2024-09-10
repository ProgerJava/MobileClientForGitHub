package com.githubdatauser.mobileclientforgithub.service;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import androidx.annotation.Nullable;


public class ServiceNetwork extends Service {

    private Thread thread;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        thread = new Thread(new CheckConnection(connectivityManager));
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (thread.isInterrupted()) {
            thread.interrupt();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {return null;}


}
