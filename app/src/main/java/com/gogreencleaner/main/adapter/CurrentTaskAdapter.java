package com.gogreencleaner.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gogreencleaner.main.R;
import com.gogreencleaner.main.model.CurrentAssignment.RESPONSE.CurrentAssignmentResponseResult;
import com.gogreencleaner.main.screens.AssignmentActivity;

import java.util.ArrayList;
import java.util.List;

public class CurrentTaskAdapter extends RecyclerView.Adapter<CurrentTaskAdapter.CurrentTaskViewHolder> implements Filterable {

    private Context context;
    private List<CurrentAssignmentResponseResult> currentJobList;
    private List<CurrentAssignmentResponseResult> mFilterList;
    ValueFilter valueFilter;


    public CurrentTaskAdapter(Context context, List<CurrentAssignmentResponseResult> currentJob) {
        this.context = context;
        this.currentJobList = currentJob;
        this.mFilterList=currentJob;
    }

    @Override
    public CurrentTaskAdapter.CurrentTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.current_order_list, parent, false);
        return new CurrentTaskAdapter.CurrentTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CurrentTaskAdapter.CurrentTaskViewHolder holder, final int position) {

        holder.apartmentNumber.setText(currentJobList.get(position).getApartmentNumber());
        holder.parkingNumber.setText(currentJobList.get(position).getParkingNumber());
        holder.carModel.setText(currentJobList.get(position).getCarBrand() + ", " + currentJobList.get(position).getCarModel());
        holder.orderId.setText(currentJobList.get(position).getOrderId());
        holder.carColor.setText(currentJobList.get(position).getCarColor());
        holder.plateNumber.setText(currentJobList.get(position).getPlateNumber());
        try {
            if (currentJobList.get(position).getService_type().equals("2"))
                holder.servingNumber.setText("Exterior");
            else
                holder.servingNumber.setText("Exterior,Interior");
        } catch (Exception e) {

        }


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), AssignmentActivity.class);
                intent.putExtra("services_type", currentJobList.get(position).getService_type());
                if (currentJobList.get(position).getService_type().equals("2"))
                    intent.putExtra("services_type_name", "Exterior");
                else
                    intent.putExtra("services_type_name", "Exterior,Interior");
                intent.putExtra("userID", currentJobList.get(position).getUser_id());
                intent.putExtra("purchase_date", currentJobList.get(position).getPurchase_date());
                intent.putExtra("packaage_id", currentJobList.get(position).getBooked_package_id());
                intent.putExtra("expire_date", currentJobList.get(position).getExpiry_date());
                intent.putExtra("package_type", currentJobList.get(position).getPackageType());
                intent.putExtra("car_id", currentJobList.get(position).getCarId());
                intent.putExtra("car_type", currentJobList.get(position).getCar_type());
                intent.putExtra("day", currentJobList.get(position).getDays());
                intent.putExtra("teamId", currentJobList.get(position).getTeamId());
                intent.putExtra("Payment", currentJobList.get(position).getPaymentKey());
                intent.putExtra("services_date", currentJobList.get(position).getService_date());
                if (currentJobList.get(position).getPackageType().equals("once"))
                    intent.putExtra("services_date_days", currentJobList.get(position).getService_date());
                else
                    intent.putExtra("services_date_days", currentJobList.get(position).getDays());
                intent.putExtra("carmodel", currentJobList.get(position).getCarBrand() + ", " + currentJobList.get(position).getCarModel());
                intent.putExtra("color", currentJobList.get(position).getCarColor());
                intent.putExtra("parking", currentJobList.get(position).getParkingNumber());
                intent.putExtra("plateNumber", currentJobList.get(position).getPlateNumber());


                intent.putExtra("userName", currentJobList.get(position).getUser_Name());
                intent.putExtra("userNumber", currentJobList.get(position).getUser_phone());
                intent.putExtra("userCity", currentJobList.get(position).getUser_city() + "," + currentJobList.get(position).getUser_locality() + "," + currentJobList.get(position).getUser_street());
                intent.putExtra("apartmentNumber", currentJobList.get(position).getApartmentNumber());
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });


        if (currentJobList.get(position).isExpanded()) {

            holder.servingTypeLayout.setVisibility(View.GONE);
            holder.parkingSlotLayout.setVisibility(View.GONE);
            holder.colorLayout.setVisibility(View.GONE);
            holder.plateLayout.setVisibility(View.GONE);
            holder.viewMore.setText(R.string.viewMore);


        } else {

            holder.servingTypeLayout.setVisibility(View.VISIBLE);
            holder.parkingSlotLayout.setVisibility(View.VISIBLE);
            holder.colorLayout.setVisibility(View.VISIBLE);
            holder.plateLayout.setVisibility(View.VISIBLE);
            holder.viewMore.setText(R.string.viewLess);

        }


        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentJobList.get(position).isExpanded()) {
                    currentJobList.get(position).setExpanded(false);
                    notifyItemChanged(position);
                } else {
                    currentJobList.get(position).setExpanded(true);
                    notifyItemChanged(position);

                }

            }


        });
    }


    @Override
    public int getItemCount() {
        return currentJobList.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public class CurrentTaskViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout servingTypeLayout, colorLayout, parkingSlotLayout, layout, plateLayout;
        private TextView orderId, viewMore, carModel, carColor, parkingNumber, apartmentNumber, servingNumber, plateNumber;

        public CurrentTaskViewHolder(View itemView) {
            super(itemView);

            servingTypeLayout = (LinearLayout) itemView.findViewById(R.id.servicingtype_layout);
            colorLayout = (LinearLayout) itemView.findViewById(R.id.color_layout);
            parkingSlotLayout = (LinearLayout) itemView.findViewById(R.id.parking_layout);
            orderId = (TextView) itemView.findViewById(R.id.car_orderId);
            viewMore = (TextView) itemView.findViewById(R.id.view_more);
            carModel = (TextView) itemView.findViewById(R.id.car_model);
            carColor = (TextView) itemView.findViewById(R.id.color);
            parkingNumber = (TextView) itemView.findViewById(R.id.car_parking_No);
            apartmentNumber = (TextView) itemView.findViewById(R.id.apart_number);
            servingNumber = (TextView) itemView.findViewById(R.id.maintainType);
            plateNumber = (TextView) itemView.findViewById(R.id.car_plate_No);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            plateLayout = (LinearLayout) itemView.findViewById(R.id.plate_layout);


        }
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            CurrentAssignmentResponseResult filterHash;

            if (constraint != null && constraint.length() > 0) {
                List<CurrentAssignmentResponseResult> filterList = new ArrayList<CurrentAssignmentResponseResult>();

                for (int i = 0; i < mFilterList.size(); i++) {
                    if (mFilterList.get(i).getUser_street().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterHash = mFilterList.get(i);
                        filterList.add(filterHash);
                    }

                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mFilterList.size();
                results.values = mFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            currentJobList = (ArrayList<CurrentAssignmentResponseResult>) results.values;
            notifyDataSetChanged();
        }
    }
}
