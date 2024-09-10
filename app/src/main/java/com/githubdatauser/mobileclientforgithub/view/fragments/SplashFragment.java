package com.githubdatauser.mobileclientforgithub.view.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.githubdatauser.mobileclientforgithub.R;
import com.githubdatauser.mobileclientforgithub.components.constants.MyConstants;
import com.githubdatauser.mobileclientforgithub.databinding.FragmentSplashBinding;
import com.githubdatauser.mobileclientforgithub.view.activities.MainActivity;


public class SplashFragment extends Fragment {

    private FragmentSplashBinding binding;
    private CountDownTimer timer;
    private MainActivity mainActivity;
    private Long backPressedTime = 0L;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        //////////////Анимация загрузки пользовательского интерфейса
        startTimer();
        mainActivity = (MainActivity) getActivity();
        ////////////////Кнопка назад
        onBackPressed();


        return binding.getRoot();
    }

    private void onBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void handleOnBackPressed() {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    mainActivity.finish();
                } else {
                    Toast.makeText(mainActivity, getString(R.string.pressToExit), Toast.LENGTH_SHORT).show();
                    backPressedTime = System.currentTimeMillis();
                }
            }
        });
    }
    private void startTimer() {
        timer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                mainActivity.funChangeFragment(MyConstants.REGISTRATION_FRAGMENT);
                timer.cancel();
            }
        }.start();
    }

}