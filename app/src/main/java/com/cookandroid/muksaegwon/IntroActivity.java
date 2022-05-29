package com.cookandroid.muksaegwon;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

public class IntroActivity extends AppCompatActivity{
    public static Activity activity;
    ImageView image;
    private long backPressedTime = 0;
    private final long FINISH_INTERVAL_TIME = 2000;

    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private ActivityResultContracts.RequestMultiplePermissions multiplePermissionsContract = new ActivityResultContracts.RequestMultiplePermissions();
    private ActivityResultLauncher<String[]> multiplePermissionLauncher = registerForActivityResult(multiplePermissionsContract, isGranted -> {
        Log.d("PERMISSIONS", "Launcher result: " + isGranted.toString());
        if (isGranted.containsValue(false)) {
            Log.d("PERMISSIONS", "At least one of the permissions was not granted, launching again...");
            Toast.makeText(getApplicationContext(),"위치 권한이 필요합니다",Toast.LENGTH_LONG).show();
        } else {
            setResult(Activity.RESULT_OK);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        image=findViewById(R.id.imageView);
        Glide.with(this).load(R.raw.intro_gif).into(image);

        activity = this;

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        if(!initCheckPermission()){
            askPermissions(multiplePermissionLauncher);
        }
    }

    private void askPermissions(ActivityResultLauncher<String[]> multiplePermissionLauncher) {
        if (!hasPermissions(REQUIRED_PERMISSIONS)) {
            Log.d("PERMISSIONS", "Launching multiple contract permission launcher for ALL required permissions");
            multiplePermissionLauncher.launch(REQUIRED_PERMISSIONS);
        } else {
            Log.d("PERMISSIONS", "All permissions are already granted");
        }
    }



    private boolean hasPermissions(String[] permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("PERMISSIONS", "Permission is not granted: " + permission);
                    return false;
                }
                Log.d("PERMISSIONS", "Permission already granted: " + permission);
            }
            return true;
        }
        return false;
    }

    public boolean initCheckPermission(){
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        return true;
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