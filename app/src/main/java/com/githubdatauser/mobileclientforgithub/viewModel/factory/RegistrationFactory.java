package com.githubdatauser.mobileclientforgithub.viewModel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.githubdatauser.mobileclientforgithub.model.RegistrationModel;
import com.githubdatauser.mobileclientforgithub.viewModel.RegistrationViewModel;

public class RegistrationFactory implements ViewModelProvider.Factory {
    private RegistrationModel registrationModel;

    public RegistrationFactory(RegistrationModel registrationModel) {
        super();
        this.registrationModel = registrationModel;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RegistrationViewModel(registrationModel);
    }
}
