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

import java.util.ArrayList;
import java.util.Vector;

import androidx.fragment.app.DialogFragment;
public class AutoMrg extends DialogFragment{

    EditText pari_num;
    Button bt_add, bt_cancel;
    AutoMrg autoMrg = this;
//    int flag = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auto_mrg, container);

        bt_add = (Button) view.findViewById(R.id.bt_add);
        bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        pari_num = (EditText) view.findViewById(R.id.pari_auto_num);

        pari_num.requestFocus();

        pari_num.addTextChangedListener(new TextWatcher() {
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

                if(pari_num.getText().toString().trim().equals("")) {
                    pari_num.requestFocus();
                    return;
                }

//                if(flag == 0) {
//                    flag = 1;
                    try {
                        ArrayList autoMrg = new ArrayList<>();
                        for(int i = 0; i < Public.ticket_data.size() ; i++) {
                            JSONObject jsonObj = (JSONObject) Public.ticket_data.get(i);
                            String gameCategory = jsonObj.getString("gameCategory");
                            String number = jsonObj.getString("number");

                            if(gameCategory.trim().equals("BLT")) {
                                autoMrg.add(number.trim());
                            }
                        }

//                        JSONObject bltJson = new JSONObject();
//                        bltJson.put("type", "check");
//                        bltJson.put("lotteryCategoryName", Public.lotteryCategoryName.trim());
//
//                        bltJson.put("subAdminId", Public.subAdminId.trim());
//                        bltJson.put("sellerId", Public.sellerId.trim());


                        for(int i = 0; i < autoMrg.size() - 1; i++) {
                            for(int j = i + 1; j < autoMrg.size(); j++) {
                                String newMRG = autoMrg.get(i).toString() + "×" + autoMrg.get(j).toString();
//                                bltJson.put("gameCategory", "MRG");
//                                bltJson.put("number", newMRG.trim());
//                                bltJson.put("quantity", Integer.parseInt(pari_num.getText().toString().trim()));

                                Public.addTicketItem("MRG", newMRG.trim(), Integer.parseInt(pari_num.getText().toString().trim()));
                                Public.calc_sum();
//                                ApiClient.sendMessage(bltJson.toString());
//                                Public.addTicketItem("MRG", newMRG, Integer.parseInt(pari_num.getText().toString().trim()));
                            }
                        }
                        Public.calc_sum();
                        pari_num.getText().clear();
                    }catch(Exception ex) {
                    }
//                }

                autoMrg.dismiss();
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoMrg.dismiss();
            }
        });

        return view;
    }
}