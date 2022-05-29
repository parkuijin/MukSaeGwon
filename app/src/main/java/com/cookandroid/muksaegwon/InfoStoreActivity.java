package com.cookandroid.muksaegwon;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cookandroid.muksaegwon.model.Store;

public class InfoStoreActivity extends AppCompatActivity {

    TextView storeNameTv, storeLocationTv, categoryTv, menuTv, payWayTv, isRunningSw, openTimeTv, offTimeTv;
    Store store;
    Button reviewRegBtn;
    Dialog reviewRegDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_store);

        // ActionBar hide
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        reviewRegBtn = (Button) findViewById(R.id.reviewRegBtn);

        storeNameTv = (TextView) findViewById(R.id.storeNameTv);
        storeLocationTv = (TextView)findViewById(R.id.storeLocationTv);
        categoryTv = (TextView) findViewById(R.id.categoryTv);
        /*menuTv = (TextView) findViewById(R.id.menuTv);*/
        payWayTv = (TextView) findViewById(R.id.payWayTv);
        isRunningSw = (TextView) findViewById(R.id.isRunningSwitch);
        openTimeTv = (TextView) findViewById(R.id.openTimeTv);
        offTimeTv = (TextView) findViewById(R.id.offTimeTv);

        reviewRegDialog = new Dialog(InfoStoreActivity.this);
        reviewRegDialog.setContentView(R.layout.dialog_review_register);

        String url ="";
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

        reviewRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewRegDialog.show();
            }
        });

    }
}