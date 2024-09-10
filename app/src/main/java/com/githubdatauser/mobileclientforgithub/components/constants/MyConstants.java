package com.githubdatauser.mobileclientforgithub.components.constants;

import android.content.Context;

import org.kohsuke.github.GitHub;

public class MyConstants {
    public static final String SPLASH_FRAGMENT = "SPLASH_FRAGMENT";
    public static final String REGISTRATION_FRAGMENT = "REGISTRATION_FRAGMENT";
    public static final String NETWORK_CONNECTED = "NETWORK_CONNECTED";
    public static final String NETWORK_NOT_CONNECTED = "NETWORK_NOT_CONNECTED";
    public static final String MY_TOKEN = "MY_TOKEN";
    public static final String MAIN_MENU_FRAGMENT = "MAIN_MENU_FRAGMENT";
    public static final String USER_PHOTO = "USER_PHOTO";
    public static final String USER_LOGIN = "USER_NICK";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_URL = "USER_URL";



    public static Context mainActivityContext;

    //////////////////////Context для сервиса
    public static void setContext (Context context) {
        mainActivityContext = context;
    }

}
