package com.githubdatauser.mobileclientforgithub.model;

import android.util.Log;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RegistrationModel {


    public GitHub startRegistration(String token) {
        Callable task = () -> {
            GitHub git = new GitHubBuilder().withOAuthToken(token).build();
            if (git.isCredentialValid()) {
                return git;
            }else {
                return null;
            }
        };
        FutureTask future = new FutureTask(task);
        new Thread(future).start();
        try {
            return (GitHub) future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
