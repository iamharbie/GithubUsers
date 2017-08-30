package com.intrepid.githubusers;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;


import  android.content.Intent;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.intrepid.githubusers.util.AppController;
import com.intrepid.githubusers.util.CircleImageView;

import android.net.Uri;



/**
 * Created by Intrepid on 28/08/2017.
 */
public class GithubUserActivity  extends AppCompatActivity{

    public static final String USERNAME = "username";
    public static final String IMAGE_URL = "imageUrl";
    public static final String USER_URL = "userUrl";

    String userName, githubUrl;


    private CircleImageView profileImage;
    private TextView userNameTextView;
    private TextView githubUrlTextView;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_github);

      ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);




        Intent intent = getIntent();
        userName = intent.getStringExtra(USERNAME);
        githubUrl = intent.getStringExtra(USER_URL);
        String imageUrl = intent.getStringExtra(IMAGE_URL);


        actionBar.setTitle(userName + "\'s Profile");

        profileImage = (CircleImageView) findViewById(R.id.activity_user_github_profileImage);
        userNameTextView = (TextView) findViewById(R.id.activity_user_github_userName) ;
        githubUrlTextView = (TextView) findViewById(R.id.activity_user_github_githubUrl);




        profileImage.setImageUrl(imageUrl,imageLoader);
        userNameTextView.setText(userName);
        githubUrlTextView.setText(githubUrl);

        githubUrlTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage(githubUrlTextView.getText().toString());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_github_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share){

            shareText();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void shareText() {

        String mimeType = "text/plain";

        String title = "Share Profile";



        String textToShare = "Check out this awesome developer @" +  userName +", " +  githubUrl ;


        ShareCompat.IntentBuilder
                /* The from method specifies the Context from which this share is coming from */
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(textToShare)
                .startChooser();
    }


    private void openWebPage(String url) {

        Uri webpage = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
