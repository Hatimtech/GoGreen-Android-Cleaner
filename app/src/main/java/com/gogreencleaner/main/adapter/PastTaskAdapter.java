package com.gogreencleaner.main.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gogreencleaner.main.R;
import com.gogreencleaner.main.Utils.CommonUtils;
import com.gogreencleaner.main.Utils.Constants;
import com.gogreencleaner.main.Volley.APIUtility;
import com.gogreencleaner.main.fragment.PastJobFragment;
import com.gogreencleaner.main.model.PastAssignment.RESPONSE.PastAssignmentResponseResult;
import com.gogreencleaner.main.model.UpdateAttendedStatus.REQUEST.UpdateAttendedStatusRequest;
import com.gogreencleaner.main.model.UpdateAttendedStatus.RESPONSE.UpdateAttendedStatusResponseWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PastTaskAdapter extends RecyclerView.Adapter<PastTaskAdapter.PastTaskViewHolder> implements Filterable {

    private PastJobFragment context;
    private List<PastAssignmentResponseResult> pastJobList;
    private List<PastAssignmentResponseResult> mFilterList;
    ValueFilter valueFilter;
    APIUtility apiUtility;


    public PastTaskAdapter(PastJobFragment context, List<PastAssignmentResponseResult> pastJobList) {
        this.context = context;
        this.pastJobList = pastJobList;
        this.mFilterList = pastJobList;
        apiUtility = new APIUtility(context.getActivity());
    }

    @Override
    public PastTaskAdapter.PastTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_order_list, parent, false);
        return new PastTaskAdapter.PastTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PastTaskAdapter.PastTaskViewHolder holder, final int position) {

        holder.apartmentNumber.setText(pastJobList.get(position).getApartmentNumber());
        holder.parkingNumber.setText(pastJobList.get(position).getParkingNumber());
        holder.carModel.setText(pastJobList.get(position).getCarBrand() + ", " + pastJobList.get(position).getCarModel());
        holder.orderId.setText(String.valueOf(Integer.parseInt(pastJobList.get(position).getOrderId()) + 100000));
        holder.carColor.setText(pastJobList.get(position).getCarColor());
        holder.plateNumber.setText(pastJobList.get(position).getPlateNumber());
        if (pastJobList.get(position).getServiceType().equals("2"))
            holder.servingNumber.setText("Exterior");

        else
            holder.servingNumber.setText("Exterior, Interior");
        try {
            float rating = Float.valueOf(pastJobList.get(position).getRating()) / Integer.parseInt(pastJobList.get(position).getCount_who_rated());
            holder.ratingBar.setRating(rating);
        } catch (Exception e) {
            holder.ratingBar.setRating(0);
        }


        if (pastJobList.get(position).getStatus().equals("1")) {
            holder.status.setText(R.string.completed);
            holder.curvedImage.setImageResource(R.mipmap.curved_edge);
            holder.status_layout.setBackgroundResource(R.drawable.order_confirm_text_drawable);
        } else {
            holder.status.setText(R.string.pending);
            holder.status_layout.setBackgroundResource(R.drawable.orderpendingdrawalbe);
            holder.curvedImage.setImageResource(R.mipmap.red_curved_edge);
        }
        if (pastJobList.get(position).isExpanded()) {
            holder.servingTypeLayout.setVisibility(View.VISIBLE);
            holder.parkingSlotLayout.setVisibility(View.VISIBLE);
            holder.colorLayout.setVisibility(View.VISIBLE);
            holder.ratingBarLayout.setVisibility(View.VISIBLE);
            holder.aprtNumberLayout.setVisibility(View.VISIBLE);
            holder.plateLayout.setVisibility(View.VISIBLE);
            holder.viewMore.setText(R.string.viewLess);
        } else {
            holder.servingTypeLayout.setVisibility(View.GONE);
            holder.parkingSlotLayout.setVisibility(View.GONE);
            holder.colorLayout.setVisibility(View.GONE);
            holder.ratingBarLayout.setVisibility(View.GONE);
            holder.aprtNumberLayout.setVisibility(View.GONE);
            holder.plateLayout.setVisibility(View.GONE);
            holder.viewMore.setText(R.string.viewMore);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pastJobList.get(position).getStatus().equals("2")) {
                    attendedDailog(position);
                } else {

                }



            }
        });


        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pastJobList.get(position).isExpanded()) {
                    pastJobList.get(position).setExpanded(false);
                    notifyItemChanged(position);
                } else {
                    pastJobList.get(position).setExpanded(true);
                    notifyItemChanged(position);

                }

            }
        });



    }


    @Override
    public int getItemCount() {
        return pastJobList.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }


    public class PastTaskViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout servingTypeLayout, colorLayout, parkingSlotLayout, aprtNumberLayout, ratingBarLayout, status_layout, plateLayout;
        private TextView orderId, viewMore, carModel, carColor, parkingNumber, apartmentNumber, servingNumber, status, plateNumber;
        private RatingBar ratingBar;
        private ImageView curvedImage;
        private CardView cardView;

        public PastTaskViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_past);
            curvedImage = (ImageView) itemView.findViewById(R.id.curved);
            status_layout = (LinearLayout) itemView.findViewById(R.id.status_layout);
            plateLayout = (LinearLayout) itemView.findViewById(R.id.palate_layout);
            servingTypeLayout = (LinearLayout) itemView.findViewById(R.id.servicingtype_layout);
            colorLayout = (LinearLayout) itemView.findViewById(R.id.color_layout);
            parkingSlotLayout = (LinearLayout) itemView.findViewById(R.id.parking_layout);
            orderId = (TextView) itemView.findViewById(R.id.car_orderId);
            viewMore = (TextView) itemView.findViewById(R.id.view_more);
            carModel = (TextView) itemView.findViewById(R.id.car_model);
            carColor = (TextView) itemView.findViewById(R.id.color);
            parkingNumber = (TextView) itemView.findViewById(R.id.car_parking);
            apartmentNumber = (TextView) itemView.findViewById(R.id.aprtmentNO);
            servingNumber = (TextView) itemView.findViewById(R.id.mentain_type);
            plateNumber = (TextView) itemView.findViewById(R.id.car_palate);
            status = (TextView) itemView.findViewById(R.id.car_orderStatus);
            aprtNumberLayout = (LinearLayout) itemView.findViewById(R.id.apartment_number_layout);
            ratingBarLayout = (LinearLayout) itemView.findViewById(R.id.ratingBarLayout);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingbar);


        }
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            PastAssignmentResponseResult filterHash;

            if (constraint != null && constraint.length() > 0) {
                List<PastAssignmentResponseResult> filterList = new ArrayList<PastAssignmentResponseResult>();

                for (int i = 0; i < mFilterList.size(); i++) {
                    if (mFilterList.get(i).getStreetName().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
            pastJobList = (ArrayList<PastAssignmentResponseResult>) results.values;
            notifyDataSetChanged();
        }
    }

    void UpdateAttendedStatus(int pos) {
        UpdateAttendedStatusRequest request = new UpdateAttendedStatusRequest();
        request.setAppKey(Constants.APPKEY);
        request.setAttendent("1");
        request.setFeedback("Attended");
        request.setMethod("update_job_done_as_attendent");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(c.getTime());
        request.setJob_done_date(strDate);
        request.setId(pastJobList.get(pos).getId());

        apiUtility.updateAttendentStatus(context.getActivity(), request, true, new APIUtility.APIResponseListener<UpdateAttendedStatusResponseWrapper>() {
            @Override
            public void onReceiveResponse(UpdateAttendedStatusResponseWrapper response) {
                if (response != null)
                    context.getPastAssignment(true);
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(context.getActivity(), context.getActivity().getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(UpdateAttendedStatusResponseWrapper response) {

            }
        });
    }


    void attendedDailog(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity());
        builder.setTitle(R.string.app_name);
        builder.setMessage("Are you attending this task?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                UpdateAttendedStatus(pos);
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
}
