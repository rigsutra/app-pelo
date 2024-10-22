package com.elevenzon.pelo;

//import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;
import androidx.fragment.app.DialogFragment;

public class BltDialog extends DialogFragment {

    Button bt_add, bt_cancel;
    EditText blt_num, blt_val;
    BltDialog blt_dialog = this;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blt_dialog, container);

        bt_add = (Button) view.findViewById(R.id.bt_add);
        bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        blt_num = (EditText) view.findViewById(R.id.pari_auto_num);
        blt_val = (EditText) view.findViewById(R.id.blt_val);

        blt_num.requestFocus();

        blt_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.length() >= 2) {
                    blt_val.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        blt_val.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.length() >= 5) {
                    bt_add.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(blt_num.getText().toString().trim().equals("")) {
                    blt_num.requestFocus();
                    return;
                }

                if(blt_val.getText().toString().trim().equals("")) {
                    blt_val.requestFocus();
                    return;
                }

//                if(Public.backendCheckFlag) {

                    String gameCategory = "BLT";
                    String gameNumber = blt_num.getText().toString().trim();
                    int amount = Integer.parseInt(blt_val.getText().toString().trim());

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

                    blt_num.getText().clear();
                    blt_val.getText().clear();
                    blt_num.requestFocus();
//                    Public.backendCheckFlag = false;
                }
//            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blt_dialog.dismiss();
            }
        });

        return view;
    }
}