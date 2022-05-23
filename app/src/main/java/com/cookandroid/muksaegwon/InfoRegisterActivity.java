package com.cookandroid.muksaegwon;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class InfoRegisterActivity extends AppCompatActivity {

    ImageView infoRegFinBtn;
    CheckBox cash, creditCard, account, mon, tue, wed, thu, fri, sat, sun;
    EditText storeName;
    TextView storeLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_register);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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

        infoRegFinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}