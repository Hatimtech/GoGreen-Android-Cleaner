package com.gogreencleaner.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gogreencleaner.main.R;

import java.util.List;

public class StreetAdapter extends BaseAdapter {


        Context context;
        LayoutInflater inflater;
        List<String> colorList;


        public StreetAdapter(Context context, List<String> colorList) {

            this.context = context;
            this.colorList = colorList;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        @Override
        public int getCount() {
            return colorList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {


            view = inflater.inflate(R.layout.spinner_rows, null);
            TextView color_name = (TextView) view.findViewById(R.id.color);
            color_name.setText(colorList.get(i));
            return view;
        }


    }




