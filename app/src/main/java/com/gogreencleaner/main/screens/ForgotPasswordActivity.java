package com.gogreencleaner.main.screens;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.gogreencleaner.main.R;
import com.gogreencleaner.main.Utils.CommonUtils;
import com.gogreencleaner.main.Utils.Preferences.PrefEntity;
import com.gogreencleaner.main.Utils.Preferences.Preferences;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText userPhone;
    private Button send;
    private Toolbar toolbar;
    private AppCompatTextView title;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        InitViews();
    }


    public void InitViews() {

        userPhone = (EditText) findViewById(R.id.user_email);
        send = (Button) findViewById(R.id.send);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText(R.string.toolbar);
        setSupportActionBar(toolbar);
        back = (ImageView) findViewById(R.id.back);
        send.setOnClickListener(this);
        back.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.send:
                hideKeyboard();
                validation();
                break;

            case R.id.back:
                finish();
                break;
        }
    }


    public void validation() {

        if (!TextUtils.isEmpty(userPhone.getText().toString().trim())) {
            if (CommonUtils.isValidMobile(userPhone)) {
                Preferences.setPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER, userPhone.getText().toString());
                Intent intent= new Intent(ForgotPasswordActivity.this,OTPActivity.class);
                startActivity(intent);


            } else {
                userPhone.setError(getString(R.string.validPhoneAlert));

            }
        } else {
            userPhone.setError(getString(R.string.phoneAlert));
        }
    }




    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}

