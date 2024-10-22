package com.elevenzon.pelo;

import android.annotation.SuppressLint;
//import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import androidx.fragment.app.DialogFragment;


public class L4cDialog extends DialogFragment {

    EditText l4c_num, l4c_val;
    Button bt_add, bt_cancel;
    L4cDialog l4c_dialog = this;

    public String gameCategory = "";

    @SuppressLint("ValidFragment")
    public L4cDialog(String value) {
        this.gameCategory = value;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.l4c_dialog, container);

        bt_add = (Button) view.findViewById(R.id.bt_add);
        bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        TextView l4c_caption = view.findViewById(R.id.l4c_caption);
        l4c_num = (EditText) view.findViewById(R.id.l4c_num);
        l4c_val = (EditText) view.findViewById(R.id.l4c_val);

        l4c_caption.setText(gameCategory + "  ");

        l4c_num.requestFocus();

        l4c_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.length() >= 4) {
//                    l4c_opt1.requestFocus();
                    l4c_val.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(l4c_num.getText().toString().trim().equals("")) {
                    l4c_num.requestFocus();
                    return;
                }

//                if(Public.backendCheckFlag) {

                    String gameNumber = l4c_num.getText().toString().trim();
                    int amount = Integer.parseInt(l4c_val.getText().toString().trim());

//                    try {
//                        JSONObject bltJson = new JSONObject();
//                        bltJson.put("type", "check");
//                        bltJson.put("lotteryCategoryName", Public.lotteryCategoryName.trim());
//                        bltJson.put("gameCategory", gameCategory);
//                        bltJson.put("number", gameNumber.trim());
//                        bltJson.put("quantity", amount);
//                        bltJson.put("subAdminId", Public.subAdminId.trim());
//                        bltJson.put("sellerId", Public.sellerId.trim());

                        Public.addTicketItem(gameCategory, gameNumber.trim(), amount);
                        Public.calc_sum();

//                        ApiClient.sendMessage(bltJson.toString());
//                    } catch (Exception ex) {
//                        System.out.println("blt number check error: " + ex);
//                    }


//                    Public.addTicketItem(gameCategory, gameNumber, amount);
//                    Public.calc_sum();

                    l4c_num.getText().clear();
                    l4c_val.getText().clear();
                    l4c_num.requestFocus();
//                    Public.backendCheckFlag = false;
                }
//            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              l4c_dialog.dismiss();
            }
        });

        return view;
    }
}