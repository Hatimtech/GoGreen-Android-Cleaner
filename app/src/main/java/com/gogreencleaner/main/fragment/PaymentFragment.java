package com.gogreencleaner.main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.gogreencleaner.main.R;
import com.gogreencleaner.main.Utils.CommonUtils;
import com.gogreencleaner.main.Utils.Constants;
import com.gogreencleaner.main.Utils.Preferences.PrefEntity;
import com.gogreencleaner.main.Utils.Preferences.Preferences;
import com.gogreencleaner.main.Volley.APIUtility;
import com.gogreencleaner.main.adapter.PaymentAdapter;
import com.gogreencleaner.main.adapter.StreetAdapter;
import com.gogreencleaner.main.model.PaymentAssignment.REQUEST.PaymentAssignmentRequest;
import com.gogreencleaner.main.model.PaymentAssignment.RESPONSE.PaymentAssignmentResponseResult;
import com.gogreencleaner.main.model.PaymentAssignment.RESPONSE.PaymentAssignmentResponseWrapper;
import com.gogreencleaner.main.screens.AssignmentsActivity;

import java.util.ArrayList;
import java.util.List;

public class PaymentFragment extends Fragment {

    List<PaymentAssignmentResponseResult> paymentList;
    RecyclerView paymentView;
    public static PaymentAdapter paymentAdapter;
    SwipeRefreshLayout refreshLayout;
    LinearLayout noPaymentJob;
    APIUtility apiUtility;
    List<String> streetList = new ArrayList<>();
    StreetAdapter streetAdapter;
    Spinner spinner;
    LinearLayout filterLayout;
    android.content.Context  context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.past_job_fragment, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        paymentView = (RecyclerView) view.findViewById(R.id.past_job);
        filterLayout=(LinearLayout)view.findViewById(R.id.filter);
        noPaymentJob = (LinearLayout) view.findViewById(R.id.no_past_payment_job);
        spinner = (Spinner) view.findViewById(R.id.selected_street);
        context=getActivity();
        apiUtility = new APIUtility(getActivity());
        InitViews();
        return view;
    }

    private void InitViews() {
        paymentList = new ArrayList<>();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        paymentView.setLayoutManager(linearLayoutManager);
        paymentAdapter = new PaymentAdapter(this, paymentList);
        paymentView.setAdapter(paymentAdapter);
        getPaymentAssignment(true);
        refreshList();

        streetAdapter = new StreetAdapter(context, streetList);
        spinner.setAdapter(streetAdapter);
        spinner.setSelection(Preferences.getPreference_intNumber(context,PrefEntity.NUMBER));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (paymentAdapter != null)
                    paymentAdapter.getFilter().filter(streetList.get(position));
                    Preferences.setPreference_int(context,PrefEntity.NUMBER,position);

                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void getPaymentAssignment(boolean isProgress) {
        PaymentAssignmentRequest paymentAssignmentRequest = new PaymentAssignmentRequest();
        paymentAssignmentRequest.setAppKey(Constants.APPKEY);
        paymentAssignmentRequest.setMethod("get_collected_payment_detail");
        paymentAssignmentRequest.setCleanerId(Preferences.getPreference(context.getApplicationContext(), PrefEntity.USERID));

        apiUtility.getPaymentAssignment(context, paymentAssignmentRequest, isProgress, new APIUtility.APIResponseListener<PaymentAssignmentResponseWrapper>() {
            @Override
            public void onReceiveResponse(PaymentAssignmentResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                if (response != null) {
                    if (response.getResponse().getResult().size() > 0) {


                        noPaymentJob.setVisibility(View.GONE);
                        paymentView.setVisibility(View.VISIBLE);
                        filterLayout.setVisibility(View.VISIBLE);
                        paymentList.clear();
                        paymentList.addAll(response.getResponse().getResult());
                        streetList.clear();
                        for (int i = 0; i < response.getResponse().getResult().size(); i++) {
                            if (streetList.contains(response.getResponse().getResult().get(i).getStreet_name().trim())) {

                            } else {
                                streetList.add(response.getResponse().getResult().get(i).getStreet_name().trim());
                            }

                        }

                        streetAdapter = new StreetAdapter(context, streetList);
                        spinner.setAdapter(streetAdapter);
                        spinner.setSelection(Preferences.getPreference_intNumber(context,PrefEntity.NUMBER));
                        paymentAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onResponseFailed() {
                refreshLayout.setRefreshing(false);
                noPaymentJob.setVisibility(View.VISIBLE);
                paymentView.setVisibility(View.GONE);
                filterLayout.setVisibility(View.GONE);
                CommonUtils.alert(context, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(PaymentAssignmentResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                noPaymentJob.setVisibility(View.VISIBLE);
                filterLayout.setVisibility(View.GONE);
                paymentView.setVisibility(View.GONE);

            }
        });


    }

    void refreshList() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                if (CommonUtils.isNetworkAvailable(context)) {
                    getPaymentAssignment(false);
                } else {
                    refreshLayout.setRefreshing(false);
                    CommonUtils.displayNetworkAlert(context, false);
                }
            }
        });

        refreshLayout.setColorSchemeColors(Color.GREEN, Color.BLUE, Color.RED, Color.CYAN, Color.GRAY);
        refreshLayout.setDistanceToTriggerSync(100);// in dips
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

    }

    void resetSearch() {
        if (AssignmentsActivity.searchView != null) {
            AssignmentsActivity.searchView.setQuery("", false);
            AssignmentsActivity.searchView.clearFocus();
        }
    }

    @Override
    public void onResume() {
//        spinner.setSelection(Preferences.getPreference_int(context,PrefEntity.NUMBER));
        super.onResume();
    }
}
