package com.githubdatauser.mobileclientforgithub.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import com.githubdatauser.mobileclientforgithub.R;
import com.githubdatauser.mobileclientforgithub.components.constants.MyConstants;
import com.githubdatauser.mobileclientforgithub.databinding.ActivityMainBinding;
import com.githubdatauser.mobileclientforgithub.service.ServiceNetwork;
import com.githubdatauser.mobileclientforgithub.view.fragments.MainMenuFragment;
import com.githubdatauser.mobileclientforgithub.view.fragments.RegistrationFragment;
import com.githubdatauser.mobileclientforgithub.view.fragments.SplashFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //////////////viewBinding
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MyConstants.setContext(this);
        //////////////////Start service Network
        startService(new Intent(this, ServiceNetwork.class));
        ///////////////////////AddFirstFragment
        funChangeFragment(MyConstants.SPLASH_FRAGMENT);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, ServiceNetwork.class));
    }

    @SuppressLint("CommitTransaction")
    public void funChangeFragment (String fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (fragment) {
            case (MyConstants.SPLASH_FRAGMENT):
                fragmentTransaction.add(R.id.frameLayout, new SplashFragment()).commit();
                break;
            case (MyConstants.REGISTRATION_FRAGMENT):
                fragmentTransaction.replace(R.id.frameLayout, new RegistrationFragment()).commit();
                break;
            case (MyConstants.MAIN_MENU_FRAGMENT):
                fragmentTransaction.replace(R.id.frameLayout, new MainMenuFragment()).commit();
                break;
        }

    }
}