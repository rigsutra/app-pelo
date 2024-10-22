package com.elevenzon.pelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;

public class BlockDataAdapterListView extends BaseAdapter {
    LayoutInflater inflter;

    public BlockDataAdapterListView(Context applicationContext) {
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return Public.block_data.length();
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
        view = inflter.inflate(R.layout.block_number_info, null);
        TextView game_name = view.findViewById(R.id.game_name);
        TextView game_number = view.findViewById(R.id.game_number);
        try {
            JSONObject jsonObj = (JSONObject) Public.block_data.get(i);
            game_name.setText(jsonObj.getString("gameCategory"));
            game_number.setText(jsonObj.getString("number"));
        } catch (Exception err) {
            System.out.println(err);
        }
        return view;
    }
}