package com.cookandriod.muksaegwon;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    // MapFragment
    MapFragment mapFragment;
    FragmentManager fragmentManager;

    // Button
    ImageView CategoryButton, RegisterButton, MapButton, MypageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 해쉬 메소드 호출
        getAppKeyHash();

        // Google Map 프래그먼트 출력
        mapFragment = new MapFragment();
        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.Container, mapFragment, null);
        fragmentTransaction.commit();


    }

    // 해쉬값을 얻기 위한 메소드
    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key: ", something);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("name not found", e.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
};
