package com.cookandroid.muksaegwon;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cookandroid.muksaegwon.model.Member;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MypageActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 1000;

    TextView nameTv;
    ImageView setting,heart,review;
    private GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    private Member member;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        nameTv = (TextView)findViewById(R.id.nameTv);
        review = (ImageView) findViewById(R.id.reviewBtn);
        heart = (ImageView) findViewById(R.id.heartBtn);

        // 로그인
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            updateInfo(account.getId(),account.getDisplayName());
        }

        //찜 페이지 액티비티 열기
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (account != null) {
                    Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MypageActivity.this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //리뷰 페이지 액티비티 열기

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (account != null) {
                    Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MypageActivity.this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        nameTv.setText(name);
        findViewById(R.id.sign_in_button).setVisibility(View.INVISIBLE);

        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/userRegister.jsp?uId=" + id + "&uName=" + name +"&";
        Log.i("INFO: ", url);
        if (account != null) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("INFO:", response);
                            member = new Member(id, name);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("LOGINERROR: ",error.toString());
                        }
                    });
            requestQueue.add(stringRequest);
        } else {
            System.out.print("FAILED");
        }

        //SharedPreference
        preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userId",id);
        editor.apply();
    }
}