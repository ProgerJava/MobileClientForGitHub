package com.githubdatauser.mobileclientforgithub.model;

import com.githubdatauser.mobileclientforgithub.components.pojo.RepositoryPOJO;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainMenuModel {

    public Map<String, GHRepository> getUserRepo (String token, String userLogin) {
        Callable task = () -> {
            GitHub gitHub = new GitHubBuilder().withOAuthToken(token).build();
            Map<String, GHRepository> repositories = gitHub.getUser(userLogin).getRepositories();
            if (repositories.isEmpty()) {
                return null;
            }else {
                return repositories;
            }
        };
        FutureTask future = new FutureTask(task);
        new Thread(future).start();
        try {
            return (Map<String, GHRepository>) future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList <RepositoryPOJO> getPojoClass (Map<String, GHRepository> repositories) {
        ArrayList <String> listWithRepoName = new ArrayList<>(repositories.keySet());
        ArrayList <RepositoryPOJO> listRepo = new ArrayList<>();
        for (String nameOfRepository : listWithRepoName) {
            GHRepository repository = repositories.get(nameOfRepository);
            try {
                listRepo.add(
                        new RepositoryPOJO(
                                nameOfRepository,
                                repository.getDescription(),
                                repository.getOwnerName(),
                                repository.getOwner().getAvatarUrl(),
                                repository.getForksCount(),
                                repository.getWatchersCount()
                        ));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return listRepo;
    }

}
