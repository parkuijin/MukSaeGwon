package com.cookandroid.muksaegwon;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class IntroActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback,View.OnClickListener{
    private LoginCallback loginCallback;

    public interface LoginCallback{
        void logined(boolean locationBool);
    }
    public void setLoginCallback(LoginCallback loginCallback){
        this.loginCallback = loginCallback;
    }

    public static Activity activity;

    ImageView image;
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;

    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    // LOGIN PART
    private static final int RC_SIGN_IN = 1000;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    SharedPreferences Preferences;
    SignInButton signInButton;
    boolean locationPermission;

    SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        image=findViewById(R.id.imageView);
        Glide.with(this).load(R.raw.intro_gif).into(image);

        activity = this;

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        spinKitView = (SpinKitView)findViewById(R.id.spin_kit);

        //LOGIN
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            signIn();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            Log.d(TAG, "Account received");
            updateInfo(account.getId(), account.getDisplayName());
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }



    private void updateInfo(String id, String name) {
        signInButton.setVisibility(View.INVISIBLE);
        spinKitView.setVisibility(View.VISIBLE);

        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/idCheck.jsp?uId=" + id + "&uName=" + name;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("INFO:", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);

        //SharedPreference
        Preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = Preferences.edit();
        editor.putString("userId",id);
        editor.apply();

        permissionCheck();
    }

    public void LogOn(boolean b){
        loginCallback.logined(b);
    }

    public void permissionCheck(){
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getApplicationContext(), REQUIRED_PERMISSIONS[0]);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getApplicationContext(), REQUIRED_PERMISSIONS[1]);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED){
            locationPermission = true;
            ((MapFragment)MapFragment.mapFragment).startLogin(locationPermission);
        } else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,REQUIRED_PERMISSIONS[0])){
                ActivityCompat.requestPermissions(this,REQUIRED_PERMISSIONS,200);
            } else {
                ActivityCompat.requestPermissions(this,REQUIRED_PERMISSIONS,200);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200 && grantResults.length == REQUIRED_PERMISSIONS.length){
            locationPermission = true;
            for (int result : grantResults){
                if(result != PackageManager.PERMISSION_GRANTED){
                    locationPermission = false;
                    break;
                }
            }
            ((MapFragment)MapFragment.mapFragment).startLogin(locationPermission);
        }
    }


    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            MainActivity mainActivity = (MainActivity) MainActivity.activity;
            mainActivity.finish();
            finish();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }


}