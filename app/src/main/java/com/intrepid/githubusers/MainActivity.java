package com.intrepid.githubusers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.util.Log;
import android.widget.ProgressBar;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;



import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.intrepid.githubusers.model.GithubUser;
import com.intrepid.githubusers.util.AppController;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements GitHubUserAdapter.GithubUserAdapterClickHandler{

    public static final String GITHUB_URL = "https://api.github.com/search/users?q=java+location:lagos";

    private List<GithubUser> githubUsersList;
    private RecyclerView recyclerView;
    private GitHubUserAdapter gitHubUserAdapter;
    private TextView errorMessageTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing views
        recyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerView);
        gitHubUserAdapter = new GitHubUserAdapter(this);
        errorMessageTextView = (TextView) findViewById(R.id.activity_main_error_message);
        progressBar = (ProgressBar) findViewById(R.id.activity_main_progressBar);

        githubUsersList = new ArrayList<>();



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(gitHubUserAdapter);

        progressBar.setVisibility(View.VISIBLE);


        sendJsonRequest(GITHUB_URL);

    }

    public void sendJsonRequest(String url){

        showGithubUsers();
        Log.d(this.getClass().getSimpleName(),"sendJsonMethod called ...");
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d(this.getClass().getSimpleName(),response.toString());
                progressBar.setVisibility(View.INVISIBLE);
                parseJson(response);


                gitHubUserAdapter.setgithubUserList(githubUsersList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(MainActivity.class.getSimpleName(),error.toString());
                progressBar.setVisibility(View.INVISIBLE);
                showErrorMessage();
            }
        }) ;

      AppController.getInstance().addToRequestQueue(request);


    }

    private void showGithubUsers() {
        errorMessageTextView.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        recyclerView.setVisibility(View.INVISIBLE);
        errorMessageTextView.setVisibility(View.VISIBLE);
    }

    private void parseJson(JSONObject response) {
        if (response.has("items")){

            try {
                JSONArray githubUsersArray = response.getJSONArray("items");
                for (int i = 0; i < githubUsersArray.length(); i++) {
                    JSONObject currentGithubUser = githubUsersArray.getJSONObject(i);
                    String githubUserUsername = currentGithubUser.getString("login");
                    String githubUserImageUrl = currentGithubUser.getString("avatar_url");
                    String githubUserUrl = currentGithubUser.getString("html_url");

                    GithubUser githubUser = new GithubUser();
                    githubUser.setgithubUrl(githubUserUrl);
                    githubUser.setGithubUsername(githubUserUsername);
                    githubUser.setProfileImageUrl(githubUserImageUrl);


                    this.githubUsersList.add(githubUser);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    public void onGithubUserClickHandler(GithubUser githubUser) {
        Intent intent = new  Intent(this,GithubUserActivity.class);

        intent.putExtra(GithubUserActivity.USERNAME, githubUser.getGithubUsername());
        intent.putExtra(GithubUserActivity.IMAGE_URL, githubUser.getProfileImageUrl());
        intent.putExtra(GithubUserActivity.USER_URL,githubUser.getgithubUrl());



        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_refresh){
            sendJsonRequest(GITHUB_URL);
        }

        return super.onOptionsItemSelected(item);
    }
}
