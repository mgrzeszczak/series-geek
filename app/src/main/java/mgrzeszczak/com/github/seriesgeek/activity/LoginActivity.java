package mgrzeszczak.com.github.seriesgeek.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import mgrzeszczak.com.github.seriesgeek.R;
import mgrzeszczak.com.github.seriesgeek.injection.Injector;
import mgrzeszczak.com.github.seriesgeek.model.ProfileData;
import mgrzeszczak.com.github.seriesgeek.service.ProfileService;
import mgrzeszczak.com.github.seriesgeek.util.PermissionVerifier;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import javax.inject.Inject;

/**
 * Created by Maciej on 21.02.2017.
 */
public class LoginActivity extends BaseActivity {

    CallbackManager callbackManager;

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
        PermissionVerifier.verifyStoragePermissions(this);

        if (Profile.getCurrentProfile()!=null){
            Profile currentProfile = Profile.getCurrentProfile();
            //profileService.loadFromFile();
            //ProfileData data = profileService.get(currentProfile.getId());
            //if (data == null) {
            //    profileService.save(new ProfileData(currentProfile.getId()));
            //}
            logService.log("Hello "+currentProfile.getFirstName()+" "+currentProfile.getLastName());
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            LoginActivity.this.startActivity(intent);
        } else
            init();
    }

    private void init(){
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ProfileTracker profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        this.stopTracking();
                        logService.log(currentProfile.getFirstName()+" "+currentProfile.getLastName());
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                    }
                };
                profileTracker.startTracking();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
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




                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    logService.log("NOT ALLOWED");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

}
