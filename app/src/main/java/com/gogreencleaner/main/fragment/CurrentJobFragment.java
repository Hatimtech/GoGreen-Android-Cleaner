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

import com.gogreencleaner.main.R;
import com.gogreencleaner.main.Utils.CommonUtils;
import com.gogreencleaner.main.Utils.Constants;
import com.gogreencleaner.main.Utils.Preferences.PrefEntity;
import com.gogreencleaner.main.Utils.Preferences.Preferences;
import com.gogreencleaner.main.Volley.APIUtility;
import com.gogreencleaner.main.adapter.CurrentTaskAdapter;
import com.gogreencleaner.main.adapter.StreetAdapter;
import com.gogreencleaner.main.model.CurrentAssignment.REQUEST.CurrentAssignmentRequest;
import com.gogreencleaner.main.model.CurrentAssignment.RESPONSE.CurrentAssignmentResponseResult;
import com.gogreencleaner.main.model.CurrentAssignment.RESPONSE.CurrentAssignmentResponseWrapper;

import java.util.ArrayList;
import java.util.List;

public class CurrentJobFragment extends Fragment {
    List<CurrentAssignmentResponseResult> currentJobList;
    RecyclerView currentTaskListView;
    public static CurrentTaskAdapter currentTaskAdapter;
    SwipeRefreshLayout refreshLayout;
    LinearLayout noCurrentJob;
    android.content.Context context;
    LinearLayout filterLayout;
    APIUtility apiUtility;
    List<String> streetList=new ArrayList<>();
    Spinner spinner;
    StreetAdapter streetAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_job_fragment, container, false);
        currentTaskListView = (RecyclerView) view.findViewById(R.id.current_job);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        noCurrentJob = (LinearLayout) view.findViewById(R.id.no_current_job);
        filterLayout=(LinearLayout)view.findViewById(R.id.filter);
        spinner=(Spinner)view.findViewById(R.id.selected_street);
        context=getActivity();
        apiUtility = new APIUtility(getActivity());
        InitView();
        return view;
    }


    private void InitView() {
        currentJobList = new ArrayList<>();

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        currentTaskListView.setLayoutManager(linearLayoutManager);
        currentTaskAdapter = new CurrentTaskAdapter(context, currentJobList);
        currentTaskListView.setAdapter(currentTaskAdapter);
          getCurrentAssignment(true);
          refreshList();
        streetAdapter=new StreetAdapter(context,streetList);
        spinner.setAdapter(streetAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (CurrentJobFragment.currentTaskAdapter!= null)
                    CurrentJobFragment.currentTaskAdapter.getFilter().filter(streetList.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    void getCurrentAssignment(boolean isProgress) {

        CurrentAssignmentRequest currentAssignmentRequest = new CurrentAssignmentRequest();
        currentAssignmentRequest.setAppKey(Constants.APPKEY);
        currentAssignmentRequest.setMethod("send_todays_order_to_cleaner");
        currentAssignmentRequest.setCleanerId(Preferences.getPreference(context.getApplicationContext(), PrefEntity.USERID));

        apiUtility.getCurrentAssignment(context, currentAssignmentRequest, isProgress, new APIUtility.APIResponseListener<CurrentAssignmentResponseWrapper>() {
            @Override
            public void onReceiveResponse(CurrentAssignmentResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                if (response != null) {
                    if (response.getResponse().getResult().size() > 0) {
                        noCurrentJob.setVisibility(View.GONE);
                        currentTaskListView.setVisibility(View.VISIBLE);
                        filterLayout.setVisibility(View.VISIBLE);
                        currentJobList.clear();
                        currentJobList.addAll(response.getResponse().getResult());
                        streetList.clear();
                        for(int i=0;i<response.getResponse().getResult().size();i++) {
                            if (streetList.contains(response.getResponse().getResult().get(i).getUser_street())) {

                            } else {
                                streetList.add(response.getResponse().getResult().get(i).getUser_street());
                            }
                        }
                        streetAdapter=new StreetAdapter(context,streetList);
                        spinner.setAdapter(streetAdapter);


                    } else {
                        currentJobList.clear();
                        currentTaskAdapter.notifyDataSetChanged();
                        filterLayout.setVisibility(View.GONE);
                        noCurrentJob.setVisibility(View.VISIBLE);
                        currentTaskListView.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onResponseFailed() {
                refreshLayout.setRefreshing(false);
                noCurrentJob.setVisibility(View.VISIBLE);
                filterLayout.setVisibility(View.GONE);
                currentTaskListView.setVisibility(View.GONE);
                CommonUtils.alert(context, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(CurrentAssignmentResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                noCurrentJob.setVisibility(View.VISIBLE);
                filterLayout.setVisibility(View.GONE);
                currentTaskListView.setVisibility(View.GONE);
            }
        });

    }


    void refreshList() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                if (CommonUtils.isNetworkAvailable(context)) {
                    getCurrentAssignment(false);
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




    }




