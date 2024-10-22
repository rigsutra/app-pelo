package com.elevenzon.pelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;

public class LimitDataAdapterListView extends BaseAdapter {
    LayoutInflater inflter;

    public LimitDataAdapterListView(Context applicationContext) {
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return Public.limit_data.length();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.limit_number_info, null);
        TextView game_name = view.findViewById(R.id.game_name);
        TextView game_number = view.findViewById(R.id.game_number);
        TextView request = view.findViewById(R.id.request_amount);
        TextView accepted = view.findViewById(R.id.accepted_amount);
        try {
            JSONObject jsonObj = (JSONObject) Public.limit_data.get(i);
            game_name.setText(jsonObj.getString("gameCategory"));
            game_number.setText(jsonObj.getString("number"));
            request.setText(jsonObj.getString("amount"));
            accepted.setText(jsonObj.getString("availableAmount"));
        } catch (Exception err) {
            System.out.println(err);
        }
        return view;
    }
}