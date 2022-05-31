package com.cookandroid.muksaegwon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.cookandroid.muksaegwon.controller.InputFilterMinMax;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InfoRegisterActivity extends AppCompatActivity {

    Button submitBtn;
    ImageView infoRegFinBtn, plusMenu, minusMenu;
    CheckBox cash, creditCard, account, mon, tue, wed, thu, fri, sat, sun;
    CheckBox corn, fish, topokki, eomuk, sweetpotato, toast, takoyaki, waffle, dakggochi;
    EditText storeName, openTime, closeTime;
    TextView storeLocation;

    LinearLayout menuContainer;

    JSONObject menu;
    JSONArray menus;

    JSONObject payWay;

    JSONObject runningDate;

    JSONObject selectedCategory;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_register);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        RegisterActivity registerActivity = (RegisterActivity) RegisterActivity.registerActivity;

        openTime = (EditText) findViewById(R.id.openTimeTvStore);
        closeTime = (EditText) findViewById(R.id.offTimeTvStore);
        corn = (CheckBox) findViewById(R.id.checkCornStore);
        fish = (CheckBox) findViewById(R.id.checkFishStore);
        topokki = (CheckBox) findViewById(R.id.checkTopokkiStore);
        eomuk = (CheckBox) findViewById(R.id.checkEomukStore);
        sweetpotato = (CheckBox) findViewById(R.id.checkSweetPotatoStore);
        toast = (CheckBox) findViewById(R.id.checkToastStore);
        takoyaki = (CheckBox) findViewById(R.id.checkTakoyakiStore);
        waffle = (CheckBox) findViewById(R.id.checkWaffleStore);
        dakggochi = (CheckBox) findViewById(R.id.checkDakggochiStore);
        plusMenu = (ImageView) findViewById(R.id.plusMenuBtn);
        minusMenu = (ImageView) findViewById(R.id.minusMenuBtn);
        menuContainer = (LinearLayout) findViewById(R.id.menuItemLayout);
        storeName = (EditText) findViewById(R.id.etStroeName);
        storeLocation = (TextView) findViewById(R.id.StoreNameTv);
        submitBtn = (Button) findViewById(R.id.regInfoSubmitBtn);
        infoRegFinBtn = (ImageView) findViewById(R.id.btn_back3);
        cash = (CheckBox) findViewById(R.id.checkCashStore);
        creditCard = (CheckBox) findViewById(R.id.checkCreditCardStore);
        account = (CheckBox) findViewById(R.id.checkAccountTransferStore);
        mon = (CheckBox) findViewById(R.id.checkMonStore);
        tue = (CheckBox) findViewById(R.id.checkTueStore);
        wed = (CheckBox) findViewById(R.id.checkWedStore);
        thu = (CheckBox) findViewById(R.id.checkThuStore);
        fri = (CheckBox) findViewById(R.id.checkFriStore);
        sat = (CheckBox) findViewById(R.id.checkSatStore);
        sun = (CheckBox) findViewById(R.id.checkSunStore);

        menus = new JSONArray();

        Intent intent = getIntent();

        // Runtime Input Filter
        openTime.setFilters(new InputFilter[]{new InputFilterMinMax("0","24")});
        closeTime.setFilters(new InputFilter[]{new InputFilterMinMax("0","24")});

        requestQueue = Volley.newRequestQueue(this);

        // 172.111.113.13
        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/infoRegister.jsp";

        storeLocation.setText(intent.getStringExtra("loc"));

        infoRegFinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        plusMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // LayoutInflater inflater = LayoutInflater.from(MenuActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                inflater.inflate(R.layout.item_menu, menuContainer, true);

            }
        });

        minusMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 인덱스 번호가 큰 레이아웃부터 삭제
                menuContainer.removeViewAt(menuContainer.getChildCount() - 1);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 결제 방식 가져오기
                try {
                    payWay = new JSONObject();
                    payWay.put("cash", cash.isChecked());
                    payWay.put("card", creditCard.isChecked());
                    payWay.put("account", account.isChecked());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // 출몰 요일 가져오기
                try {
                    runningDate = new JSONObject();
                    runningDate.put("mon", mon.isChecked());
                    runningDate.put("tue", tue.isChecked());
                    runningDate.put("wed", wed.isChecked());
                    runningDate.put("thu", thu.isChecked());
                    runningDate.put("fri", fri.isChecked());
                    runningDate.put("sat", sat.isChecked());
                    runningDate.put("sun", sun.isChecked());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray = new JSONArray("sdf");
                }catch(Exception e){}


                // 카테고리 가져오기
                try {
                    selectedCategory = new JSONObject();
                    selectedCategory.put("corn", corn.isChecked());
                    selectedCategory.put("fish", fish.isChecked());
                    selectedCategory.put("topokki", topokki.isChecked());
                    selectedCategory.put("eomuk", eomuk.isChecked());
                    selectedCategory.put("sweetpotato", sweetpotato.isChecked());
                    selectedCategory.put("toast", toast.isChecked());
                    selectedCategory.put("takoyaki", takoyaki.isChecked());
                    selectedCategory.put("waffle", waffle.isChecked());
                    selectedCategory.put("dakggochi", dakggochi.isChecked());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // 입력한 메뉴 가져오기
                for (int i = 0; i < menuContainer.getChildCount(); i++) {
                    View v = menuContainer.getChildAt(i);

                    // TextView tvMenuName = v.findViewById(R.id.tvMenuName);
                    EditText etMenuName = v.findViewById(R.id.MenuNameTv);
                    // TextView tvMenuPrice = v.findViewById(R.id.tvMenuPrice);
                    EditText etMenuPrice = v.findViewById(R.id.MenuPriceTv);

                    try {
                        menu = new JSONObject();
                        menu.put("name", etMenuName.getText().toString());
                        menu.put("price", etMenuPrice.getText().toString());

                        menus.put(menu);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } // for

                Log.i("JSONARRAYTEST", String.valueOf(menus));

                // Volley Post
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("REGISTER : ", response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();

                        // 입력한 가게 이름 가져오기
                        params.put("StoreName", storeName.getText().toString());

                        // 가게 주소 가져오기
                        params.put("StoreLocation", storeLocation.getText().toString());

                        // 가게 위도, 경도 가져오기, * 소수점 5자리 까지 *
                        params.put("lat", String.valueOf(intent.getDoubleExtra("lat", 0)));
                        params.put("lng", String.valueOf(intent.getDoubleExtra("lon", 0)));

                        // 결제 방식 가져오기
                        params.put("PayWay", payWay.toString());

                        // 출몰 요일 가져오기
                        params.put("RunningDate", runningDate.toString());



                        // 입력한 메뉴 가져오기
                        params.put("menus", menus.toString());
                        Log.i("menus", menus.toString());

                        // OPENTIME, CLOSETIME
                        params.put("OpenTime", openTime.getText().toString());
                        params.put("CloseTime", closeTime.getText().toString());

                        // 카테고리 가져오기
                        params.put("SelectedCategory", selectedCategory.toString());

                        // isRunning (Default 0)
                        params.put("isRunning", "0");

                        Log.i("PARAMS: ", params.toString());

                        return params;
                    }
                };

                requestQueue.add(stringRequest);
                // 메인 화면으로 돌아가기 위한 Activity 종료
                registerActivity.finish();

                // 나중에 보기 성공 메시지
                Toast.makeText(getApplicationContext(),"가게 정보가 등록됐습니다.",Toast.LENGTH_LONG).show();

                finish();
            } // OnClick
        }); // ClickListener
    }
}