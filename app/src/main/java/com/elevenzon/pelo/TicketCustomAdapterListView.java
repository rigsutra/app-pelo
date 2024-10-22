package com.elevenzon.pelo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TicketCustomAdapterListView extends BaseAdapter {
    LayoutInflater inflter;
    public TicketCustomAdapterListView(Context applicationContext) {
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return Public.responTicketsJsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private void showDeleteModal(String id, int index) {
        if(Public.ticket_view.equals("deleted ticket")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TicketView.ticketView);
            builder.setTitle("Delete Ticket");
            builder.setMessage("Are you sure you want to delete this item?\nYou will not find this ticket again in here.");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        ApiClient.ticketDeleteForever(id, index);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.create().show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(TicketView.ticketView);
            builder.setTitle("Delete Ticket");
            builder.setMessage("Are you sure you want to delete this item?");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        ApiClient.ticketDelete(id, index);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.create().show();
        }

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.ticket_item, null);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-DD", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("DD/mm/yyyy", Locale.getDefault());
        TextView date = view.findViewById(R.id.date);
        TextView lottery = view.findViewById(R.id.lottery);
        Button ticketIdBtn = view.findViewById(R.id.ticket_id_btn);
        Button ticketDeleteBtn = view.findViewById(R.id.ticket_delete_btn);
        try {
            JSONObject ticketInfo = (JSONObject) Public.responTicketsJsonArray.get(i);
            String dateStr = ticketInfo.getString("date").substring(0, 10);
            Date dateObj = inputFormat.parse(dateStr);
            date.setText(outputFormat.format(dateObj));
            lottery.setText(ticketInfo.getString("lotteryCategoryName"));
            ticketIdBtn.setText(ticketInfo.getString("ticketId"));

            ticketIdBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        TicketView.progress_layout.setVisibility(View.VISIBLE);
                        if(Public.ticket_view.equals("win ticket")) {
                            Public.paid_amount = ticketInfo.getLong("paidAmount");
                            Public.responTicketNumbersJsonArray = ticketInfo.getJSONArray("numbers");

                            TicketView.ticketView.onSuccess(ticketInfo.getJSONArray("numbers"));
                        } else {
                            String _id = ticketInfo.getString("_id");
                            ApiClient.getTicketNumbers(_id, TicketView.ticketView);
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            });

            ticketDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        showDeleteModal(ticketInfo.getString("_id"), i);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            });
        } catch (Exception err) {
            System.out.println(err);
        }
        return view;
    }
}
