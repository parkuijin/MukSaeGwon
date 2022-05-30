package com.cookandroid.muksaegwon;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cookandroid.muksaegwon.adapter.StoreReviewAdapter;
import com.cookandroid.muksaegwon.model.StoreReview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfoStoreActivity extends AppCompatActivity {

    TextView storeNameTv, storeLocationTv, openTimeStore, offTimeStore;
    CheckBox cashStore, creditCardStore, accountStore, monStore, tueStore, wedStore, thuStore, friStore, satStore, sunStore;
    CheckBox cornStore, fishStore, topokkiStore, eomukStore, sweetpotatoStore, toastStore, takoyakiStore, waffleStore, dakggochiStore;
    ImageView infoStoreFinBtn;
    Button reviewRegBtn;
    Dialog reviewRegDialog;

    RatingBar reviewRating;
    EditText reviewContent;
    Button reviewSubmitBtn;

    RecyclerView storeReviewRecyclerView, storeMenuRecyclerView;
    StoreReviewAdapter storeReviewAdapter;
    ArrayList<StoreReview> storeReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_store);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        storeMenuRecyclerView = (RecyclerView) findViewById(R.id.storeMenuRecyclerView);
        storeReviewRecyclerView = (RecyclerView) findViewById(R.id.storeReviewRecyclerView);

        storeReviews = new ArrayList<StoreReview>();
        storeReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeReviewAdapter = new StoreReviewAdapter(storeReviews);
        storeReviewRecyclerView.setAdapter(storeReviewAdapter);

        reviewRating = (RatingBar) findViewById(R.id.reviewRating);
        reviewContent = (EditText) findViewById(R.id.reviewContent);
        reviewSubmitBtn = (Button) findViewById(R.id.reviewSubmitBtn);

        infoStoreFinBtn = (ImageView) findViewById(R.id.infoStoreFinBtn);
        reviewRegBtn = (Button) findViewById(R.id.reviewRegBtn);
        storeNameTv = (TextView) findViewById(R.id.StoreNameTv);
        storeLocationTv = (TextView) findViewById(R.id.StoreLocationTv);

        monStore = (CheckBox) findViewById(R.id.checkMonStore);
        tueStore = (CheckBox) findViewById(R.id.checkTueStore);
        wedStore = (CheckBox) findViewById(R.id.checkWedStore);
        thuStore = (CheckBox) findViewById(R.id.checkThuStore);
        friStore = (CheckBox) findViewById(R.id.checkFriStore);
        satStore = (CheckBox) findViewById(R.id.checkSatStore);
        sunStore = (CheckBox) findViewById(R.id.checkSunStore);

        cashStore = (CheckBox) findViewById(R.id.checkCashStore);
        creditCardStore = (CheckBox) findViewById(R.id.checkCreditCardStore);
        accountStore = (CheckBox) findViewById(R.id.checkAccountTransferStore);

        cornStore = (CheckBox) findViewById(R.id.checkCornStore);
        fishStore = (CheckBox) findViewById(R.id.checkFishStore);
        topokkiStore = (CheckBox) findViewById(R.id.checkTopokkiStore);
        eomukStore = (CheckBox) findViewById(R.id.checkEomukStore);
        sweetpotatoStore = (CheckBox) findViewById(R.id.checkSweetPotatoStore);
        toastStore = (CheckBox) findViewById(R.id.checkToastStore);
        takoyakiStore = (CheckBox) findViewById(R.id.checkTakoyakiStore);
        waffleStore = (CheckBox) findViewById(R.id.checkWaffleStore);
        dakggochiStore = (CheckBox) findViewById(R.id.checkDakggochiStore);

        openTimeStore = (TextView) findViewById(R.id.openTimeTvStore);
        offTimeStore = (TextView) findViewById(R.id.offTimeTvStore);

        reviewRegDialog = new Dialog(InfoStoreActivity.this);
        reviewRegDialog.setContentView(R.layout.dialog_review_register);
        reviewRegDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 현재 가게 정보 액티비티 종료 (뒤로가기)
        infoStoreFinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String url = "";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("rating",String.valueOf(reviewRating.getRating()));
                params.put("review",reviewContent.getText().toString());

                return params;
            }
        };
        requestQueue.add(stringRequest);

        reviewRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewRegDialog.show();
            }
        });

    }
}