package com.cookandroid.muksaegwon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cookandroid.muksaegwon.controller.MsgXmlParser;
import com.cookandroid.muksaegwon.model.Category;
import com.cookandroid.muksaegwon.model.Menu;
import com.cookandroid.muksaegwon.model.PayWay;
import com.cookandroid.muksaegwon.model.Store;

import java.util.ArrayList;

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

        Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // ActionBar hide
                ActionBar actionBar = getSupportActionBar();
                actionBar.hide();


                // 이 아래로 테스트 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                String text = "<storeName>abc</storeName>" +
                        "<lat>37.122</lat> " +
                        "<lng>122.436</lng>" +
                        " <menu>[{\"name\":\"aaa\",\"price\":\"1000\"},{\"name\":\"bbb\",\"price\":\"3000\"}]</menu> " +
                        "<payWay>{\"cash\": true,\"card\": false,\"account\": true}</payWay> " +
                        "<isRunning>0</isRunning> " +
                        "<runDay>aaa</runDay> " +
                        "<openTime>9</openTime> " +
                        "<offTime>9</offTime>" +
                        "<category>{\n" +
                        "\t\"corn\": true,\n" +
                        "\t\"fish\": false,\n" +
                        "\t\"topokki\": true,\n" +
                        "\t\"eomuk\": true,\n" +
                        "\t\"sweetpotato\": true,\n" +
                        "\t\"toast\": true,\n" +
                        "\t\"takoyaki\": true,\n" +
                        "\t\"waffle\": true,\n" +
                        "\t\"dakggochi\": true\n" +
                        "}</category>";

                ArrayList<Category> categories = new ArrayList<>();
                ArrayList<PayWay> payWays = new ArrayList<>();
                ArrayList<Store> stores = new ArrayList<>();
                ArrayList<Menu> menus = new ArrayList<>();

                MsgXmlParser msgXmlParser = new MsgXmlParser(text);
                msgXmlParser.xmlParsingSFM(stores);

                Log.i("test",stores.get(0).getMenus().toString());

                msgXmlParser.jsonParsingMNP(stores.get(0).getMenus(), menus);
                msgXmlParser.jsonParsingPW(stores.get(0).getPayWay(), payWays);
                msgXmlParser.jsonParsingCTG(stores.get(0).getCategory(), categories);
                String a = categories.get(0).getCorn(); // corn의 값이 반환

                // 이 위로 테스트 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~




                mapFragment = new MapFragment();

                fragmentManager = getSupportFragmentManager();

                // Google Map 프래그먼트 출력
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.Container, mapFragment, null);
                fragmentTransaction.commit();

                // 카테고리 액티비티 열기
                CategoryButton = (ImageView)

                        findViewById(R.id.CategoryButton);
                CategoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                        startActivity(intent);
                    }
                });

                // 로그인 액티비티 열기
                MypageButton = (ImageView)

                        findViewById(R.id.MypageButton);
                MypageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                        startActivity(intent);
                    }
                });

                // 가게 등록 액티비티 열기
                RegisterButton = (ImageView)

                        findViewById(R.id.RegisterButton);
                RegisterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intent);
                    }
                });

                // 테스트용 가게 정보 액티비티 열기
                MapButton = (ImageView)

                        findViewById(R.id.MapButton);
                MapButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), InfoStoreActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }, 1);
    }
}
