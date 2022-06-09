package com.cookandroid.muksaegwon;

import android.content.Context;
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

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cookandroid.muksaegwon.controller.InputFilterMinMax;
import com.cookandroid.muksaegwon.model.Menu;

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
    CheckBox cash, creditCard, account, mon, tue, wed, thu, fri, sat, sun;
    CheckBox corn, fish, topokki, eomuk, sweetpotato, toast, takoyaki, waffle, dakggochi;
    EditText storeName, openTime, closeTime;

    ArrayList<Menu> menuArrayList = new ArrayList<>();

    JSONObject menu;
    JSONArray menus;
    JSONObject payWay;
    JSONObject runningDate;
    JSONObject selectedCategory;

    RequestQueue requestQueue;

    String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_update);

        storeId = getIntent().getStringExtra("storeId");

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

        corn = (CheckBox) findViewById(R.id.checkCornUpdate);
        fish = (CheckBox) findViewById(R.id.checkFishUpdate);
        topokki = (CheckBox) findViewById(R.id.checkTopokkiUpdate);
        eomuk = (CheckBox) findViewById(R.id.checkEomukUpdate);
        sweetpotato = (CheckBox) findViewById(R.id.checkSweetPotatoUpdate);
        toast = (CheckBox) findViewById(R.id.checkToastUpdate);
        takoyaki = (CheckBox) findViewById(R.id.checkTakoyakiUpdate);
        waffle = (CheckBox) findViewById(R.id.checkWaffleUpdate);
        dakggochi = (CheckBox) findViewById(R.id.checkDakggochiUpdate);

        menuContainer = (LinearLayout) findViewById(R.id.menuItemLayoutUpdate);
        storeName = (EditText) findViewById(R.id.StoreNameUpdateTv);

        deleteBtn = (TextView) findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://ec2-54-188-243-35.us-west-2.compute.amazonaws.com:8080/MukSaeGwonServer/deleteStore.jsp?storeId="+storeId;
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("DELETE STORE: ",response);
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

        cash = (CheckBox) findViewById(R.id.checkCashUpdate);
        creditCard = (CheckBox) findViewById(R.id.checkCreditCardUpdate);
        account = (CheckBox) findViewById(R.id.checkAccountTransferUpdate);

        mon = (CheckBox) findViewById(R.id.checkMonUpdate);
        tue = (CheckBox) findViewById(R.id.checkTueUpdate);
        wed = (CheckBox) findViewById(R.id.checkWedUpdate);
        thu = (CheckBox) findViewById(R.id.checkThuUpdate);
        fri = (CheckBox) findViewById(R.id.checkFriUpdate);
        sat = (CheckBox) findViewById(R.id.checkSatUpdate);
        sun = (CheckBox) findViewById(R.id.checkSunUpdate);

        // loadStoreInfo();

        // 현재 DB에 입력된 메뉴만큼 메뉴 레이아웃 생성
        for (int i = 0; i < menuArrayList.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.item_menu, menuContainer, true);
        }

        // 생성된 메뉴 레이아웃에 현재 DB에 입력된 메뉴 넣기
        for (int i = 0; i < menuContainer.getChildCount(); i++) {
            View v = menuContainer.getChildAt(i);

            EditText etMenuName = v.findViewById(R.id.MenuNameTv);
            EditText etMenuPrice = v.findViewById(R.id.MenuPriceTv);

            etMenuName.setText(menuArrayList.get(i).getMenuName());
            etMenuPrice.setText(menuArrayList.get(i).getMenuPrice());
        }

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

        String url = "";

        // 수정된 내용 제출 버튼
        UpdateSubmitBtn.setOnClickListener(new View.OnClickListener() {
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
                } catch (Exception e) {
                }

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

                    EditText etMenuName = v.findViewById(R.id.MenuNameTv);
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

                        // 입력한 가게 이름 가져오기
                        params.put("StoreName", storeName.getText().toString());

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

                        return params;
                    }
                };

                // 가게 정보, 수정 액티비티 종료
               // infoStoreActivity.finish();
               // finish();

                // 가게 정보 액티비티 실행
                //Intent intent = new Intent(getApplicationContext(), InfoStoreActivity.class);
                //startActivity(intent);

            } // onClick
        }); // setOnClickListener


    } // onCreate

    public void loadStoreInfo(String storeId) {
        String url = "";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*Log.i("response: ",response);
                        MsgXmlParser msgXmlParser = new MsgXmlParser(response);
                        msgXmlParser.xmlParsingForStore(store);
                        msgXmlParser.payWayInfo(store.getPayWay(),payWayBooleans);
                        msgXmlParser.daysInfo(store.getRunDay(),daysBooleans);
                        msgXmlParser.menuInfo(store.getMenus(),menuList);

                        msgXmlParser.categoryInfo(store.getCategory(),ctgBooleans);
                        updateUi(store);*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}