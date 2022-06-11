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
import com.cookandroid.muksaegwon.controller.MsgXmlParser;
import com.cookandroid.muksaegwon.model.Menu;
import com.cookandroid.muksaegwon.model.StoreSerializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfoUpdateActivity extends AppCompatActivity {

    TextView deleteBtn;
    ImageView plusMenu, minusMenu, infoUpdateFinBtn;
    LinearLayout menuContainer;
    Button UpdateSubmitBtn;
    CheckBox[] payWays = new CheckBox[3];
    CheckBox[] days = new CheckBox[7];
    CheckBox[] categorys = new CheckBox[9];
    EditText storeName, openTime, closeTime;

    ArrayList<Menu> menuArrayList = new ArrayList<>();

    JSONObject menu;
    JSONArray menus = new JSONArray();
    JSONObject payWay;
    JSONObject runningDate;
    JSONObject selectedCategory;

    RequestQueue requestQueue;

    String storeId;
    StoreSerializable storeSerializable;

    private ArrayList<Boolean> payWayBooleans;
    private ArrayList<Boolean> daysBooleans;
    private ArrayList<Boolean> ctgBooleans;

    private ArrayList<Menu> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_update);

        Intent intent = getIntent();

        storeId = intent.getStringExtra("storeId");
        Log.i("STOREID: ",storeId);
        storeSerializable = (StoreSerializable) intent.getSerializableExtra("storeUpdateInfo");

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        InfoStoreActivity infoStoreActivity = (InfoStoreActivity) InfoStoreActivity.infoStoreActivity;

        requestQueue = Volley.newRequestQueue(this);

        UpdateSubmitBtn = (Button) findViewById(R.id.updateInfoSubmitBtn);
        menuContainer = (LinearLayout) findViewById(R.id.menuItemLayoutUpdate);
        plusMenu = (ImageView) findViewById(R.id.plusMenuBtnUpdate);
        minusMenu = (ImageView) findViewById(R.id.minusMenuBtnUpdate);
        openTime = (EditText) findViewById(R.id.openTimeTvUpdate);
        closeTime = (EditText) findViewById(R.id.offTimeTvUpdate);

        categorys[0] = (CheckBox) findViewById(R.id.checkCornUpdate);
        categorys[1] = (CheckBox) findViewById(R.id.checkFishUpdate);
        categorys[2] = (CheckBox) findViewById(R.id.checkTopokkiUpdate);
        categorys[3] = (CheckBox) findViewById(R.id.checkEomukUpdate);
        categorys[4] = (CheckBox) findViewById(R.id.checkSweetPotatoUpdate);
        categorys[5] = (CheckBox) findViewById(R.id.checkToastUpdate);
        categorys[6] = (CheckBox) findViewById(R.id.checkTakoyakiUpdate);
        categorys[7] = (CheckBox) findViewById(R.id.checkWaffleUpdate);
        categorys[8] = (CheckBox) findViewById(R.id.checkDakggochiUpdate);

        menuContainer = (LinearLayout) findViewById(R.id.menuItemLayoutUpdate);
        storeName = (EditText) findViewById(R.id.StoreNameUpdateTv);

        deleteBtn = (TextView) findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/deleteStore.jsp?storeId=" + storeId;
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_LONG).show();
                                infoStoreActivity.finish();
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                requestQueue.add(stringRequest);
            }
        });

        infoUpdateFinBtn = (ImageView) findViewById(R.id.btn_back5);

        payWays[0] = (CheckBox) findViewById(R.id.checkCashUpdate);
        payWays[1] = (CheckBox) findViewById(R.id.checkCreditCardUpdate);
        payWays[2] = (CheckBox) findViewById(R.id.checkAccountTransferUpdate);

        days[0] = (CheckBox) findViewById(R.id.checkMonUpdate);
        days[1] = (CheckBox) findViewById(R.id.checkTueUpdate);
        days[2] = (CheckBox) findViewById(R.id.checkWedUpdate);
        days[3] = (CheckBox) findViewById(R.id.checkThuUpdate);
        days[4] = (CheckBox) findViewById(R.id.checkFriUpdate);
        days[5] = (CheckBox) findViewById(R.id.checkSatUpdate);
        days[6] = (CheckBox) findViewById(R.id.checkSunUpdate);

        // 가게 정보 로드
        loadStoreInfo(storeSerializable);

        // Runtime Input Filter
        openTime.setFilters(new InputFilter[]{new InputFilterMinMax("0", "24")});
        closeTime.setFilters(new InputFilter[]{new InputFilterMinMax("0", "24")});

        // 뒤로가기 버튼
        infoUpdateFinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        plusMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater.inflate(R.layout.item_menu, menuContainer, true);
            }
        }); // plus

        minusMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 인덱스 번호가 큰 레이아웃부터 삭제
                menuContainer.removeViewAt(menuContainer.getChildCount() - 1);
            }
        }); // minus

        String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/infoUpdate.jsp";

        // 수정된 내용 제출 버튼
        UpdateSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (menuContainer.getChildCount() == 0) {
                    Toast.makeText(getApplicationContext(), "메뉴를 추가하세요.", Toast.LENGTH_LONG).show();
                    return;
                } else if (storeName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "가게 이름을 입력하세요.", Toast.LENGTH_LONG).show();
                    return;
                }

                // 결제 방식 가져오기
                try {
                    payWay = new JSONObject();
                    payWay.put("cash", payWays[0].isChecked());
                    payWay.put("card", payWays[1].isChecked());
                    payWay.put("account", payWays[2].isChecked());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // 출몰 요일 가져오기
                try {
                    runningDate = new JSONObject();
                    runningDate.put("mon", days[0].isChecked());
                    runningDate.put("tue", days[1].isChecked());
                    runningDate.put("wed", days[2].isChecked());
                    runningDate.put("thu", days[3].isChecked());
                    runningDate.put("fri", days[4].isChecked());
                    runningDate.put("sat", days[5].isChecked());
                    runningDate.put("sun", days[6].isChecked());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray = new JSONArray("sdf");
                } catch (Exception e) {
                }

                // 카테고리 가져오기
                try {
                    selectedCategory = new JSONObject();
                    selectedCategory.put("corn", categorys[0].isChecked());
                    selectedCategory.put("fish", categorys[1].isChecked());
                    selectedCategory.put("topokki", categorys[2].isChecked());
                    selectedCategory.put("eomuk", categorys[3].isChecked());
                    selectedCategory.put("sweetpotato", categorys[4].isChecked());
                    selectedCategory.put("toast", categorys[5].isChecked());
                    selectedCategory.put("takoyaki", categorys[6].isChecked());
                    selectedCategory.put("waffle", categorys[7].isChecked());
                    selectedCategory.put("dakggochi", categorys[8].isChecked());
                    if (!selectedCategory.toString().contains("true")) {
                        Toast.makeText(getApplicationContext(), "카테고리를 선택하세요.", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // 입력한 메뉴 가져오기
                for (int i = 0; i < menuContainer.getChildCount(); i++) {
                    View v = menuContainer.getChildAt(i);

                    EditText etMenuName = v.findViewById(R.id.MenuNameTv);
                    EditText etMenuPrice = v.findViewById(R.id.MenuPriceTv);

                    if (etMenuName.getText().toString().equals("") ||
                            etMenuPrice.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "메뉴를 입력하세요.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    try {
                        menu = new JSONObject();
                        menu.put("name", etMenuName.getText().toString());
                        menu.put("price", etMenuPrice.getText().toString());

                        menus.put(menu);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } // for

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();

                        params.put("storeId", storeId);

                        // 입력한 가게 이름 가져오기
                        params.put("StoreName", nullCheck(storeName.getText().toString()));

                        // 결제 방식 가져오기
                        params.put("PayWay", payWay.toString());

                        // 출몰 요일 가져오기
                        params.put("RunningDate", runningDate.toString());

                        // OPENTIME, CLOSETIME
                        params.put("OpenTime", timeCheck(openTime.getText().toString()));
                        params.put("CloseTime", timeCheck(closeTime.getText().toString()));

                        // 카테고리 가져오기
                        params.put("SelectedCategory", selectedCategory.toString());

                        // 입력한 메뉴 가져오기
                        params.put("menus", menus.toString());
                        Log.i("menus", menus.toString());

                        Log.i("Params:", params.toString());

                        return params;
                    }
                }; // getParams

                requestQueue.add(stringRequest);
                finish();

            } // onClick
        }); // setOnClickListener


    } // onCreate


    public void loadStoreInfo(StoreSerializable storeSerializable) {

        MsgXmlParser msgXmlParser = new MsgXmlParser();

        storeName.setText(storeSerializable.getStoreName());

        try {
            payWayBooleans = new ArrayList<>();
            String strPayWay = storeSerializable.getPayWay();
            JSONObject payWayJSONObj = new JSONObject(strPayWay);
            msgXmlParser.payWayInfo(payWayJSONObj, payWayBooleans);
            payWayChecking(payWayBooleans);
        } catch (Exception e) {
        }

        try {
            daysBooleans = new ArrayList<>();
            String strDays = storeSerializable.getRunDay();
            JSONObject daysJSONObj = new JSONObject(strDays);
            msgXmlParser.daysInfo(daysJSONObj, daysBooleans);
            daysChecking(daysBooleans);
        } catch (Exception e) {
        }

        openTime.setText(storeSerializable.getOpenTime());
        closeTime.setText(storeSerializable.getOffTime());

        try {
            ctgBooleans = new ArrayList<>();
            String strCtg = storeSerializable.getCategory();
            JSONObject ctgJSONObj = new JSONObject(strCtg);
            msgXmlParser.categoryInfo(ctgJSONObj, ctgBooleans);
            categoryChecking(ctgBooleans);
        } catch (Exception e) {
        }

        try {
            menuList = new ArrayList<>();
            String strMenus = storeSerializable.getMenus();
            JSONArray menuJSONArray = new JSONArray(strMenus);
            msgXmlParser.menuInfo(menuJSONArray, menuList);
            menuPrint(menuList);
        } catch (Exception e) {
        }

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
        for (int i = 0; i < bool.size(); i++) {
            if (bool.get(i)) {
                categorys[i].setChecked(true);
            }
        }
    }

    public void menuPrint(ArrayList<Menu> m) {

        // 현재 DB에 입력된 메뉴만큼 메뉴 레이아웃 생성
        for (int i = 0; i < m.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.item_menu, menuContainer, true);
        }

        // 생성된 메뉴 레이아웃에 현재 DB에 입력된 메뉴 넣기
        for (int i = 0; i < menuContainer.getChildCount(); i++) {
            View v = menuContainer.getChildAt(i);

            EditText etMenuName = v.findViewById(R.id.MenuNameTv);
            EditText etMenuPrice = v.findViewById(R.id.MenuPriceTv);

            etMenuName.setText(m.get(i).getMenuName());
            etMenuPrice.setText(m.get(i).getMenuPrice());
        }
    }

    public String nullCheck(String nCheck) {
        if (nCheck.equals(null)) {
            nCheck = "";
        }
        return nCheck;
    }

    public String timeCheck(String nCheck) {
        if (nCheck.equals(null) || nCheck.equals("")) {
            nCheck = "0";
        }
        return nCheck;
    }

}