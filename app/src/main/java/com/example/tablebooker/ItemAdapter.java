package com.example.tablebooker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] names;
    String[] rating;
    String[] descriptions;

    public ItemAdapter (Context c, String[] i, String[] r, String[] d) {
        names = i;
        rating = r;
        descriptions = d;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View v = mInflater.inflate(R.layout.my_listview_detail,null);
        TextView nameTextView = (TextView) v.findViewById(R.id.nameTextView);
        TextView descriptionTextView = (TextView) v.findViewById(R.id.descriptionTextView);
        TextView ratingTextView = (TextView) v.findViewById(R.id.ratingTextView);

        String name = names[i];
        String desc = descriptions[i];
        String rate = rating[i];

        nameTextView.setText(name);
        descriptionTextView.setText(desc);
        ratingTextView.setText(rate);

        return v;
    }
}
