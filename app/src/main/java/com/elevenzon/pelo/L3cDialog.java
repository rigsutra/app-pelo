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
public class L3cDialog extends DialogFragment{

    EditText l3c_num, l3c_val;
    Button bt_add, bt_cancel;
    L3cDialog l3c_dialog = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.l3c_dialog, container);

        bt_add = (Button) view.findViewById(R.id.bt_add);
        bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        l3c_num = (EditText) view.findViewById(R.id.l3c_num);
        l3c_val = (EditText) view.findViewById(R.id.l3c_val);

        l3c_num.requestFocus();

        l3c_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.length() >= 3) {
                    l3c_val.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.length() >= 3) {
                    l3c_val.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        l3c_val.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.length() >= 3) {
                    bt_add.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.length() >= 3) {
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

                if(l3c_num.getText().toString().trim().equals("")) {
                    l3c_num.requestFocus();
                    return;
                }

                if(l3c_val.getText().toString().trim().equals("")) {
                    l3c_val.requestFocus();
                    return;
                }

//                if(Public.backendCheckFlag) {

                    String gameCategory = "L3C";
                    String gameNumber = l3c_num.getText().toString().trim();
                    int amount = Integer.parseInt(l3c_val.getText().toString().trim());

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

                    l3c_num.getText().clear();
                    l3c_val.getText().clear();
                    l3c_num.requestFocus();
//                    Public.backendCheckFlag = false;
                }
//            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l3c_dialog.dismiss();
            }
        });

        return view;
    }
}