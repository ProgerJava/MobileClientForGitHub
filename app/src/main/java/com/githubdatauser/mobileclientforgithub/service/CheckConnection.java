package com.githubdatauser.mobileclientforgithub.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.githubdatauser.mobileclientforgithub.R;
import com.githubdatauser.mobileclientforgithub.components.constants.MyConstants;


public class CheckConnection implements Runnable {

    Context context = MyConstants.mainActivityContext;
    ConnectivityManager connectivityManager;
    //Handler handler;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.obj.toString().equals(MyConstants.NETWORK_NOT_CONNECTED)) {
                Toast.makeText(context, context.getString(R.string.networkNotConnected), Toast.LENGTH_SHORT).show();
            }
        }
    };

    public CheckConnection(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    ////////////////////////Every 3 second update Network Status
    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Message massage = new Message();
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
                massage.obj = MyConstants.NETWORK_CONNECTED;
            } else {
                massage.obj = MyConstants.NETWORK_NOT_CONNECTED;
            }
            handler.sendMessage(massage);
        }
    }
}