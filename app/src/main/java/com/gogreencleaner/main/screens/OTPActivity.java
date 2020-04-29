package com.gogreencleaner.main.screens;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.chaos.view.PinView;

import com.gogreencleaner.main.R;
import com.gogreencleaner.main.Utils.CommonUtils;
import com.gogreencleaner.main.Utils.Constants;
import com.gogreencleaner.main.Utils.Preferences.PrefEntity;
import com.gogreencleaner.main.Utils.Preferences.Preferences;
import com.gogreencleaner.main.Volley.APIUtility;
import com.gogreencleaner.main.model.FgPassword.REQUEST.FgPasswordRequest;
import com.gogreencleaner.main.model.FgPassword.RESPONSE.FgPasswordResponseWrapper;
import com.sinch.verification.Config;
import com.sinch.verification.InitiationResult;
import com.sinch.verification.SinchVerification;
import com.sinch.verification.Verification;
import com.sinch.verification.VerificationListener;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by AKASH SHUKLA on 28-May-18.
 */

public class OTPActivity extends BaseActivity implements View.OnClickListener {
    private Button virefy;
    private TextView resend_OTP, phone_number;
    private Toolbar toolbar;
    private AppCompatTextView title;
    APIUtility apiUtility;
    private PinView pinView;
    private ImageView back;
    Verification verification;
    int isOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_screen_activity);
        apiUtility = new APIUtility(OTPActivity.this);
        SendOTP(Constants.COUNTYCODE+ Preferences.getPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER));
        InitViews();


    }


    public void InitViews() {
        back = (ImageView) findViewById(R.id.back);
        pinView = (PinView) findViewById(R.id.pinview);
        resend_OTP = (TextView) findViewById(R.id.resendOTP);
        virefy = (Button) findViewById(R.id.verifyButton);
        phone_number = (TextView) findViewById(R.id.phone_number);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) toolbar.findViewById(R.id.title);
        title.setText(" ");
        phone_number.setText(Constants.COUNTYCODE+ Preferences.getPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER));
        setSupportActionBar(toolbar);
        back.setOnClickListener(this);
        virefy.setOnClickListener(this);
        resend_OTP.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.verifyButton:
                verification.verify(pinView.getText().toString());
                break;

            case R.id.resendOTP:

                SendOTP(Constants.COUNTYCODE + Preferences.getPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER));
                break;


            case R.id.back:
                finish();
                break;
        }
    }


    void SendOTP(final String phoneNumberString) {
        apiUtility.showDialog(OTPActivity.this, true);
        Config config = SinchVerification.config().applicationKey(Constants.APPLICATION_KEY).context(getApplicationContext()).build();
        VerificationListener listener = new VerificationListener() {
            @Override
            public void onInitiated(InitiationResult initiationResult) {
                apiUtility.dismissDialog(true);
                isOTP=1;
            }

            @Override
            public void onInitiationFailed(Exception e) {
                apiUtility.dismissDialog(true);

            }

            @Override
            public void onVerified() {

                phoneVerified(Preferences.getPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER));

            }

            @Override
            public void onVerificationFailed(Exception e) {

                if (isOTP == 1) {
                    isOTP=0;
                } else {
                CommonUtils.alert(OTPActivity.this, getString(R.string.validOtp));
                }
            }

            @Override
            public void onVerificationFallback() {

            }
        };

        verification = SinchVerification.createSmsVerification(config, phoneNumberString, listener);
        verification.initiate();


    }

    void phoneVerified(String phoneNumber) {

        FgPasswordRequest fgPasswordRequest = new FgPasswordRequest();
        fgPasswordRequest.setAppKey(Constants.APPKEY);
        fgPasswordRequest.setMethod("get_id_by_number");
        fgPasswordRequest.setPhoneNumber(phoneNumber);

        apiUtility.forgetPassword(OTPActivity.this, fgPasswordRequest, true, new APIUtility.APIResponseListener<FgPasswordResponseWrapper>() {
            @Override
            public void onReceiveResponse(FgPasswordResponseWrapper response) {
                if (response != null) {

                    Preferences.setPreference(getApplicationContext(),PrefEntity.USER_NAME,response.getResponse().getResult().get(0).getName());
                    Preferences.setPreference(getApplicationContext(),PrefEntity.USERID,response.getResponse().getResult().get(0).getUserID());
                    Preferences.setPreference(getApplicationContext(),PrefEntity.PHONE_NUMBER,response.getResponse().getResult().get(0).getPhoneNumber());
                    Intent intent = new Intent(OTPActivity.this, UpdatePasswordActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }

            @Override
            public void onResponseFailed() {


                CommonUtils.alert(OTPActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(FgPasswordResponseWrapper response) {

                CommonUtils.alert(OTPActivity.this, response.getResponse().getMessage());
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
