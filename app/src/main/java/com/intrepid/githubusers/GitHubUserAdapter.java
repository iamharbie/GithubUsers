package com.intrepid.githubusers;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.intrepid.githubusers.model.GithubUser;
import com.intrepid.githubusers.util.AppController;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;


/**
 * Created by Intrepid on 28/08/2017.
 */

public class GitHubUserAdapter extends RecyclerView.Adapter<GitHubUserAdapter.GithubUserViewHolder>{
    private List<GithubUser> githubUserList;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private GithubUserAdapterClickHandler githubUserAdapterClickHandler;

    public GitHubUserAdapter(GithubUserAdapterClickHandler githubUserAdapterClickHandler) {
        this.githubUserAdapterClickHandler = githubUserAdapterClickHandler;
    }

    @Override
    public GithubUserViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.github_user_list_item,viewGroup,false);

        return new GithubUserViewHolder(view);
    }



    @Override
    public void onBindViewHolder(GitHubUserAdapter.GithubUserViewHolder holder, int position) {
        GithubUser githubUser = githubUserList.get(position);
        holder.userNameTextView.setText(githubUser.getGithubUsername());
        holder.githubUserProfileImage.setImageUrl(githubUser.getProfileImageUrl(),imageLoader);
        //holder.numberOfUsers.setText(String.valueOf(githubUserList.size()));

    }

    @Override
    public int getItemCount() {
        if(githubUserList == null){
            return 0;
        }

        return githubUserList.size();
    }

    public class GithubUserViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        NetworkImageView githubUserProfileImage;
        TextView userNameTextView;
        //TextView numberOfUsers;

        public GithubUserViewHolder(View itemView)
        {
            super(itemView);
           // numberOfUsers  = (TextView) itemView.findViewById(R.id.number_of_users);
            githubUserProfileImage = (NetworkImageView) itemView.findViewById(R.id.github_user_profile_image);
            userNameTextView = (TextView) itemView.findViewById(R.id.github_user_userName);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            GithubUser githubUser = githubUserList.get(getAdapterPosition());
            githubUserAdapterClickHandler.onGithubUserClickHandler(githubUser);
        }
    }



    /**
     *Method to set githubUserlist.
     * Not using a constructor to set it cos we may need to change it wiothout creating a new
     * GitHubUserAdapter
     *
     */
    public void setgithubUserList(List<GithubUser> githubUserList){
        this.githubUserList = githubUserList;
        notifyDataSetChanged();
    }




    interface GithubUserAdapterClickHandler{
        void onGithubUserClickHandler(GithubUser githubUser);
    }
}
