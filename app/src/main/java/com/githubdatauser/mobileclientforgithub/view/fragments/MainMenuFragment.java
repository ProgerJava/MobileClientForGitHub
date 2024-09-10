package com.githubdatauser.mobileclientforgithub.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.method.ScrollingMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.githubdatauser.mobileclientforgithub.R;
import com.githubdatauser.mobileclientforgithub.components.constants.MyConstants;
import com.githubdatauser.mobileclientforgithub.components.recyclerView.RepositoryAdapterRecycler;
import com.githubdatauser.mobileclientforgithub.databinding.FragmentMainMenuBinding;
import com.githubdatauser.mobileclientforgithub.model.MainMenuModel;
import com.githubdatauser.mobileclientforgithub.components.staticFunction.MyStaticFunction;
import com.githubdatauser.mobileclientforgithub.view.activities.MainActivity;
import com.githubdatauser.mobileclientforgithub.viewModel.MainMenuViewModel;
import com.githubdatauser.mobileclientforgithub.viewModel.factory.MainMenuFactory;

import java.util.ArrayList;
import java.util.Set;


public class MainMenuFragment extends Fragment {

    private FragmentMainMenuBinding binding;
    private SharedPreferences sharedPreferences;
    private MainActivity mainActivity;
    private Long backPressedTime = 0L;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        mainActivity = (MainActivity) getActivity();
        sharedPreferences = requireActivity().getSharedPreferences("Main", Context.MODE_PRIVATE);
        ////////////Обработка кнопки назад
        onBackPressed();
        ////////////////Создание фабрики и VM
        MainMenuViewModel viewModel = new ViewModelProvider(this, new MainMenuFactory(new MainMenuModel())).get(MainMenuViewModel.class);
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        ////////////////Подтягиваем инфу о пользователе
        setUserPersonalData();
        /////////////////Запрашиваем репозитории пользователя
        viewModel.getUserRepo(sharedPreferences.getString(MyConstants.MY_TOKEN, ""), sharedPreferences.getString(MyConstants.USER_LOGIN, ""));
        //////////////////Заполняем Recycler
        viewModel.repositories.observe(requireActivity(), repositories -> {
            binding.recycler.setAdapter(new RepositoryAdapterRecycler(repositories));
        });



        return binding.getRoot();
    }
    private void setUserPersonalData () {
        MyStaticFunction.setPhotoUser(binding.progressBar, binding.userPhoto, sharedPreferences.getString(MyConstants.USER_PHOTO, ""), requireActivity());
        binding.userLogin.setText(sharedPreferences.getString(MyConstants.USER_LOGIN, ""));
        binding.userURL.setMovementMethod(new ScrollingMovementMethod());
        binding.userURL.setHorizontallyScrolling(true);
        binding.userURL.setText(sharedPreferences.getString(MyConstants.USER_URL, ""));
        Linkify.addLinks(binding.userURL, Linkify.WEB_URLS);
    }
    private void onBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void handleOnBackPressed() {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    mainActivity.funChangeFragment(MyConstants.REGISTRATION_FRAGMENT);
                } else {
                    Toast.makeText(mainActivity, getString(R.string.pressToMoveToAuth), Toast.LENGTH_SHORT).show();
                    backPressedTime = System.currentTimeMillis();
                }
            }
        });
    }
}