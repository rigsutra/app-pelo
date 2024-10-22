package com.elevenzon.pelo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import androidx.fragment.app.DialogFragment;

public class FiniDialog extends DialogFragment{

    public static Button bt_ok;
    TextView tdp_count, vt_sum, tirer;

    public static RelativeLayout spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fini_dialog, container);

        tdp_count = (TextView) view.findViewById(R.id.conf_tds_count);
        vt_sum = (TextView) view.findViewById(R.id.conf_vt_sum);
        tirer = (TextView) view.findViewById(R.id.tirer);

        spinner = view.findViewById(R.id.progressBar_layout);

        long sum = 0;

        for (int i = 0; i < Public.ticket_data.size(); i++) {
            try {
                // Get the JSONObject at the current index
                JSONObject jsonTicketObject = (JSONObject) Public.ticket_data.get(i);

                // Get the amount value from the JSONObject and add it to the sum
                sum += jsonTicketObject.optInt("amount");
            } catch (Exception err){System.out.println("ticket item error: " + err);}
        }

        tdp_count.setText(Public.numberFormat.format(Public.ticket_data.size()));
        vt_sum.setText(Public.numberFormat.format(sum));

        tirer.setText(Public.lotteryCategoryName);

        bt_ok = (Button) view.findViewById(R.id.bt_ok);


        if(!Public.backendCheckFlag) {
            spinner.setVisibility(View.VISIBLE);
            bt_ok.setEnabled(false);
        }

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(Public.backendCheckFlag) {
                        Public.view = view;
                        Toast.makeText(Public.activity, "Data saveing...please wait moment", Toast.LENGTH_SHORT).show();
                        JSONObject jsonObjectTicketData = new JSONObject();
                        jsonObjectTicketData.put("sellerId", Public.sellerId);
                        jsonObjectTicketData.put("lotteryCategoryName", Public.lotteryCategoryName.trim());
                        jsonObjectTicketData.put("numbers", Public.ticket_data.toString());
                        spinner.setVisibility(View.VISIBLE);
                        bt_ok.setEnabled(false);
                        ApiClient.requestTicket(jsonObjectTicketData);
                        Public.backendCheckFlag = false;
                    }
                } catch (Exception err) {
                    Toast.makeText(Public.activity, "toString: " + err, Toast.LENGTH_LONG).show();
                    System.out.println("printer err: " + err);
                }
            }
        });
        return view;
    }
}