package com.elevenzon.pelo;

//import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import androidx.fragment.app.DialogFragment;


public class SelectDialog extends DialogFragment{

    Button del_ok, del_cancel;
    TextView sel_data;
    SelectDialog selectDialog = this;

    int select_no;

    int flag = 0;

    String gameCategory, number;
    int amount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.select_del, container);

        del_ok = (Button) view.findViewById(R.id.del_ok);
        del_cancel = (Button) view.findViewById(R.id.del_cancel);
        sel_data = (TextView) view.findViewById(R.id.select_val);

        JSONObject selected_ticket_item = (JSONObject) Public.ticket_data.get(select_no);

        try {
            gameCategory = selected_ticket_item.getString("gameCategory");
            number = selected_ticket_item.getString("number");
            amount =  selected_ticket_item.getInt("amount");

            String show_str = gameCategory + "   " + number + "   (" + Public.numberFormat.format(amount) + ")";
            sel_data.setText((show_str));
        } catch (Exception err) {
            System.out.println(err);
        }

        del_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == 0) {
                    try {
//                        JSONObject bltJson = new JSONObject();
//                        bltJson.put("type", "deleteSoldQuantity");
//                        bltJson.put("lotteryCategoryName", Public.lotteryCategoryName.trim());
//                        bltJson.put("subAdminId", Public.subAdminId.trim());
//                        bltJson.put("sellerId", Public.sellerId.trim());
//                        bltJson.put("gameCategory", gameCategory);
//                        bltJson.put("number", number.trim());
//                        bltJson.put("quantity", amount);
//                        bltJson.put("selectRowNumber", select_no);

//                        ApiClient.sendMessage(bltJson.toString());

                        Public.ticket_data.remove(select_no);
                        Public.calc_sum();
                        selectDialog.dismiss();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        });

        del_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDialog.dismiss();
            }
        });

        return view;
    }

    public void set_data(int no) {
        this.select_no = no;
    }
}