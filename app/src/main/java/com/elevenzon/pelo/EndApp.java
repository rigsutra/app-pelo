package com.elevenzon.pelo;

import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class EndApp extends DialogFragment{

    Button bt_ok, bt_cancel;
    EndApp endApp = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_finish, container);

        bt_ok = (Button) view.findViewById(R.id.bt_end_ok);
        bt_cancel = (Button) view.findViewById(R.id.bt_end_cancel);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_out();
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endApp.dismiss();
            }
        });

        return view;
    }

    public static void sign_out() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Public.activity.finishAndRemoveTask();
            } else {
                Public.activity.finishAffinity();
            }
        }catch (Exception ex) {
            System.out.println("app exit: " + ex);
        }
    }
}