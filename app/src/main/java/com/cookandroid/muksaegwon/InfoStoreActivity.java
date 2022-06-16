package com.cookandroid.muksaegwon;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
import com.cookandroid.muksaegwon.model.StoreSerializable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InfoStoreActivity extends AppCompatActivity {

    public static InfoStoreActivity infoStoreActivity;

    Long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    String getDate = ymd.format(date);

    Store store;

    TextView storeNameTv, storeLocationTv, openTimeStore, offTimeStore, modifyBtn;
    CheckBox[] payWays = new CheckBox[3];
    CheckBox[] days = new CheckBox[7];
    CheckBox[] categorys = new CheckBox[9];
    CheckBox favoriteBtn;
    Switch isRunningSwitch;

    ImageView infoStoreFinBtn;
    ImageView reviewRegBtn;
    Dialog reviewRegDialog;

    RatingBar reviewRating;
    EditText reviewContent;
    Button reviewSubmitBtn;

    RecyclerView storeReviewRecyclerView, storeMenuRecyclerView;
    StoreReviewAdapter storeReviewAdapter;
    ArrayList<StoreReview> storeReviews;

    String storeId;
    private ArrayList<Boolean> payWayBooleans;
    private ArrayList<Boolean> daysBooleans;
    private ArrayList<Menu> menuList;
    private MenuAdapter menuAdapter;

    private ArrayList<Boolean> ctgBooleans;

    SharedPreferences preferences;
    String userId;

    StoreSerializable storeSerializable = new StoreSerializable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_store);

        infoStoreActivity = InfoStoreActivity.this;

        preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = preferences.getString("userId","");

        storeId = getIntent().getStringExtra("storeId");

        loadStoreInfo(storeId);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        favoriteBtn = (CheckBox) findViewById(R.id.favoriteBtn);
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ((CheckBox)v).isChecked();
                if(isChecked){
                    likeThisStore(true,userId,store.getStoreId());
                } else
                    likeThisStore(false,userId,store.getStoreId());
            }
        });

        isRunningSwitch = (Switch)findViewById(R.id.isRunningSwitch);
        isRunningSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOn = ((Switch) v).isChecked();
                if(isOn){
                    isRunningSwitch.setText("영업 중");
                    storeRunCheck((byte) 1,store.getStoreId());
                } else
                    isRunningSwitch.setText("영업 종료");
                    storeRunCheck((byte) 0,store.getStoreId());
            }
        });

        storeMenuRecyclerView = (RecyclerView) findViewById(R.id.storeMenuRecyclerView);
        storeReviewRecyclerView = (RecyclerView) findViewById(R.id.storeReviewRecyclerView);

        storeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        modifyBtn = (TextView) findViewById(R.id.modifyBtn);
        infoStoreFinBtn = (ImageView) findViewById(R.id.btn_back5);
        reviewRegBtn = (ImageView) findViewById(R.id.reviewRegBtn);
        storeNameTv = (TextView) findViewById(R.id.storeNameTv);
        storeLocationTv = (TextView) findViewById(R.id.StoreNameUpdateTv);

        days[0] = (CheckBox) findViewById(R.id.checkMon);
        days[1] = (CheckBox) findViewById(R.id.checkTueUpdate);
        days[2]= (CheckBox) findViewById(R.id.checkWedUpdate);
        days[3]= (CheckBox) findViewById(R.id.checkThuUpdate);
        days[4]= (CheckBox) findViewById(R.id.checkFriUpdate);
        days[5]= (CheckBox) findViewById(R.id.checkSatUpdate);
        days[6]= (CheckBox) findViewById(R.id.checkSunUpdate);

        payWays[0] = (CheckBox) findViewById(R.id.checkCashUpdate);
        payWays[1] = (CheckBox) findViewById(R.id.checkCreditCardUpdate);
        payWays[2] = (CheckBox) findViewById(R.id.checkAccountTransferUpdate);

        categorys[0] = (CheckBox) findViewById(R.id.checkCornUpdate);
        categorys[1] = (CheckBox) findViewById(R.id.checkFishUpdate);
        categorys[2] = (CheckBox) findViewById(R.id.checkTopokkiUpdate);
        categorys[3] = (CheckBox) findViewById(R.id.checkEomukUpdate);
        categorys[4] = (CheckBox) findViewById(R.id.checkSweetPotatoUpdate);
        categorys[5]= (CheckBox) findViewById(R.id.checkToastUpdate);
        categorys[6] = (CheckBox) findViewById(R.id.checkTakoyakiUpdate);
        categorys[7] = (CheckBox) findViewById(R.id.checkWaffleUpdate);
        categorys[8] = (CheckBox) findViewById(R.id.checkDakggochi);

        openTimeStore = (TextView) findViewById(R.id.openTimeTvUpdate);
        offTimeStore = (TextView) findViewById(R.id.offTimeTvUpdate);

        reviewRegDialog = new Dialog(InfoStoreActivity.this);
        reviewRegDialog.setContentView(R.layout.dialog_review_register);
        reviewRegDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        reviewRating = reviewRegDialog.findViewById(R.id.reviewRating);
        reviewContent = reviewRegDialog.findViewById(R.id.reviewContent);

        reviewSubmitBtn = reviewRegDialog.findViewById(R.id.reviewSubmitBtn);
        reviewSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reviewRating.getRating() != 0 && !reviewContent.getText().toString().equals("")) {
                    reviewSubmit(reviewRating.getRating(), reviewContent.getText());
                    reviewRegDialog.dismiss();
                    finish();
                    overridePendingTransition(0, 0);
                    Intent intent = getIntent();
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    Toast.makeText(InfoStoreActivity.this, "작성하지 않은 리뷰가 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


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

        // 수정 페이지로 이동
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoUpdateActivity.class);
                intent.putExtra("storeId",storeId);
                intent.putExtra("storeUpdateInfo", storeSerializable);
                startActivity(intent);
            }
        });

    } // onCreate

    @Override
    protected void onRestart() {
        super.onRestart();
        loadStoreInfo(storeId);
    }

    private void storeRunCheck(byte b, String storeId) {
        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/storeRunCheck.jsp?run="+b+"&storeId="+storeId;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("StoreRunCheck:", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    private void likeThisStore(boolean b,String mId, String storeId) {
        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/likeThisStore.jsp?like="+b+"&mId="+mId+"&storeId="+storeId;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
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
                });
        requestQueue.add(stringRequest);
    }

    public void loadStoreInfo(String storeId){
        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/infoStore.jsp?storeId="+storeId;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Store Info:", response);
                        store = new Store();
                        MsgXmlParser msgXmlParser = new MsgXmlParser(response);
                        msgXmlParser.xmlParsingForStore(store);

                        storeSerializable.setStoreName(store.getStoreName());
                        storeSerializable.setPayWay(store.getPayWay().toString());
                        storeSerializable.setRunDay(store.getRunDay().toString());
                        storeSerializable.setOpenTime(store.getOpenTime());
                        storeSerializable.setOffTime(store.getOffTime());
                        storeSerializable.setCategory(store.getCategory().toString());
                        storeSerializable.setMenus(store.getMenus().toString());

                        payWayBooleans = new ArrayList<>();
                        msgXmlParser.payWayInfo(store.getPayWay(),payWayBooleans);
                        daysBooleans = new ArrayList<>();
                        msgXmlParser.daysInfo(store.getRunDay(),daysBooleans);
                        menuList = new ArrayList<>();
                        msgXmlParser.menuInfo(store.getMenus(),menuList);
                        ctgBooleans = new ArrayList<>();
                        msgXmlParser.categoryInfo(store.getCategory(),ctgBooleans);
                        updateUi(store);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    public void updateUi(Store store){
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        favoriteLoad(userId, store.getStoreId());
                        isRunningLoad(store.getStoreId());
                    }
                });
            }
        }).start();

        storeLocationTv.setText(store.getStoreLocation());
        storeNameTv.setText(store.getStoreName());
        payWayChecking(payWayBooleans);
        daysChecking(daysBooleans);
        categoryChecking(ctgBooleans);

        openTimeStore.setText(store.getOpenTime());
        offTimeStore.setText(store.getOffTime());

        menuPrint(menuList);
        storeReviews = new ArrayList<StoreReview>();
        reviewPrint(storeReviews);
    }

    public void payWayChecking(ArrayList<Boolean> bool){
        for(int i=0;i<bool.size();i++){
            if (bool.get(i)){
                payWays[i].setChecked(true);
            } else
                payWays[i].setChecked(false);
        }
    }

    public void daysChecking(ArrayList<Boolean> bool){
        for(int i=0;i<bool.size();i++){
            if (bool.get(i)){
                days[i].setChecked(true);
            } else
                days[i].setChecked(false);

        }
    }

    public void categoryChecking(ArrayList<Boolean> bool){
        for(int i=0;i<bool.size();i++){
            if (bool.get(i)){
                categorys[i].setChecked(true);
            } else
                categorys[i].setChecked(false);
        }
    }

    public void menuPrint(ArrayList<Menu> m) {
        menuAdapter = new MenuAdapter(m);
        storeMenuRecyclerView.setAdapter(menuAdapter);
    }

    public void reviewPrint(ArrayList<StoreReview> m) {
        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/reviewFromStore.jsp?sId="+storeId;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        MsgXmlParser msgXmlParser = new MsgXmlParser(response);

                        msgXmlParser.xmlParsingSRFM(m);
                        storeReviewAdapter = new StoreReviewAdapter(m);
                        storeReviewRecyclerView.setAdapter(storeReviewAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    public void favoriteLoad(String mId, String storeId){
        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/infoFavorite.jsp?mId="+mId+"&storeId="+storeId;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] result = response.split("\\?");
                        if (result[1].equals("1")){
                            favoriteBtn.setChecked(true);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    public void isRunningLoad(String storeId){
        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/infoRunning.jsp?storeId="+storeId;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("runningLoad",response);
                        String[] result = response.split("\\?");
                        if (result[1].equals("1")){
                            isRunningSwitch.setChecked(true);
                            isRunningSwitch.setText("영업 중");
                        } else {
                            isRunningSwitch.setText("영업 종료");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    private void reviewSubmit(float rating, Editable text) {
        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/reviewRegister.jsp";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        if(!String.valueOf(reviewRating.getRating()).equals(null) && !reviewContent.getText().toString().equals(null)) {
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
                    params.put("mId",userId);
                    params.put("storeId",storeId);
                    params.put("rating", String.valueOf(rating));
                    params.put("review", text.toString());
                    params.put("date", getDate);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}