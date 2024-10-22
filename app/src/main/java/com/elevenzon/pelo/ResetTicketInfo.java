package com.elevenzon.pelo;

//import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.DialogFragment;
public class ResetTicketInfo extends DialogFragment {

    Button bt_ok, bt_cancel;
    ResetTicketInfo resetTicketInfo = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ticket_info_reset, container);

        bt_ok = (Button) view.findViewById(R.id.bt_end_ok);
        bt_cancel = (Button) view.findViewById(R.id.bt_end_cancel);

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTicketInfo.dismiss();
            }
        });

        return view;
    }

    public static void reset() {
        try {
            Intent intent = new Intent(Public.activity.getApplicationContext(), VenteRapport.class);
            Public.activity.startActivity(intent);
        }catch (Exception ex) {
            System.out.println("app exit: " + ex);
        }
    }
}