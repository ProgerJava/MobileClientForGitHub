package com.githubdatauser.mobileclientforgithub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.githubdatauser.mobileclientforgithub.model.RegistrationModel;

import org.kohsuke.github.GitHub;

public class RegistrationViewModel extends ViewModel {

    private RegistrationModel registrationModel;
    public MutableLiveData <GitHub> resultGitHubConnection = new MutableLiveData<>();



    public RegistrationViewModel(RegistrationModel registrationModel) {
        this.registrationModel = registrationModel;
    }
    public void startRegistration (String token)  {
        resultGitHubConnection.setValue(registrationModel.startRegistration(token));
    }
}

