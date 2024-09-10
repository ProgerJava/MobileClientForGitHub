package com.githubdatauser.mobileclientforgithub.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.githubdatauser.mobileclientforgithub.R;
import com.githubdatauser.mobileclientforgithub.components.constants.MyConstants;
import com.githubdatauser.mobileclientforgithub.databinding.FragmentRegistrationBinding;
import com.githubdatauser.mobileclientforgithub.model.RegistrationModel;
import com.githubdatauser.mobileclientforgithub.components.staticFunction.MyStaticFunction;
import com.githubdatauser.mobileclientforgithub.view.activities.MainActivity;
import com.githubdatauser.mobileclientforgithub.viewModel.RegistrationViewModel;
import com.githubdatauser.mobileclientforgithub.viewModel.factory.RegistrationFactory;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;


public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;
    private Long backPressedTime = 0L;
    private MainActivity mainActivity;
    private RegistrationViewModel viewModel;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor shPrEditor;

    private String token;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        mainActivity = (MainActivity) getActivity();
        onBackPressed();
        /////////////////Подключаем SP для сохранения пользовательского токена входа
        sharedPreferences = mainActivity.getSharedPreferences("Main", Context.MODE_PRIVATE);
        shPrEditor = sharedPreferences.edit();
        ////////////////Создание фабрики и VM
        viewModel = new ViewModelProvider(this, new RegistrationFactory(new RegistrationModel())).get(RegistrationViewModel.class);
        //////////////////Устанавливаем тип техстовому полю - Password
        binding.personalToken.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ////////////////////Если в SP сохранились данные пользователя, подтягиваем их в поле
        if (!sharedPreferences.getString(MyConstants.MY_TOKEN, "").isEmpty()) {
            binding.personalToken.setText(sharedPreferences.getString(MyConstants.MY_TOKEN, ""));
        }
        if (!sharedPreferences.getString(MyConstants.USER_PHOTO, "").isEmpty()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            MyStaticFunction.setPhotoUser(binding.progressBar, binding.mainPicture, sharedPreferences.getString(MyConstants.USER_PHOTO, ""), mainActivity);
        }
        ////////////////////Visibility token
        binding.codeVisibility.setOnClickListener (view -> {
            if (binding.codeVisibility.getTag().equals(getString(R.string.tagEyeInvisible))) {
                binding.codeVisibility.setTag(getString(R.string.tagEyeVisible));
                binding.codeVisibility.setImageDrawable(mainActivity.getDrawable(R.drawable.open_eye));
                binding.personalToken.setInputType(InputType.TYPE_CLASS_TEXT);
            }else {
                binding.codeVisibility.setTag(getString(R.string.tagEyeInvisible));
                binding.codeVisibility.setImageDrawable(mainActivity.getDrawable(R.drawable.close_eye));
                binding.personalToken.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            binding.personalToken.setSelection(binding.personalToken.getText().length());
        });

        binding.buttonEnter.setOnClickListener(view -> {
            token = binding.personalToken.getText().toString().trim();
            if (token.isEmpty()) {
                Toast.makeText(mainActivity, mainActivity.getString(R.string.firstAddToken), Toast.LENGTH_SHORT).show();
            }else {
                viewModel.startRegistration(token);
            }
        });
        //////////////////////observer git
        viewModel.resultGitHubConnection.observe(requireActivity(), git -> {
            if (git != null) {
                //////////////Saving token
                shPrEditor.putString(MyConstants.MY_TOKEN, token).commit();
                try {
                    ////////////////////Сохраняем фото, логин, имя, URL
                    shPrEditor.putString(MyConstants.USER_PHOTO, git.getMyself().getAvatarUrl()).commit();
                    shPrEditor.putString(MyConstants.USER_LOGIN, git.getMyself().getLogin()).commit();
                    shPrEditor.putString(MyConstants.USER_NAME, git.getMyself().getName()).commit();
                    shPrEditor.putString(MyConstants.USER_URL, git.getMyself().getHtmlUrl() + "").commit();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ///////////////Переходим на главное активити
                binding.progressBarGoToMain.setVisibility(View.VISIBLE);
                mainActivity.funChangeFragment(MyConstants.MAIN_MENU_FRAGMENT);
            }else {
                Toast.makeText(getActivity(), mainActivity.getString(R.string.failureConnectionToGit), Toast.LENGTH_SHORT).show();
            }
        });
        ////////////////Забываем пользователя
        binding.removeAccount.setOnClickListener(view -> {
            AtomicBoolean flagDelete = new AtomicBoolean(true);
            if (!sharedPreferences.getString(MyConstants.MY_TOKEN, "").isEmpty()) {
                Snackbar snackbar = Snackbar.make(binding.constraint,
                        getString(R.string.deleteAccount)
                                + " "
                                + sharedPreferences.getString(MyConstants.USER_LOGIN, ""), Snackbar.LENGTH_LONG);
                snackbar.setAction(getString(R.string.cancel), v -> {
                    flagDelete.set(false);
                });
                snackbar.show();
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        if (flagDelete.get()) {
                            removeUser();
                        }
                    }
                });
            }
        });

        return binding.getRoot();
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void removeUser () {
        shPrEditor.clear().commit();
        binding.personalToken.getText().clear();
        binding.mainPicture.setImageDrawable(mainActivity.getDrawable(R.drawable.git_hub_logo));
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
}