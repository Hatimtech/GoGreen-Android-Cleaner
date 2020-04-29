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
import com.gogreencleaner.main.Utils.Constants;
import com.gogreencleaner.main.Utils.Preferences.PrefEntity;
import com.gogreencleaner.main.Utils.Preferences.Preferences;
import com.gogreencleaner.main.Volley.APIUtility;
import com.gogreencleaner.main.model.UpdatePassword.REQUEST.UpdatePasswordRequest;
import com.gogreencleaner.main.model.UpdatePassword.RESPONSE.UpdatePasswordResponseWrapper;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by AKASH SHUKLA on 28-May-18.
 */

public class UpdatePasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText newPassword, confirmPassword;
    private Button save;
    private Toolbar toolbar;
    private AppCompatTextView title;
    private ImageView back;
    APIUtility apiUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        apiUtility = new APIUtility(UpdatePasswordActivity.this);

        InitView();
    }

    private void InitView() {


        newPassword = (EditText) findViewById(R.id.new_password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        save = (Button) findViewById(R.id.submit);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) findViewById(R.id.title);
        title.setText(R.string.updatePassword);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        save.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                validation();
                break;
        }

    }

    void validation() {


        if (!TextUtils.isEmpty(newPassword.getText().toString().trim())) {

            if (newPassword.getText().toString().trim().length() >= 6 && newPassword.getText().toString().trim().length() <= 16) {

                if (!TextUtils.isEmpty(confirmPassword.getText().toString().trim())) {

                    if (confirmPassword.getText().toString().trim().length() >= 6 && confirmPassword.getText().toString().trim().length() <= 16) {

                        if (newPassword.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {

                            UpdatePassword(confirmPassword.getText().toString());


                        } else {
                            confirmPassword.setError(getString(R.string.samePasswordAlert));
                        }

                    } else {
                        confirmPassword.setError(getString(R.string.validPasswordAlert));
                    }
                } else {
                    confirmPassword.setError(getString(R.string.passwordAlert));
                }

            } else {
                newPassword.setError(getString(R.string.validPasswordAlert));
            }
        } else {
            newPassword.setError(getString(R.string.passwordAlert));
        }
    }

    void UpdatePassword(String confirmPassword) {
        UpdatePasswordRequest changePasswordRequest = new UpdatePasswordRequest();
        changePasswordRequest.setAppKey(Constants.APPKEY);
        changePasswordRequest.setConfirmPassword(confirmPassword);
        changePasswordRequest.setUserID(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));
        changePasswordRequest.setMethod("update_password");

        apiUtility.updatePassword(UpdatePasswordActivity.this, changePasswordRequest, true, new APIUtility.APIResponseListener<UpdatePasswordResponseWrapper>() {
            @Override
            public void onReceiveResponse(UpdatePasswordResponseWrapper response) {
                if (response != null) {
                    Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(UpdatePasswordActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(UpdatePasswordResponseWrapper response) {
                CommonUtils.alert(UpdatePasswordActivity.this, response.getResponse().getMessage());

            }
        });

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
