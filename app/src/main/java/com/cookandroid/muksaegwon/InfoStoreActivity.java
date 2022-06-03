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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InfoStoreActivity extends AppCompatActivity {

    Long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
    String getDate = ymd.format(date);

    Store store = new Store();

    TextView storeNameTv, storeLocationTv, openTimeStore, offTimeStore;
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
    ArrayList<Menu> menus;

    String storeId;
    private ArrayList<Boolean> payWayBooleans = new ArrayList<>();
    private ArrayList<Boolean> daysBooleans = new ArrayList<>();
    private ArrayList<Menu> menuList = new ArrayList<>();
    private MenuAdapter menuAdapter;

    private ArrayList<Boolean> ctgBooleans = new ArrayList<>();

    SharedPreferences preferences;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_store);

        preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        userId = preferences.getString("userId","");

        storeId = getIntent().getStringExtra("storeId");
        Log.i("StoreId:", storeId+"");
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
                    isRunningSwitch.setText(" 영업 중");
                    storeRunCheck((byte) 1,store.getStoreId());
                } else
                    isRunningSwitch.setText("영업 종료");
                    storeRunCheck((byte) 0,store.getStoreId());
            }
        });

        storeMenuRecyclerView = (RecyclerView) findViewById(R.id.storeMenuRecyclerView);
        storeReviewRecyclerView = (RecyclerView) findViewById(R.id.storeReviewRecyclerView);

        menus = new ArrayList<Menu>();
        storeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        storeReviews = new ArrayList<StoreReview>();
        storeReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        infoStoreFinBtn = (ImageView) findViewById(R.id.btn_back4);
        reviewRegBtn = (ImageView) findViewById(R.id.reviewRegBtn);
        storeNameTv = (TextView) findViewById(R.id.storeNameTv);
        storeLocationTv = (TextView) findViewById(R.id.StoreLocationTv);

        days[0] = (CheckBox) findViewById(R.id.checkMonStore);
        days[1] = (CheckBox) findViewById(R.id.checkTueStore);
        days[2]= (CheckBox) findViewById(R.id.checkWedStore);
        days[3]= (CheckBox) findViewById(R.id.checkThuStore);
        days[4]= (CheckBox) findViewById(R.id.checkFriStore);
        days[5]= (CheckBox) findViewById(R.id.checkSatStore);
        days[6]= (CheckBox) findViewById(R.id.checkSunStore);

        payWays[0] = (CheckBox) findViewById(R.id.checkCashStore);
        payWays[1] = (CheckBox) findViewById(R.id.checkCreditCardStore);
        payWays[2] = (CheckBox) findViewById(R.id.checkAccountTransferStore);

        categorys[0] = (CheckBox) findViewById(R.id.checkCornStore);
        categorys[1] = (CheckBox) findViewById(R.id.checkFishStore);
        categorys[2] = (CheckBox) findViewById(R.id.checkTopokkiStore);
        categorys[3] = (CheckBox) findViewById(R.id.checkEomukStore);
        categorys[4] = (CheckBox) findViewById(R.id.checkSweetPotatoStore);
        categorys[5]= (CheckBox) findViewById(R.id.checkToastStore);
        categorys[6] = (CheckBox) findViewById(R.id.checkTakoyakiStore);
        categorys[7] = (CheckBox) findViewById(R.id.checkWaffleStore);
        categorys[8] = (CheckBox) findViewById(R.id.checkDakggochiStore);

        openTimeStore = (TextView) findViewById(R.id.openTimeTvStore);
        offTimeStore = (TextView) findViewById(R.id.offTimeTvStore);

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
    }

    private void storeRunCheck(byte b, String storeId) {
        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/storeRunCheck.jsp?run="+b+"&storeId="+storeId;
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

    private void likeThisStore(boolean b,String mId, String storeId) {
        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/likeThisStore.jsp?like="+b+"&mId="+mId+"&storeId="+storeId;
        Log.i("URL: ",url);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("LIKE:", response);
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
                        Log.i("response: ",response);
                        MsgXmlParser msgXmlParser = new MsgXmlParser(response);
                        msgXmlParser.xmlParsingForStore(store);
                        msgXmlParser.payWayInfo(store.getPayWay(),payWayBooleans);
                        msgXmlParser.daysInfo(store.getRunDay(),daysBooleans);
                        msgXmlParser.menuInfo(store.getMenus(),menuList);
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
        reviewPrint(storeReviews);
    }

    public void payWayChecking(ArrayList<Boolean> bool){
        for(int i=0;i<bool.size();i++){
            if (bool.get(i)){
                payWays[i].setChecked(true);
            }
        }
    }

    public void daysChecking(ArrayList<Boolean> bool){
        for(int i=0;i<bool.size();i++){
            if (bool.get(i)){
                days[i].setChecked(true);
            }
        }
    }

    public void categoryChecking(ArrayList<Boolean> bool){
        for(int i=0;i<bool.size();i++){
            if (bool.get(i)){
                categorys[i].setChecked(true);
            }
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
                        Log.i("response:", response);
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
                        String[] result = response.split("\\?");
                        if (result[1].equals("1")){
                            isRunningSwitch.setChecked(true);
                            isRunningSwitch.setText(" 영업 중");
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
                            Log.i("review:", response);
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
                    Log.i("PARAMA:",params.toString());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}