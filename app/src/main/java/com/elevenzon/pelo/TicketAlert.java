package com.elevenzon.pelo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.DialogFragment;

public class TicketAlert extends DialogFragment {

    public static Button bt_ok;

    TicketAlert ticketAlert = this;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alert_layout, container);

        LinearLayout block_layout = view.findViewById(R.id.block_number_layout);
        ListView block_list = view.findViewById(R.id.block_list_view);

        LinearLayout limit_layout = view.findViewById(R.id.limit_number_layout);
        ListView limit_list = view.findViewById(R.id.limit_list_view);

        if(Public.block_data.length() > 0) {
            block_layout.setVisibility(View.VISIBLE);
            BlockDataAdapterListView customAdapterListView = new BlockDataAdapterListView(Public.activity.getApplicationContext());
            block_list.setAdapter(customAdapterListView);
        } else {
            block_layout.setVisibility(View.GONE);
        }

        if(Public.limit_data.length() > 0) {
            limit_layout.setVisibility(View.VISIBLE);
            LimitDataAdapterListView customAdapterListView = new LimitDataAdapterListView(Public.activity.getApplicationContext());
            limit_list.setAdapter(customAdapterListView);
        } else {
            limit_layout.setVisibility(View.GONE);
        }

        bt_ok = view.findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Public.respon_ticket_data.length() > 0) {
                    ticketAlert.dismiss();
                } else {
                    Intent intent = new Intent(Public.activity, VenteActivityAction.class);
                    Public.activity.startActivity(intent);
                }
            }
        });
        return view;
    }
}