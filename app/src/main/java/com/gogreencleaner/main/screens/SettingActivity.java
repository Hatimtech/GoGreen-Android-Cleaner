package com.gogreencleaner.main.screens;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.gogreencleaner.main.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by AKASH SHUKLA on 28-May-18.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout updateInfo, contactAdmin, changePassword, logout;
    private Toolbar toolbar;
    private AppCompatTextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        InitView();

        PhoneStateListener phoneListener = new PhoneStateListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void InitView() {

        updateInfo = (RelativeLayout) findViewById(R.id.updateInfo);
        changePassword = (RelativeLayout) findViewById(R.id.change_password);
        contactAdmin = (RelativeLayout) findViewById(R.id.contact_admin);
        logout = (RelativeLayout) findViewById(R.id.logout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) findViewById(R.id.title);
        title.setText(R.string.Settings);
        setSupportActionBar(toolbar);
        updateInfo.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        contactAdmin.setOnClickListener(this);
        logout.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.updateInfo:
                Intent intent2 = new Intent(SettingActivity.this, UpdateInfoActivity.class);
                startActivity(intent2);
                break;


            case R.id.change_password:
                Intent intent1 = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                startActivity(intent1);
                break;


            case R.id.contact_admin:
                Intent i = new Intent(Intent.ACTION_DIAL);
                String p = "tel:" +"971545866100";
                i.setData(Uri.parse(p));
                startActivity(i);
                break;


            case R.id.logout:

                showDailog();

                break;
        }


    }

    void showDailog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setCancelable(false);
        builder.setTitle("Go Green Cleaner");
        builder.setMessage(R.string.logoutAlert);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showProgressDialog();
                ClearUserDetails();
                Intent logoutIntent = new Intent(SettingActivity.this, LoginActivity.class);
                cancelProgressDialog();
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
