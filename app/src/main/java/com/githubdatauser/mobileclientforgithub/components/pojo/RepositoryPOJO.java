package com.githubdatauser.mobileclientforgithub.components.pojo;


import org.kohsuke.github.GHCommitComment;
import org.kohsuke.github.PagedIterable;

import java.util.Objects;

public class RepositoryPOJO {

    private final String nameOfRepository;
    private final String description;
    private final String ownerName;
    private final String avatarUrl;
    private final int forksCount;
    private final int watchersCount;

    public RepositoryPOJO(String nameOfRepository, String description, String ownerName, String avatarUrl, int forksCount, int watchersCount) {
        this.nameOfRepository = nameOfRepository;
        this.description = description;
        this.ownerName = ownerName;
        this.avatarUrl = avatarUrl;
        this.forksCount = forksCount;
        this.watchersCount = watchersCount;
    }

    public String getNameOfRepository() {
        return checkNonNull(nameOfRepository);
    }

    public String getDescription() {
        return checkNonNull(description);
    }

    public String getOwnerName() {
        return checkNonNull(ownerName);
    }
    public String getAvatarUrl() {
        return checkNonNull(avatarUrl);
    }
    public int getForksCount() {
        return forksCount;
    }
    public int getWatchersCount() {
        return watchersCount;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryPOJO that = (RepositoryPOJO) o;
        return forksCount == that.forksCount && watchersCount == that.watchersCount && Objects.equals(nameOfRepository, that.nameOfRepository) && Objects.equals(description, that.description) && Objects.equals(ownerName, that.ownerName) && Objects.equals(avatarUrl, that.avatarUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfRepository, description, ownerName, avatarUrl, forksCount, watchersCount);
    }
    private String checkNonNull (String str) {
        if (str == null) {
            return "";
        }else {
            return str;
        }
    }
}
