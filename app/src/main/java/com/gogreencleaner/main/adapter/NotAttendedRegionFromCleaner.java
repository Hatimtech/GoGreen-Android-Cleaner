package com.gogreencleaner.main.adapter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gogreencleaner.main.R;



import java.util.List;

public class NotAttendedRegionFromCleaner extends RecyclerView.Adapter<NotAttendedRegionFromCleaner.RegionViewHolder> {

    Context context;
    List<String> regionList;
    int SelectedPos = 9999;
    ReasonCallback callback;

    public NotAttendedRegionFromCleaner(List<String> regionList, Context context) {
        this.context = context;
        this.regionList = regionList;
        this.callback = (ReasonCallback) (context);

    }

    @Override
    public RegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.region_list, parent, false);
        return new NotAttendedRegionFromCleaner.RegionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RegionViewHolder holder, final int position) {
        holder.region.setText(regionList.get(position));
        if (SelectedPos == position) {
            holder.regionLayout.setBackgroundResource(R.drawable.not_attended_reason_green);
            holder.region.setTextColor(context.getResources().getColor(R.color.white));
        } else {

            holder.regionLayout.setBackgroundResource(R.drawable.rounded_shape);
            holder.region.setTextColor(context.getResources().getColor(R.color.textcolor));
        }

        holder.regionLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                holder.regionLayout.setBackgroundResource(R.drawable.not_attended_reason_green);
                holder.region.setTextColor(context.getResources().getColor(R.color.white));
                SelectedPos = position;
                callback.itemClick(SelectedPos);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return regionList.size();
    }

    public class RegionViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout regionLayout;
        private TextView region;

        public RegionViewHolder(View itemView) {
            super(itemView);


            regionLayout = (LinearLayout) itemView.findViewById(R.id.layout);
            region = (TextView) itemView.findViewById(R.id.region);


        }
    }

    public interface ReasonCallback {
        void itemClick(int pos);
    }

}
