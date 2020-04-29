package com.gogreencleaner.main.screens;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gogreencleaner.main.R;
import com.gogreencleaner.main.Utils.CommonUtils;
import com.gogreencleaner.main.Utils.Constants;
import com.gogreencleaner.main.Utils.Preferences.PrefEntity;
import com.gogreencleaner.main.Utils.Preferences.Preferences;
import com.gogreencleaner.main.Volley.APIUtility;
import com.gogreencleaner.main.model.DashBoard.REQUEST.DashBoardRequest;
import com.gogreencleaner.main.model.DashBoard.RESPONSE.DashBoardResponseWrapper;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by AKASH SHUKLA on 28-May-18.
 */


public class DashBoardActivity extends BaseActivity implements View.OnClickListener {

    private ImageView setting,profile;
    private Toolbar toolbar;
    private AppCompatTextView title;
    private TextView todayTask, completedTask, remaingTask, checkAssignment, city, locality, street,name;
    private SwipeRefreshLayout refreshLayout;
    private RatingBar ratingBar;
    private CardView half;
    APIUtility apiUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        apiUtility = new APIUtility(DashBoardActivity.this);
        InitViews();

    }

    private void InitViews() {
        profile=(ImageView)findViewById(R.id.cl_image);
        city = (TextView) findViewById(R.id.cl_city_name);
        locality = (TextView) findViewById(R.id.cl_locality_name);
        street = (TextView) findViewById(R.id.cl_streetName);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        setting = (ImageView) findViewById(R.id.settings);
        title = (AppCompatTextView) findViewById(R.id.title);
        half=(CardView)findViewById(R.id.half2);
        checkAssignment = (TextView) findViewById(R.id.checkAssignment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        todayTask = (TextView) findViewById(R.id.todayTask);
        completedTask = (TextView) findViewById(R.id.completetask);
        remaingTask = (TextView) findViewById(R.id.remaing);
        ratingBar=(RatingBar)findViewById(R.id.ratingbar);
        name=(TextView) findViewById(R.id.cl_name);
        name.setText(Preferences.getPreference(getApplicationContext(),PrefEntity.USER_NAME) + " " +Preferences.getPreference(getApplicationContext(),PrefEntity.LAST_NAME));
        setting.setOnClickListener(this);
        checkAssignment.setOnClickListener(this);
        title.setText(R.string.dashBoard);
        half.setOnClickListener(this);

        getDashBoarddata(true);
        refreshList();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

           /* case R.id.checkAssignment:
                startActivity(new Intent(DashBoardActivity.this, AssignmentsActivity.class));
                break;*/


            case R.id.half2:
                startActivity(new Intent(DashBoardActivity.this, AssignmentsActivity.class));
                break;

            case R.id.settings:
                startActivity(new Intent(DashBoardActivity.this, SettingActivity.class));
                break;
        }


    }

    void refreshList() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                if (CommonUtils.isNetworkAvailable(DashBoardActivity.this)) {
                    getDashBoarddata(false);
                } else {
                    refreshLayout.setRefreshing(false);
                    CommonUtils.displayNetworkAlert(DashBoardActivity.this, false);
                }
            }
        });

        refreshLayout.setColorSchemeColors(Color.GREEN, Color.BLUE, Color.RED, Color.CYAN, Color.GRAY);
        refreshLayout.setDistanceToTriggerSync(100);// in dips
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

    }


    void getDashBoarddata(boolean isProgress) {
        DashBoardRequest dashBoardRequest = new DashBoardRequest();
        dashBoardRequest.setAppKey(Constants.APPKEY);
        dashBoardRequest.setMethod("cleaner_dashboard");
        dashBoardRequest.setCleanerId(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));

        apiUtility.getDashboardDetail(DashBoardActivity.this, dashBoardRequest, isProgress, new APIUtility.APIResponseListener<DashBoardResponseWrapper>() {
            @Override
            public void onReceiveResponse(DashBoardResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                if (response != null) {
                    todayTask.setText(response.getResponse().getResult().getToday_task());
                    completedTask.setText(response.getResponse().getResult().getCompleted_task());
                    remaingTask.setText(response.getResponse().getResult().getRemaining_task());
                    city.setText(response.getResponse().getResult().getBasicInfo().getCity());
                    locality.setText(", ");
                    street.setText(response.getResponse().getResult().getBasicInfo().getStreet());

                    try{
                            Preferences.setPreference(getApplicationContext(), PrefEntity.IMAGE, response.getResponse().getResult().getBasicInfo().getImage());
                        byte[] decodedString = Base64.decode(response.getResponse().getResult().getBasicInfo().getImage(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        profile.setImageBitmap(decodedByte);
                    }catch (Exception e){
                            profile.setImageResource(R.mipmap.placeholder);
                        }


                    try{
                        float rating=Float.valueOf(response.getResponse().getResult().getBasicInfo().getRating());
                        int count=Integer.parseInt(response.getResponse().getResult().getBasicInfo().getCount_who_rated());
                        float Avgrating=rating/count;
                        ratingBar.setRating(Avgrating);

                    }catch (Exception e){
                        ratingBar.setRating(0);
                    }

                }
            }

            @Override
            public void onResponseFailed() {
                refreshLayout.setRefreshing(false);
                CommonUtils.alert(DashBoardActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(DashBoardResponseWrapper response) {
                CommonUtils.alert(DashBoardActivity.this, response.getResponse().getMessage());
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDashBoarddata(true);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
