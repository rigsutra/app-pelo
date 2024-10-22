package com.elevenzon.pelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;

class TicketInfoCustomAdapterListView extends BaseAdapter {
    LayoutInflater inflter;

    public TicketInfoCustomAdapterListView(Context applicationContext) {
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return Public.ticket_data.size();
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
        view = inflter.inflate(R.layout.lottery_buy_list, null);
        TextView type = (TextView) view.findViewById(R.id.type);
        TextView strnum = (TextView) view.findViewById(R.id.str_num);
        TextView sum = (TextView) view.findViewById(R.id.sum);
        try {
            JSONObject current_ticket_item = (JSONObject) Public.ticket_data.get(i);
            type.setText(current_ticket_item.getString("gameCategory"));
            strnum.setText(current_ticket_item.getString("number"));
            sum.setText(current_ticket_item.getString("amount") );
        } catch (Exception err) {
            System.out.println(err);
        }
        return view;
    }
}
