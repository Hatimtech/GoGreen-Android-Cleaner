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
import com.gogreencleaner.main.adapter.PastTaskAdapter;
import com.gogreencleaner.main.adapter.StreetAdapter;
import com.gogreencleaner.main.model.PastAssignment.REQUEST.PastAssignmentRequest;
import com.gogreencleaner.main.model.PastAssignment.RESPONSE.PastAssignmentResponseResult;
import com.gogreencleaner.main.model.PastAssignment.RESPONSE.PastAssignmentResponseWrapper;
import com.gogreencleaner.main.screens.AssignmentsActivity;

import java.util.ArrayList;
import java.util.List;


public class PastJobFragment extends Fragment {

    List<PastAssignmentResponseResult> pastJobList;
    RecyclerView pastTaskListView;
    public static PastTaskAdapter pastTaskAdapter;
    SwipeRefreshLayout refreshLayout;
    LinearLayout noPastJob;
    APIUtility apiUtility;
    List<String>  streetList=new ArrayList<>();
    StreetAdapter streetAdapter;
    Spinner spinner;
    LinearLayout filterLayout;
    android.content.Context  context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.past_job_fragment, container, false);
        pastTaskListView = (RecyclerView) view.findViewById(R.id.past_job);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        noPastJob = (LinearLayout) view.findViewById(R.id.no_past_payment_job);
        spinner=(Spinner)view.findViewById(R.id.selected_street);
        filterLayout=(LinearLayout)view.findViewById(R.id.filter);
        context=getActivity();
        apiUtility = new APIUtility(getActivity());
        InitView();
        return view;
    }


    private void InitView() {
        pastJobList = new ArrayList<>();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        pastTaskListView.setLayoutManager(linearLayoutManager);
        pastTaskAdapter = new PastTaskAdapter(PastJobFragment.this, pastJobList);
        pastTaskListView.setAdapter(pastTaskAdapter);
        getPastAssignment(true);
        refreshList();

        streetAdapter=new StreetAdapter(context,streetList);
        spinner.setAdapter(streetAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (PastJobFragment.pastTaskAdapter != null)
                    PastJobFragment.pastTaskAdapter .getFilter().filter(streetList.get(position));
                Preferences.setPreference_int(context,PrefEntity.NUM,position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


  public void getPastAssignment(boolean isProgress) {
        PastAssignmentRequest pastAssignmentRequest = new PastAssignmentRequest();
        pastAssignmentRequest.setAppKey(Constants.APPKEY);
        pastAssignmentRequest.setMethod("get_past_task");
        pastAssignmentRequest.setCleanerId(Preferences.getPreference(context.getApplicationContext(), PrefEntity.USERID));

        apiUtility.getPastAssignment(context, pastAssignmentRequest, isProgress, new APIUtility.APIResponseListener<PastAssignmentResponseWrapper>() {
            @Override
            public void onReceiveResponse(PastAssignmentResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                if (response != null) {
                    if (response.getResponse().getResult().size() > 0) {
//                        resetSearch();
                        noPastJob.setVisibility(View.GONE);
                        pastTaskListView.setVisibility(View.VISIBLE);
                        filterLayout.setVisibility(View.VISIBLE);
                        pastJobList.clear();
                        pastJobList.addAll(response.getResponse().getResult());
                        streetList.clear();
                        for(int i=0;i<response.getResponse().getResult().size();i++) {
                            if(streetList.contains(response.getResponse().getResult().get(i).getStreetName())){

                            }else{
                                streetList.add(response.getResponse().getResult().get(i).getStreetName());
                            }
                        }
                        streetAdapter=new StreetAdapter(context,streetList);
                        spinner.setAdapter(streetAdapter);
                        spinner.setSelection(Preferences.getPreference_intNumber(context,PrefEntity.NUM));
                        pastTaskAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onResponseFailed() {
                refreshLayout.setRefreshing(false);
                noPastJob.setVisibility(View.VISIBLE);
                pastTaskListView.setVisibility(View.GONE);
                filterLayout.setVisibility(View.GONE);
                CommonUtils.alert(context, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(PastAssignmentResponseWrapper response) {
                refreshLayout.setRefreshing(false);
                noPastJob.setVisibility(View.VISIBLE);
                filterLayout.setVisibility(View.GONE);
                pastTaskListView.setVisibility(View.GONE);
            }
        });
    }


    void refreshList() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                if (CommonUtils.isNetworkAvailable(context)) {
                    getPastAssignment(false);
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
}
