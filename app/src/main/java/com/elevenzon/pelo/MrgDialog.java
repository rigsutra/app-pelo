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

public class MrgDialog extends DialogFragment {

    EditText mrg1_num, mrg2_num,  mrg_val;
    Button bt_add, bt_cancel;
    MrgDialog mrg_dialog = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mrg_dialog, container);

        bt_add = (Button) view.findViewById(R.id.bt_add);
        bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        mrg1_num = (EditText) view.findViewById(R.id.mrg1_num);
        mrg2_num = (EditText) view.findViewById(R.id.mrg2_num);
        mrg_val = (EditText) view.findViewById(R.id.mrg_val);

        mrg1_num.requestFocus();

        mrg1_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() >= 2) {
                    mrg2_num.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

       mrg2_num.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if(charSequence.length() >= 2) {
                   mrg_val.requestFocus();
               }
           }

           @Override
           public void afterTextChanged(Editable editable) {

           }
       });

       mrg_val.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if(charSequence.length() >= 3) {
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

               if(mrg1_num.getText().toString().trim().equals("")) {
                   mrg1_num.requestFocus();
                   return;
               }

               if(mrg2_num.getText().toString().trim().equals("")) {
                   mrg2_num.requestFocus();
                   return;
               }

               if(mrg_val.getText().toString().trim().equals("")) {
                   mrg_val.requestFocus();
                   return;
               }

//               if(Public.backendCheckFlag) {

                   String gameCategory = "MRG";
                   String gameNumber = mrg1_num.getText().toString().trim() + "×" + mrg2_num.getText().toString().trim();
                   int amount = Integer.parseInt(mrg_val.getText().toString().trim());

//                   try {
//                       JSONObject bltJson = new JSONObject();
//                       bltJson.put("type", "check");
//                       bltJson.put("lotteryCategoryName", Public.lotteryCategoryName.trim());
//                       bltJson.put("gameCategory", gameCategory);
//                       bltJson.put("number", gameNumber.trim());
//                       bltJson.put("quantity", amount);
//                       bltJson.put("subAdminId", Public.subAdminId.trim());
//                       bltJson.put("sellerId", Public.sellerId.trim());

                       Public.addTicketItem(gameCategory, gameNumber.trim(), amount);
                       Public.calc_sum();

//                       ApiClient.sendMessage(bltJson.toString());
//                   } catch (Exception ex) {
//                       System.out.println("blt number check error: " + ex);
//                   }


//                    Public.addTicketItem(gameCategory, gameNumber, amount);
//                    Public.calc_sum();

                   mrg1_num.getText().clear();
                   mrg2_num.getText().clear();
                   mrg_val.getText().clear();
                   mrg1_num.requestFocus();
//                   Public.backendCheckFlag = false;
               }
//           }
       });

       bt_cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mrg_dialog.dismiss();
           }
       });

        return view;
    }
}