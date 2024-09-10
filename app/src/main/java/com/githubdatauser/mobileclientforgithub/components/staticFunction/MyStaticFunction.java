package com.githubdatauser.mobileclientforgithub.components.staticFunction;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.githubdatauser.mobileclientforgithub.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MyStaticFunction {


    ///////////////////установка фото пользователя
    public static void setPhotoUser (
            View progressBar,
            ImageView mainPicture,
            String avatarUrl,
            Context context) {

        progressBar.setVisibility(View.VISIBLE);
        Picasso.get().load(avatarUrl).into(mainPicture, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(context, context.getString(R.string.failedDownloadsPictures), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
