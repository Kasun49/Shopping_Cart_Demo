package com.example.acer.slt_lite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TechhMyAdapter extends ArrayAdapter<String> {

    public ArrayList<String> names=new ArrayList<>();

    int[] flags;
    Context mContext;
    String[]mames;

    public TechhMyAdapter(Context context, ArrayList<String> complains) {
        super(context, R.layout.listview_complane);
        this.names = new ArrayList<>(complains);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_complane, parent, false);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        String [] mames = names.toArray(new String[names.size()]);
        mViewHolder.mName.setText(mames[position]);

        return convertView;
    }

    static class ViewHolder {

        TextView mName;
    }
}
