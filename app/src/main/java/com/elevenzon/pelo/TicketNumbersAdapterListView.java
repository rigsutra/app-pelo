package com.elevenzon.pelo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;


public class TicketNumbersAdapterListView extends BaseAdapter {
    LayoutInflater inflter;

    public TicketNumbersAdapterListView(Context applicationContext) {
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return Public.responTicketNumbersJsonArray.length();
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
        view = inflter.inflate(R.layout.ticket_numbers_item, null);
        TextView gameName = view.findViewById(R.id.gameName);
        TextView gameNumber = view.findViewById(R.id.gameNumber);
        TextView amount = view.findViewById(R.id.amount);
        try {
            JSONObject ticketNumbers = (JSONObject) Public.responTicketNumbersJsonArray.get(i);
            boolean bonusFlag = ticketNumbers.getBoolean("bonus");

            if(bonusFlag) {
                gameName.setText(ticketNumbers.getString("gameCategory") + "(Bonus)");
            } else {
                gameName.setText(ticketNumbers.getString("gameCategory"));
            }

            gameNumber.setText(ticketNumbers.getString("number"));
            amount.setText(ticketNumbers.getString("amount"));

            try {
                if (bonusFlag) {
                    gameName.setTextColor(Color.rgb(165,42,42));
                    gameNumber.setTextColor(Color.rgb(165,42,42));
                    amount.setTextColor(Color.rgb(165,42,42));
                }
                if ( ticketNumbers.getBoolean("winFlag") ) {
                    gameName.setTextColor(Color.BLUE);
                    gameNumber.setTextColor(Color.BLUE);
                    amount.setTextColor(Color.BLUE);
                }
            } catch (Exception ex) {}
        } catch (Exception err) {
            System.out.println(err);
        }
        return view;
    }
}