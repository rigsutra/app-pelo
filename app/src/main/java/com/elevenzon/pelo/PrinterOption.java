package com.elevenzon.pelo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.RadioButton;
import androidx.fragment.app.DialogFragment;

import com.elevenzon.pelo.utils.SunmiPrintHelper;

public class PrinterOption extends DialogFragment {

    Button bt_ok;
    RadioButton print1, print2, print3, print4;
    PrinterOption printerOption = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.print_option, container);

        bt_ok = (Button) view.findViewById(R.id.print_ok);

        print1 = (RadioButton) view.findViewById(R.id.printer1);
//        print2 = (RadioButton) view.findViewById(R.id.printer2);
//        print3 = (RadioButton) view.findViewById(R.id.printer3);
        print4 = (RadioButton) view.findViewById(R.id.printer4);
        try {
            if(Public.selected_print.equals("Bluetooth Printer")) {
                print1.setChecked(true);
//            } else if(Public.selected_print.equals("printer2")) {
//                print2.setChecked(true);
//            } else if(Public.selected_print.equals("printer3")) {
//                print3.setChecked(true);
            } else if(Public.selected_print.equals("Sunmi Printer")) {
                print4.setChecked(true);
                SunmiPrintHelper.getInstance().initSunmiPrinterService(Public.activity);
            }
        } catch (Exception ex){
        }


        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printerOption.dismiss();
            }
        });

        print1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_check();
            }
        });

//        print2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                radio_check();
//            }
//        });
//
//        print3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                radio_check();
//            }
//        });

        print4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_check();
            }
        });


        return view;
    }

    public void radio_check() {
        try {

            if(print1.isChecked()) {
                Public.selected_print = "Bluetooth Printer";
//        }else if(print2.isChecked()) {
//            Public.selected_print = "printer2";
//        }else if(print3.isChecked()) {
//            Public.selected_print = "printer3";
            }else if(print4.isChecked()) {
                Public.selected_print = "Sunmi Printer";
            }
            SharedPreferences.Editor editor = Public.sharedPreferences.edit();
            editor.putString("printer_device", Public.selected_print);
            editor.apply();
        } catch (Exception ex) {
        }

    }

}