package com.example.wagh.jsontest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tv1;
    String s1,s2,s3;
    String s4="";

    // Facebook Variables**********************************************************************

    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;

    private FacebookCallback<LoginResult> callback=new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            Profile profile=Profile.getCurrentProfile();
            nextActivity(profile);

        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };


    //end of facebook variables part**********************************************************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv1=(TextView)findViewById(R.id.tv1);

        //Facebook part 2**********************************************************************

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();


        LoginButton loginButton=(LoginButton)findViewById(R.id.login);

        callback=new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken=loginResult.getAccessToken();
                Profile profile=Profile.getCurrentProfile();
                nextActivity(profile);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        };

        loginButton.setReadPermissions("user_friends");

        loginButton.registerCallback(callbackManager, callback);


        //end of facebook part 2**********************************************************************

        JSONObject hello=new JSONObject();
        try {
            hello.put("Name","viraj");
            hello.put("Time","3.07");
            hello.put("Test","test");


            s1=hello.getString("Name");
            s2=hello.getString("Time");
            s3=hello.getString("Test");

            s4+=s1;
            s4+="\n";
            s4+=s2;
            s4+="\n";
            s4+=s3;


                    tv1.setText(s4);


        } catch (JSONException e) {
            e.printStackTrace();
        }




    }

    //part 3 starts Facebook**********************************************************************

    protected void onResume() {
        super.onResume();
        //Facebook login**********************************************************************
        Profile profile = Profile.getCurrentProfile();
        nextActivity(profile);
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        callbackManager.onActivityResult(requestCode, responseCode, intent);

    }

    private void nextActivity(Profile profile){
        if(profile != null){
            Intent main = new Intent(MainActivity.this, MainActivity.class);
            main.putExtra("name", profile.getFirstName());
            main.putExtra("surname", profile.getLastName());
            main.putExtra("imageUrl", profile.getProfilePictureUri(200,200).toString());
            startActivity(main);
        }
    }
}
