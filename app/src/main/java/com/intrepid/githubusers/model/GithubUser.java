package com.intrepid.githubusers.model;



public class GithubUser {
        private String githubUsername, profileImageUrl,githubUrl;

    public GithubUser(String githubUsername, String profileImageUrl, String githubUrl) {
        this.githubUsername = githubUsername;
        this.profileImageUrl = profileImageUrl;
        this.githubUrl = githubUrl;




    }

    public GithubUser(){

    }


    public String getGithubUsername() {
        return githubUsername;
    }

    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getgithubUrl() {
        return githubUrl;
    }

    public void setgithubUrl(String getgithubUrl) {
        this.githubUrl = getgithubUrl;
    }
}
