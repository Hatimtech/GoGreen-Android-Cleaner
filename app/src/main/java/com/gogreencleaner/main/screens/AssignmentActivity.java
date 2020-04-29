package com.gogreencleaner.main.screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gogreencleaner.main.R;
import com.gogreencleaner.main.Utils.CommonUtils;
import com.gogreencleaner.main.Utils.Constants;
import com.gogreencleaner.main.Utils.Preferences.PrefEntity;
import com.gogreencleaner.main.Utils.Preferences.Preferences;
import com.gogreencleaner.main.Volley.APIUtility;
import com.gogreencleaner.main.adapter.NotAttendedRegionFromCleaner;
import com.gogreencleaner.main.model.AssignmentAttended.REQUEST.AssignmentAttendedRequest;
import com.gogreencleaner.main.model.AssignmentAttended.RESPONSE.AssignmentAttendedResponseWrapper;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by AKASH SHUKLA on 28-May-18.
 */

public class AssignmentActivity extends BaseActivity implements View.OnClickListener,NotAttendedRegionFromCleaner.ReasonCallback {

    private Toolbar toolbar;
    private AppCompatTextView title;
    private TextView attended, notAttented, carModel, color, parkingNumber,plateNumber, apartmentNUmber, userNmae, location, userNumber, servingType, days, carType;
    APIUtility apiUtility;
    NotAttendedRegionFromCleaner regionFromCleanerAdapter;
    List<String> list;
    int position;
    boolean isReason=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        apiUtility = new APIUtility(AssignmentActivity.this);
        InitView();
    }

    private void InitView() {
        plateNumber=(TextView) findViewById(R.id.car_palate_No);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) findViewById(R.id.title);
        attended = (TextView) findViewById(R.id.attended);
        title.setText(R.string.assignment);
        notAttented = (TextView) findViewById(R.id.notattended);
        carModel = (TextView) findViewById(R.id.car_model);
        color = (TextView) findViewById(R.id.car_color_name);
        parkingNumber = (TextView) findViewById(R.id.car_parking_No);
        apartmentNUmber = (TextView) findViewById(R.id.apartment_number);
        userNmae = (TextView) findViewById(R.id.user_name);
        location = (TextView) findViewById(R.id.adress);
        userNumber = (TextView) findViewById(R.id.user_mobile);
        servingType = (TextView) findViewById(R.id.mentain_type);
        days = (TextView) findViewById(R.id.calendar);
        carType = (TextView) findViewById(R.id.car_type_name);
        attended.setOnClickListener(this);
        notAttented.setOnClickListener(this);
        setTextData();
    }


    void setTextData() {
        servingType.setText(getIntent().getStringExtra("services_type_name"));
        days.setText(getIntent().getStringExtra("services_date_days"));
        carType.setText(getIntent().getStringExtra("car_type"));
        carModel.setText(getIntent().getStringExtra("carmodel"));
        color.setText(getIntent().getStringExtra("color"));
        parkingNumber.setText(getIntent().getStringExtra("parking"));
        userNmae.setText(getIntent().getStringExtra("userName"));
        userNumber.setText(getIntent().getStringExtra("userNumber"));
        location.setText(getIntent().getStringExtra("userCity"));
        apartmentNUmber.setText(getIntent().getStringExtra("apartmentNumber"));
        plateNumber.setText(getIntent().getStringExtra("plateNumber"));
        list = new ArrayList<>();
        list.add("Car not found in parking spot");
        list.add("Car not moving");
        list.add("On Customer Request");
        list.add("Car wash not done by cleaner");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attended:
              attendedDailog();
                break;

            case R.id.notattended:
                showDialog(list);
                break;
        }
    }


    @Override
    public void itemClick(int pos) {
        position= pos;
        isReason=true;
    }

    void sendAttendedStatus(String attended, String feeedback) {
        AssignmentAttendedRequest attendedRequest = new AssignmentAttendedRequest();
        attendedRequest.setAppKey(Constants.APPKEY);
        attendedRequest.setCleanerId(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));
        attendedRequest.setMethod("cleaner_job_done_detail");
        attendedRequest.setFeedback(feeedback);
        attendedRequest.setAttended(attended);
        attendedRequest.setBooked_package_id(getIntent().getStringExtra("packaage_id"));
        attendedRequest.setCar_type(getIntent().getStringExtra("car_type"));
        attendedRequest.setDays(getIntent().getStringExtra("day"));
        attendedRequest.setCarID(getIntent().getStringExtra("car_id"));
        attendedRequest.setExpiry_date(getIntent().getStringExtra("expire_date"));
        attendedRequest.setTeamId(getIntent().getStringExtra("teamId"));
        attendedRequest.setPackageType(getIntent().getStringExtra("package_type"));
        attendedRequest.setPaymentKey(getIntent().getStringExtra("Payment"));
        attendedRequest.setPurchase_date(getIntent().getStringExtra("purchase_date"));
        attendedRequest.setUser_id(getIntent().getStringExtra("userID"));
        attendedRequest.setService_date(getIntent().getStringExtra("services_date"));
        attendedRequest.setServices_type(getIntent().getStringExtra("services_type"));


        apiUtility.sendAttendedStatus(AssignmentActivity.this, attendedRequest, true, new APIUtility.APIResponseListener<AssignmentAttendedResponseWrapper>() {
            @Override
            public void onReceiveResponse(AssignmentAttendedResponseWrapper response) {
                if (response != null) {
                    Intent intent = new Intent(AssignmentActivity.this, AssignmentsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(AssignmentActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(AssignmentAttendedResponseWrapper response) {
                CommonUtils.alert(AssignmentActivity.this, response.getResponse().getMessage());
            }
        });
    }

    private void showDialog(List<String> reason) {
        final Dialog dialog = new Dialog(AssignmentActivity.this);
        dialog.setContentView(R.layout.dailog_car_list);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.car_list_recycle);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AssignmentActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        regionFromCleanerAdapter = new NotAttendedRegionFromCleaner(reason, this);
        recyclerView.setAdapter(regionFromCleanerAdapter);


        final TextView dialogButton = dialog.findViewById(R.id.dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isReason){
                    dialog.dismiss();
                    sendAttendedStatus("2",list.get(position));

                }else{

                    CommonUtils.alert(AssignmentActivity.this,"Please select the reason ");
                }

            }
        });

        final TextView dailogCancle=dialog.findViewById(R.id.dialog_cancle);
        dailogCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

   void attendedDailog(){
       AlertDialog.Builder builder=new AlertDialog.Builder(this);
       builder.setTitle(R.string.app_name);
       builder.setMessage("Are you attending this task?");
       builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               sendAttendedStatus("1", "");
           }
       });
       builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
           }
       });
       builder.setCancelable(false);
       builder.create();
       builder.show();
   }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(AssignmentActivity.this,AssignmentsActivity.class);
        startActivity(intent);
        finish();
    }
}
