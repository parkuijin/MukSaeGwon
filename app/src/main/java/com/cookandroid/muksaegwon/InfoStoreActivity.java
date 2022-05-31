package com.cookandroid.muksaegwon;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import com.cookandroid.muksaegwon.adapter.MenuAdapter;
import com.cookandroid.muksaegwon.adapter.StoreReviewAdapter;
import com.cookandroid.muksaegwon.controller.MsgXmlParser;
import com.cookandroid.muksaegwon.model.Menu;
import com.cookandroid.muksaegwon.model.Store;
import com.cookandroid.muksaegwon.model.StoreReview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InfoStoreActivity extends AppCompatActivity{

    Long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    String getDate = ymd.format(date);

    TextView storeNameTv, storeLocationTv, openTimeStore, offTimeStore;
    CheckBox[] payWays = new CheckBox[3];
    CheckBox[] days = new CheckBox[7];
    CheckBox[] category = new CheckBox[9];
    ImageView infoStoreFinBtn;
    ImageView reviewRegBtn;
    Dialog reviewRegDialog;

    RatingBar reviewRating;
    EditText reviewContent;
    Button reviewSubmitBtn;

    RecyclerView storeReviewRecyclerView, storeMenuRecyclerView;

    StoreReviewAdapter storeReviewAdapter;
    ArrayList<StoreReview> storeReviews;

    MenuAdapter storeMenuAdapter;
    ArrayList<Menu> storeMenus;

    Store store;

    String storeId;

    private ArrayList<Boolean> payWayBooleans = new ArrayList<>();
    private ArrayList<Boolean> daysBooleans = new ArrayList<>();
    private ArrayList<Boolean> categoryBooleans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_store);

        storeId = getIntent().getStringExtra("storeId");
        Log.i("StoreId:", storeId + "");
        loadStoreInfo(storeId);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        storeMenuRecyclerView = (RecyclerView) findViewById(R.id.storeMenuRecyclerView);
        storeReviewRecyclerView = (RecyclerView) findViewById(R.id.storeReviewRecyclerView);

        storeReviews = new ArrayList<StoreReview>();
        storeReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        store = new Store();
        storeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        /*MsgXmlParser msgXmlParser = new MsgXmlParser(response);
        msgXmlParser.xmlParsingSRFM(storeReviews);
        storeReviewAdapter = new StoreReviewAdapter(storeReviews);
        storeReviewRecyclerView.setAdapter(storeReviewAdapter);*/


        reviewRating = (RatingBar) findViewById(R.id.reviewRating);
        reviewContent = (EditText) findViewById(R.id.reviewContent);
        reviewSubmitBtn = (Button) findViewById(R.id.reviewSubmitBtn);

        infoStoreFinBtn = (ImageView) findViewById(R.id.btn_back3);
        reviewRegBtn = (ImageView) findViewById(R.id.reviewRegBtn);
        storeNameTv = (TextView) findViewById(R.id.StoreNameTv);
        storeLocationTv = (TextView) findViewById(R.id.StoreLocationTv);

        days[0] = (CheckBox) findViewById(R.id.checkMonStore);
        days[1] = (CheckBox) findViewById(R.id.checkTueStore);
        days[2] = (CheckBox) findViewById(R.id.checkWedStore);
        days[3] = (CheckBox) findViewById(R.id.checkThuStore);
        days[4] = (CheckBox) findViewById(R.id.checkFriStore);
        days[5] = (CheckBox) findViewById(R.id.checkSatStore);
        days[6] = (CheckBox) findViewById(R.id.checkSunStore);

        payWays[0] = (CheckBox) findViewById(R.id.checkCashStore);
        payWays[1] = (CheckBox) findViewById(R.id.checkCreditCardStore);
        payWays[2] = (CheckBox) findViewById(R.id.checkAccountTransferStore);

        category[0] = (CheckBox) findViewById(R.id.checkCornStore);
        category[1] = (CheckBox) findViewById(R.id.checkFishStore);
        category[2] = (CheckBox) findViewById(R.id.checkTopokkiStore);
        category[3] = (CheckBox) findViewById(R.id.checkEomukStore);
        category[4] = (CheckBox) findViewById(R.id.checkSweetPotatoStore);
        category[5] = (CheckBox) findViewById(R.id.checkToastStore);
        category[6] = (CheckBox) findViewById(R.id.checkTakoyakiStore);
        category[7] = (CheckBox) findViewById(R.id.checkWaffleStore);
        category[8] = (CheckBox) findViewById(R.id.checkDakggochiStore);

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

        reviewRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewRegDialog.show();
            }
        });
    }

    public void loadStoreInfo(String storeId) {
        String url = "http://192.168.0.22:8080/MukSaeGwonServer/infoStore.jsp?storeId=" + storeId;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response: ", response);
                        MsgXmlParser msgXmlParser = new MsgXmlParser(response);
                        msgXmlParser.xmlParsingForStore(store);
                        msgXmlParser.payWayInfo(store.getPayWay(), payWayBooleans);
                        msgXmlParser.daysInfo(store.getRunDay(), daysBooleans);
                        msgXmlParser.jsonParsingMNP(store.getMenus(), storeMenus);
                        msgXmlParser.jsonParsingCTG(store.getCategory(), categoryBooleans);
                        updateUi(store);

                        storeMenuAdapter = new MenuAdapter(storeMenus);
                        storeMenuRecyclerView.setAdapter(storeMenuAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    public void updateUi(Store store) {
        storeLocationTv.setText(store.getStoreLocation());
        storeNameTv.setText(store.getStoreName());
        openTimeStore.setText(store.getOpenTime());
        payWayChecking(payWayBooleans);
        daysChecking(daysBooleans);
        categoryChecking(categoryBooleans);
        Log.i("OPENTIME: ", store.getOpenTime());
        offTimeStore.setText(store.getOffTime());
    }

    public void payWayChecking(ArrayList<Boolean> bool) {
        for (int i = 0; i < bool.size(); i++) {
            if (bool.get(i)) {
                payWays[i].setChecked(true);
            }
        }
    }

    public void daysChecking(ArrayList<Boolean> bool) {
        for (int i = 0; i < bool.size(); i++) {
            if (bool.get(i)) {
                days[i].setChecked(true);
            }
        }
    }

    public void categoryChecking(ArrayList<Boolean> bool) {
        for (int i=0; i < bool.size(); i++){
            if (bool.get(i)){
                category[i].setChecked(true);
            }
        }
    }

    public void reviewRegister() {
        String url = "";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        // 평점, 리뷰 내용이 null이 아닐 시 실행
        if(!String.valueOf(reviewRating.getRating()).equals(null) && !reviewContent.getText().toString().equals(null)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // 리뷰 등록 시 액티비티 갱신
                            finish();
                            Intent intent = getIntent();
                            startActivity(intent);
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
                    params.put("rating", String.valueOf(reviewRating.getRating()));
                    params.put("review", reviewContent.getText().toString());
                    params.put("date", getDate);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}