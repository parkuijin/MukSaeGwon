package com.cookandroid.muksaegwon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.muksaegwon.model.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InfoRegisterActivity extends AppCompatActivity {

    Button submitBtn;
    ImageView infoRegFinBtn, plusMenu, minusMenu;
    CheckBox cash, creditCard, account, mon, tue, wed, thu, fri, sat, sun;
    EditText storeName;
    TextView storeLocation;

    LinearLayout menuContainer;

    JSONObject menu;
    JSONArray menus;

    JSONObject payWay;

    JSONObject date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_register);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        plusMenu = (ImageView) findViewById(R.id.plusMenuBtn);
        minusMenu = (ImageView) findViewById(R.id.minusMenuBtn);
        menuContainer = (LinearLayout) findViewById(R.id.menuItemLayout);
        storeName = (EditText) findViewById(R.id.etStroeName);
        storeLocation = (TextView) findViewById(R.id.tvStoreRocation);
        submitBtn = (Button) findViewById(R.id.regInfoSubmitBtn);
        infoRegFinBtn = (ImageView) findViewById(R.id.regInfoFinBtn);
        cash = (CheckBox) findViewById(R.id.checkCash);
        creditCard = (CheckBox) findViewById(R.id.checkCreditCard);
        account = (CheckBox) findViewById(R.id.checkAccountTransfer);
        mon = (CheckBox) findViewById(R.id.checkMon);
        tue = (CheckBox) findViewById(R.id.checkTue);
        wed = (CheckBox) findViewById(R.id.checkWed);
        thu = (CheckBox) findViewById(R.id.checkThu);
        fri = (CheckBox) findViewById(R.id.checkFri);
        sat = (CheckBox) findViewById(R.id.checkSat);
        sun = (CheckBox) findViewById(R.id.checkSun);

        Intent intent = getIntent();

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

        Store store = new Store(); // model - Store 객체 생성

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 가게 주소 가져오기
                storeLocation.getText().toString();

                // 입력한 가게 이름 가져오기
                storeName.getText().toString();

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

                // 카테고리 가져오기

                // 입력한 메뉴 가져오기
                for (int i = 0; i < menuContainer.getChildCount(); i++) {
                    View v = menuContainer.getChildAt(i);

                    // TextView tvMenuName = v.findViewById(R.id.tvMenuName);
                    EditText etMenuName = v.findViewById(R.id.etMenuName);
                    // TextView tvMenuPrice = v.findViewById(R.id.tvMenuPrice);
                    EditText etMenuPrice = v.findViewById(R.id.etMenuPrice);

                    try {
                        menu = new JSONObject();
                        menu.put("name", etMenuName.getText().toString());
                        menu.put("price", etMenuPrice.getText().toString());

                        menus = new JSONArray();
                        menus.put(menu);

                        // JsonObject 초기화
                        menu = new JSONObject();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



            }
        });
    }
}