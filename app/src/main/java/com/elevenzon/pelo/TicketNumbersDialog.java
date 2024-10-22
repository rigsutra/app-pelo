package com.elevenzon.pelo;

//import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import androidx.fragment.app.DialogFragment;
public class TicketNumbersDialog extends DialogFragment {

    Button bt_ok;
    TicketNumbersDialog ticketNumbersDialog = this;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ticket_numbers, container);

        bt_ok = (Button) view.findViewById(R.id.bt_ok);
        ListView ticketNumberList = view.findViewById(R.id.list_view);
        TextView sumText = view.findViewById(R.id.sum);

        LinearLayout paid_amount_layout = view.findViewById(R.id.win_ticket_paidAmount_layout);

        if(Public.ticket_view.equals("win ticket")) {
            paid_amount_layout.setVisibility(View.VISIBLE);
            TextView paid_amount = view.findViewById(R.id.paidAmount);
            paid_amount.setText(Public.numberFormat.format(Public.paid_amount));
        } else {
            paid_amount_layout.setVisibility(View.GONE);
        }

        TicketNumbersAdapterListView customAdapterListView = new TicketNumbersAdapterListView(Public.activity.getApplicationContext());
        ticketNumberList.setAdapter(customAdapterListView);

        int sumAmount = 0;
        try {
            for (int i = 0; i < Public.responTicketNumbersJsonArray.length(); i++) {
                JSONObject ticketNumbers = (JSONObject) Public.responTicketNumbersJsonArray.get(i);
                boolean bonusFlag = ticketNumbers.getBoolean("bonus");
                if(!bonusFlag) {
                    int amount = Integer.parseInt(ticketNumbers.getString("amount"));
                    sumAmount += amount;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        sumText.setText(Integer.toString(sumAmount));

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticketNumbersDialog.dismiss();
            }
        });

        return view;
    }
}