package com.githubdatauser.mobileclientforgithub.viewModel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.githubdatauser.mobileclientforgithub.model.MainMenuModel;
import com.githubdatauser.mobileclientforgithub.model.RegistrationModel;
import com.githubdatauser.mobileclientforgithub.viewModel.MainMenuViewModel;
import com.githubdatauser.mobileclientforgithub.viewModel.RegistrationViewModel;

public class MainMenuFactory implements ViewModelProvider.Factory {
    private MainMenuModel mainMenuModel;

    public MainMenuFactory(MainMenuModel mainMenuModel) {
        super();
        this.mainMenuModel = mainMenuModel;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainMenuViewModel(mainMenuModel);
    }
}
