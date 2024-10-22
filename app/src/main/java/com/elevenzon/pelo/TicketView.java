package com.elevenzon.pelo;

//import android.app.Fragment;
//import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONArray;

public class TicketView extends AppCompatActivity implements ApiResponseListener {

    public static TicketView ticketView;

    FragmentManager manager = getSupportFragmentManager();
    Fragment frag = manager.findFragmentByTag("fragment_edit_name");
    public static ListView winTicket_container;

    public static RelativeLayout progress_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_list);

        winTicket_container = findViewById(R.id.list_view);
        progress_layout = findViewById(R.id.progressBar_layout);

        TicketCustomAdapterListView customAdapterListView = new TicketCustomAdapterListView(this.getApplicationContext());
        winTicket_container.setAdapter(customAdapterListView);

        ticketView = this;

    }

    @Override
    public void onSuccess(JSONArray response) {
        try {
            TicketView.progress_layout.setVisibility(View.GONE);
            if (frag != null) {
                manager.beginTransaction().remove(frag).commit();
            }
            TicketNumbersDialog ticketNumbersDialog = new TicketNumbersDialog();
            ticketNumbersDialog.show(manager, "fragment_edit_name");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}