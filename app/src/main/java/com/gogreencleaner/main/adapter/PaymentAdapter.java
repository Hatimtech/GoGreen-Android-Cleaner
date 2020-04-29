package com.gogreencleaner.main.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gogreencleaner.main.R;
import com.gogreencleaner.main.Utils.CommonUtils;
import com.gogreencleaner.main.Utils.Constants;
import com.gogreencleaner.main.Utils.Preferences.PrefEntity;
import com.gogreencleaner.main.Utils.Preferences.Preferences;
import com.gogreencleaner.main.Volley.APIUtility;
import com.gogreencleaner.main.fragment.PaymentFragment;
import com.gogreencleaner.main.model.PaymentAssignment.RESPONSE.PaymentAssignmentResponseResult;
import com.gogreencleaner.main.model.PaymentCollectionStatus.REQUEST.PaymentCollectionStatusRequest;
import com.gogreencleaner.main.model.PaymentCollectionStatus.RESPONSE.PaymentCollectionStatusResponseWrapper;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> implements Filterable {

    private PaymentFragment context;
    List<PaymentAssignmentResponseResult> paymentList;
    List<PaymentAssignmentResponseResult> mFilterList;
    APIUtility apiUtility;
    ValueFilter valueFilter;

    public PaymentAdapter(PaymentFragment context, List<PaymentAssignmentResponseResult> paymentList) {
        this.context = context;
        this.paymentList = paymentList;
        this.mFilterList=paymentList;
        apiUtility = new APIUtility(context.getActivity());
    }

    @Override
    public PaymentAdapter.PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_list, parent, false);
        return new PaymentAdapter.PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PaymentAdapter.PaymentViewHolder holder, final int position) {
        holder.apartmentNumber.setText(paymentList.get(position).getApartmentNumber());
        holder.orderId.setText(paymentList.get(position).getOrderId());
        if(paymentList.get(position).getUser_Name()!=null) {
            holder.userName.setText(paymentList.get(position).getUser_Name().trim());
        }else{
            holder.userName.setText("");
        }
        if(paymentList.get(position).getStreet_name()!=null) {
            holder.street.setText(paymentList.get(position).getStreet_name().trim());
        }else{
            holder.street.setText("");
        }
        if(Integer.parseInt(paymentList.get(position).getAmount())-Integer.parseInt(paymentList.get(position).getPartial_payment())>0) {
            holder.money.setText(String.valueOf(Integer.parseInt(paymentList.get(position).getAmount()) - Integer.parseInt(paymentList.get(position).getPartial_payment())));
        }else{
            holder.money.setText(paymentList.get(position).getAmount());

        }
        if(paymentList.get(position).getUser_phone()!=null) {
            holder.userPhone.setText(paymentList.get(position).getUser_phone().trim());
        }else{
            holder.userPhone.setText("");
        }
        if (paymentList.get(position).getStatus().equals("1")) {
            holder.status.setText(R.string.pending);
            holder.status_layout.setBackgroundResource(R.drawable.orderpendingdrawalbe);
            holder.curvedImage.setImageResource(R.mipmap.red_curved_edge);
        } else {
            holder.status.setText(R.string.collected);
            holder.status_layout.setBackgroundResource(R.drawable.order_confirm_text_drawable);
            holder.curvedImage.setImageResource(R.mipmap.curved_edge);
        }
        if (paymentList.get(position).isExpanded()) {

            holder.servingTypeLayout.setVisibility(View.VISIBLE);
            holder.parkingSlotLayout.setVisibility(View.VISIBLE);
//            holder.colorLayout.setVisibility(View.VISIBLE);
//            holder.orderidLayout.setVisibility(View.VISIBLE);
            holder.aprtNumberLayout.setVisibility(View.VISIBLE);
            holder.viewMore.setText(R.string.viewLess);


        } else {
            holder.servingTypeLayout.setVisibility(View.GONE);
            holder.parkingSlotLayout.setVisibility(View.GONE);
//            holder.colorLayout.setVisibility(View.GONE);
//            holder.orderidLayout.setVisibility(View.GONE);
            holder.aprtNumberLayout.setVisibility(View.GONE);
            holder.viewMore.setText(R.string.viewMore);


        }

        holder.paymentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymentList.get(position).getStatus().equals("1")) {
                    showDailog(position,holder);

                }
            }
        });

        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymentList.get(position).isExpanded()) {
                    paymentList.get(position).setExpanded(false);
                    notifyItemChanged(position);
                } else {
                    paymentList.get(position).setExpanded(true);
                    notifyItemChanged(position);

                }
            }
        });


    }

    private void showDailog(final int position,final PaymentAdapter.PaymentViewHolder holder) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context.getActivity());

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                  dialog.cancel();
                 showCouponDailog(position,holder);
            }

        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             dialog.cancel();
            }
        });
        alert.setMessage(R.string.collected_money);
        alert.setTitle("Go Green Cleaner");
        alert.setCancelable(true);
        alert.create();
        alert.show();
    }

    private void showCouponDailog(final int position,final PaymentAdapter.PaymentViewHolder holder) {

        final Dialog dialog = new Dialog(context.getActivity());
        dialog.setContentView(R.layout.custom_payment_dailoge);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText amount = dialog.findViewById(R.id.enter_coupon);
        final Button dialogButton = dialog.findViewById(R.id.dialog_ok);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(amount.getText().toString().trim())) {
                    if(Integer.parseInt(amount.getText().toString().trim())<=(Integer.parseInt(paymentList.get(position).getAmount())-Integer.parseInt(paymentList.get(position).getPartial_payment()))){
                    dialog.dismiss();
                    sendPaymentstatus(position,amount.getText().toString().trim(),holder);
                    }else{
                        amount.setError(context.getString(R.string.alert_Amount1));
                    }
                } else {
                    amount.setError(context.getString(R.string.alert_Amount));
                }
            }
        });
        dialog.setCancelable(true);
        dialog.show();

    }


    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout servingTypeLayout, colorLayout, parkingSlotLayout, aprtNumberLayout, orderidLayout, paymentList, status_layout;
        private TextView orderId, viewMore, userName, carColor, userPhone, apartmentNumber, street, status, money;
        private ImageView curvedImage;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            curvedImage = (ImageView) itemView.findViewById(R.id.curved);
            status_layout = (LinearLayout) itemView.findViewById(R.id.status_layout);
            paymentList = (LinearLayout) itemView.findViewById(R.id.payment_list);
            servingTypeLayout = (LinearLayout) itemView.findViewById(R.id.servicingtype_layout);
            colorLayout = (LinearLayout) itemView.findViewById(R.id.color_layout);
            parkingSlotLayout = (LinearLayout) itemView.findViewById(R.id.parking_layout);
            orderId = (TextView) itemView.findViewById(R.id.car_orderId);
            viewMore = (TextView) itemView.findViewById(R.id.view_more);
            userName = (TextView) itemView.findViewById(R.id.car_model);
            carColor = (TextView) itemView.findViewById(R.id.color);
            userPhone = (TextView) itemView.findViewById(R.id.car_parking);
            apartmentNumber = (TextView) itemView.findViewById(R.id.aprtmentNO);
            street = (TextView) itemView.findViewById(R.id.mentain_type);
            status = (TextView) itemView.findViewById(R.id.car_orderStatus);
            aprtNumberLayout = (LinearLayout) itemView.findViewById(R.id.apartment_number_layout);
            orderidLayout = (LinearLayout) itemView.findViewById(R.id.order_id_layout);
            money = (TextView) itemView.findViewById(R.id.money);


        }
    }


    void sendPaymentstatus(final int position, final String amount,final PaymentAdapter.PaymentViewHolder holder) {

        PaymentCollectionStatusRequest paymentRequest = new PaymentCollectionStatusRequest();
        paymentRequest.setAppKey(Constants.APPKEY);
        paymentRequest.setMethod("update_payment_status");
        paymentRequest.setPartial_payment(amount);
        paymentRequest.setOrderId(paymentList.get(position).getOrderId());
        paymentRequest.setUser_id(paymentList.get(position).getUser_id());
        paymentRequest.setCleanerId(Preferences.getPreference(context.getActivity().getApplicationContext(), PrefEntity.USERID));

        apiUtility.sendPaymentStatus(context.getActivity(), paymentRequest, true, new APIUtility.APIResponseListener<PaymentCollectionStatusResponseWrapper>() {
            @Override
            public void onReceiveResponse(PaymentCollectionStatusResponseWrapper response) {

                if (response != null) {
                  context.getPaymentAssignment(true);
                }
            }

            @Override
            public void onResponseFailed() {

                CommonUtils.alert(context.getActivity(), context.getString(R.string.VolleyError));

            }

            @Override
            public void onStatusFalse(PaymentCollectionStatusResponseWrapper response) {

                CommonUtils.alert(context.getActivity(), response.getResponse().getMessage());
            }
        });

    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            PaymentAssignmentResponseResult filterHash;

            if (constraint != null && constraint.length() > 0) {
                List<PaymentAssignmentResponseResult> filterList = new ArrayList<PaymentAssignmentResponseResult>();

                for (int i = 0; i < mFilterList.size(); i++) {
                    if(mFilterList.get(i).getStreet_name()!=null) {
                        if (mFilterList.get(i).getStreet_name().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filterHash = mFilterList.get(i);
                            filterList.add(filterHash);
                        }
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
            paymentList = (ArrayList<PaymentAssignmentResponseResult>) results.values;
            notifyDataSetChanged();
        }
    }

}