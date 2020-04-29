package com.gogreencleaner.main.screens;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gogreencleaner.main.R;
import com.gogreencleaner.main.fragment.CurrentJobFragment;
import com.gogreencleaner.main.fragment.PastJobFragment;
import com.gogreencleaner.main.fragment.PaymentFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class AssignmentsActivity extends BaseActivity implements View.OnClickListener {

    private TextView payment, past, current;
    private FrameLayout frameLayout;
    private LinearLayout currentLayout, PastLayout, PaymentLayout;
    private Toolbar toolbar;
    public static SearchView searchView;
    private AppCompatTextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        InitViews();
    }

    private void InitViews() {
        payment = (TextView) findViewById(R.id.payment);
        past = (TextView) findViewById(R.id.past);
        current = (TextView) findViewById(R.id.current);
        PastLayout = (LinearLayout) findViewById(R.id.past_layout);
        currentLayout = (LinearLayout) findViewById(R.id.current_layout);
        PaymentLayout = (LinearLayout) findViewById(R.id.payment_layout);
        frameLayout = (FrameLayout) findViewById(R.id.container);
        title=(AppCompatTextView)findViewById(R.id.title);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        title.setText(R.string.Assignments);
        PastLayout.setOnClickListener(this);
        currentLayout.setOnClickListener(this);
        PaymentLayout.setOnClickListener(this);
        replaceFragment(new CurrentJobFragment());
        setSupportActionBar(toolbar);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.current_layout:

                currentLayout.setBackgroundResource(R.drawable.assignment_drawable_green);
                current.setTextColor(getResources().getColor(R.color.white));
                replaceFragment(new CurrentJobFragment());
                PastLayout.setBackgroundResource(R.drawable.assignment_drawalbe);
                PaymentLayout.setBackgroundResource(R.drawable.assignment_drawalbe);
                payment.setTextColor(getResources().getColor(R.color.textcolor));
                past.setTextColor(getResources().getColor(R.color.textcolor));

                break;


            case R.id.past_layout:

                past.setTextColor(getResources().getColor(R.color.white));
                current.setTextColor(getResources().getColor(R.color.textcolor));
                payment.setTextColor(getResources().getColor(R.color.textcolor));
                PastLayout.setBackgroundResource(R.drawable.assignment_drawable_green);
                currentLayout.setBackgroundResource(R.drawable.assignment_drawalbe);
                PaymentLayout.setBackgroundResource(R.drawable.assignment_drawalbe);
                replaceFragment(new PastJobFragment());
                break;


            case R.id.payment_layout:

                current.setTextColor(getResources().getColor(R.color.textcolor));
                payment.setTextColor(getResources().getColor(R.color.white));
                past.setTextColor(getResources().getColor(R.color.textcolor));
                PaymentLayout.setBackgroundResource(R.drawable.assignment_drawable_green);
                PastLayout.setBackgroundResource(R.drawable.assignment_drawalbe);
                currentLayout.setBackgroundResource(R.drawable.assignment_drawalbe);
                replaceFragment(new PaymentFragment());
                break;
        }

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
