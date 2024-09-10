package com.githubdatauser.mobileclientforgithub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.githubdatauser.mobileclientforgithub.components.pojo.RepositoryPOJO;
import com.githubdatauser.mobileclientforgithub.model.MainMenuModel;

import org.kohsuke.github.GHRepository;

import java.util.ArrayList;
import java.util.Map;

public class MainMenuViewModel extends ViewModel {

    private MainMenuModel mainMenuModel;
    public MutableLiveData <ArrayList <RepositoryPOJO>> repositories = new MutableLiveData<>();

    public MainMenuViewModel(MainMenuModel mainMenuModel) {
        this.mainMenuModel = mainMenuModel;
    }

    public void getUserRepo (String token, String login) {
        repositories.setValue(mainMenuModel.getPojoClass(mainMenuModel.getUserRepo(token, login)));
    }


}
