package com.gogreencleaner.main.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gogreencleaner.main.R;
import com.gogreencleaner.main.Utils.CommonUtils;
import com.gogreencleaner.main.Utils.Preferences.PrefEntity;
import com.gogreencleaner.main.Utils.Preferences.Preferences;
import com.gogreencleaner.main.Volley.APIUtility;
import com.gogreencleaner.main.model.Login.REQUEST.LoginRequest;
import com.gogreencleaner.main.model.Login.RESPONSE.LoginResponseWrapper;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by AKASH SHUKLA on 28-May-18.
 */


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button button;
    private EditText userNumber, userPassword;
    private TextView fgPassword;
    APIUtility apiUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Preferences.getPreference_int(getApplicationContext(), PrefEntity.IS_LOGIN) == 1) {
            startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);
        apiUtility = new APIUtility(LoginActivity.this);
        InitView();

    }

    public void InitView() {
        fgPassword = (TextView) findViewById(R.id.fgPassword);
        userNumber = (EditText) findViewById(R.id.user_email);
        userPassword = (EditText) findViewById(R.id.user_password);
        button = (Button) findViewById(R.id.user_login);
        button.setOnClickListener(this);
        fgPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.user_login:
                validation();
                break;

            case R.id.fgPassword:
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
        }


    }

    private void validation() {

        if (!TextUtils.isEmpty(userNumber.getText().toString().trim())) {

            if (android.util.Patterns.EMAIL_ADDRESS.matcher(userNumber.getText().toString().trim()).matches()) {

                if (!TextUtils.isEmpty(userPassword.getText().toString().trim())) {

                    if (userPassword.getText().toString().trim().length() >= 6) {

                        login(userNumber.getText().toString(), userPassword.getText().toString());

                    } else {
                        userPassword.setError(getString(R.string.validPasswordAlert));
                    }
                } else {
                    userPassword.setError(getString(R.string.passwordAlert));
                }
            } else {
                userNumber.setError(getString(R.string.alert_email_valid));

            }
        } else {
            userNumber.setError(getString(R.string.alert_email));
        }
    }


    void login(String phone, String password) {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAppKey("123456");
        loginRequest.setMethod("cleaner_login");
        loginRequest.setPassword(password);
        loginRequest.setPhoneNumber(phone);


        apiUtility.userLogin(LoginActivity.this, loginRequest, true, new APIUtility.APIResponseListener<LoginResponseWrapper>() {
            @Override
            public void onReceiveResponse(LoginResponseWrapper response) {
                if (response != null) {

                    SetUserDetails(response.getResponse().getResult().get(0).getEmail(), response.getResponse().getResult().get(0).getPhoneNumber(), response.getResponse().getResult().get(0).getFirstName(), response.getResponse().getResult().get(0).getUserId(), response.getResponse().getResult().get(0).getLastName());

                    Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(LoginActivity.this, getString(R.string.VolleyError));
                userPassword.setText("");
            }

            @Override
            public void onStatusFalse(LoginResponseWrapper response) {
                userPassword.setText("");
                CommonUtils.alert(LoginActivity.this, response.getResponse().getMessage());
            }
        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}


