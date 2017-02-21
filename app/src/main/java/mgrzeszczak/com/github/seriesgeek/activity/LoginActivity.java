package mgrzeszczak.com.github.seriesgeek.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import mgrzeszczak.com.github.seriesgeek.R;
import mgrzeszczak.com.github.seriesgeek.injection.Injector;
import mgrzeszczak.com.github.seriesgeek.model.ProfileData;
import mgrzeszczak.com.github.seriesgeek.service.ProfileService;
import mgrzeszczak.com.github.seriesgeek.util.PermissionVerifier;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by Maciej on 21.02.2017.
 */
public class LoginActivity extends BaseActivity {

    private CallbackManager callbackManager;
    @Inject
    ProfileService profileService;
    @BindView(R.id.login_button)
    LoginButton loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Injector.INSTANCE.getApplicationComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AccessToken.getCurrentAccessToken()!=null) {
            checkStoragePermissionsAndRun();
        } else login();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void loadSettings(Profile profile){
        profileService.loadFromFile();
        ProfileData data = profileService.get(profile.getId());
        if (data == null) {
            profileService.save(new ProfileData(profile.getId()));
        }
    }

    private void run(){
        Profile currentProfile = Profile.getCurrentProfile();
        Toast.makeText(this,"Hello "+currentProfile.getFirstName()+" "+currentProfile.getLastName(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        LoginActivity.this.startActivity(intent);
        finish();
    }

    private void checkStoragePermissionsAndRun(){
        if (PermissionVerifier.verifyStoragePermissions(this)) {
            loadSettings(Profile.getCurrentProfile());
            run();
        }
    }

    private void login(){
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ProfileTracker profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        this.stopTracking();
                    }
                };
                profileTracker.startTracking();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this,"Failed to login to facebook.",Toast.LENGTH_SHORT).show();
            }
        });
        loginButton.callOnClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermissionVerifier.REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadSettings(Profile.getCurrentProfile());
                    run();
                } else {
                    logService.log("NOT ALLOWED");
                    run();
                }
            }
        }
    }

}
