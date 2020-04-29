package com.gogreencleaner.main.screens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gogreencleaner.main.R;
import com.gogreencleaner.main.Utils.Preferences.PrefEntity;
import com.gogreencleaner.main.Utils.Preferences.Preferences;

public class BaseActivity extends AppCompatActivity {

    public static Context mContext;
    private ProgressDialog dialog;

    public FragmentManager fragManager;
    public FragmentTransaction fragTransaction;
    public long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;
        fragManager = getSupportFragmentManager();
        fragTransaction = fragManager.beginTransaction();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void showProgressDialog() {
        dialog = new ProgressDialog(BaseActivity.this);
        dialog.setMessage(getString(R.string.prg_dial_pleaseWait));
        dialog.setCancelable(false);
        dialog.show();
    }

    public void showProgressDialog(String Message) {
        dialog = new ProgressDialog(BaseActivity.this);
        dialog.setMessage(Message);
        dialog.setCancelable(false);
        dialog.show();
    }

    //This function is cancel progress dialog
    public void cancelProgressDialog() {
        dialog.dismiss();
    }

    // Avoid Multiple click
    public boolean issingleClick() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return false;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        return true;
    }

    public void hideKeyboard() {

        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm.isAcceptingText()) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }

    public void callAlertDialog(int message, Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(getResources().getString(R.string.app_name))
                .setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void callAlertDialog(String message, Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(getResources().getString(R.string.app_name))
                .setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public void SetUserDetails(String email, String phone, String name, String userID,String lastName) {

        Preferences.setPreference(getApplicationContext(), PrefEntity.USER_EMAIL, email);
        Preferences.setPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER, phone);
        Preferences.setPreference(getApplicationContext(), PrefEntity.USER_NAME, name);
        Preferences.setPreference(getApplicationContext(), PrefEntity.USERID, userID);
        Preferences.setPreference(getApplicationContext(),PrefEntity.LAST_NAME,lastName);
        Preferences.setPreference_int(getApplicationContext(), PrefEntity.IS_LOGIN, 1);

    }

    public void ClearUserDetails() {
        Preferences.removePreference(getApplicationContext(), PrefEntity.USER_EMAIL);
        Preferences.removePreference(getApplicationContext(), PrefEntity.USER_NAME);
        Preferences.removePreference(getApplicationContext(), PrefEntity.PHONE_NUMBER);
        Preferences.removePreference(getApplicationContext(), PrefEntity.USERID);
        Preferences.removePreference(getApplicationContext(), PrefEntity.IS_LOGIN);
        Preferences.removePreference(getApplicationContext(),PrefEntity.LAST_NAME);
        Preferences.removePreference(getApplicationContext(),PrefEntity.IMAGE);
        Preferences.removePreference(getApplicationContext(),PrefEntity.NUMBER);
        Preferences.removePreference(getApplicationContext(),PrefEntity.NUM);
    }
}
