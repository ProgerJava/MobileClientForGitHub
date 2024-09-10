package com.githubdatauser.mobileclientforgithub.components.recyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.githubdatauser.mobileclientforgithub.R;
import com.githubdatauser.mobileclientforgithub.components.pojo.RepositoryPOJO;
import com.githubdatauser.mobileclientforgithub.components.staticFunction.MyStaticFunction;

import org.kohsuke.github.GHCommitComment;
import org.kohsuke.github.PagedIterable;

import java.io.IOException;
import java.util.ArrayList;

public class RepositoryAdapterRecycler extends RecyclerView.Adapter <RepositoryAdapterRecycler.ViewHolder> {


    private final ArrayList <RepositoryPOJO> repositoryPOJOS;

    public RepositoryAdapterRecycler(ArrayList <RepositoryPOJO> repositoryPOJOS) {
        this.repositoryPOJOS = repositoryPOJOS;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_repository, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RepositoryPOJO repository = repositoryPOJOS.get(position);
        holder.repoName.setText(repository.getNameOfRepository());
        holder.authorName.setText(repository.getOwnerName());
        holder.repoDescription.setText(repository.getDescription());
        holder.forksCount.setText(String.valueOf(repository.getForksCount()));
        holder.watchesCount.setText(String.valueOf(repository.getWatchersCount()));
        MyStaticFunction.setPhotoUser(holder.progressBar, holder.photoAuthor, repository.getAvatarUrl(), holder.photoAuthor.getContext());
    }

    @Override
    public int getItemCount() {
        return repositoryPOJOS.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView repoName;
        private final TextView authorName;
        private final TextView repoDescription;
        private final TextView forksCount;
        private final TextView watchesCount;
        private final ImageView photoAuthor;
        private final ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            repoName = itemView.findViewById(R.id.repoName);
            authorName = itemView.findViewById(R.id.authorName);
            repoDescription = itemView.findViewById(R.id.repoDescription);
            forksCount = itemView.findViewById(R.id.forksCount);
            watchesCount = itemView.findViewById(R.id.watchesCount);
            photoAuthor = itemView.findViewById(R.id.photoAuthor);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }

}
