package com.elevenzon.pelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WinNumberCustemAdapterListView extends BaseAdapter {
    LayoutInflater inflter;

    public WinNumberCustemAdapterListView(Context applicationContext) {
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return Public.responWinNumberJsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.win_number_item, null);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-DD", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("DD/mm/yyyy", Locale.getDefault());
        TextView date = view.findViewById(R.id.date);
        TextView lottery = view.findViewById(R.id.lottery);
        TextView l3c = view.findViewById(R.id.l3c);
        TextView second = view.findViewById(R.id.second);
        TextView third = view.findViewById(R.id.thrid);
        try {
            JSONObject winNuberInfo = (JSONObject) Public.responWinNumberJsonArray.get(i);
            JSONObject numbers = (JSONObject) winNuberInfo.getJSONObject("numbers");
            String dateStr = winNuberInfo.getString("date").substring(0, 10);
            Date dateObj = inputFormat.parse(dateStr);
            date.setText(outputFormat.format(dateObj));
            lottery.setText(winNuberInfo.getString("lotteryName"));
            l3c.setText(numbers.getString("l3c"));
            second.setText(numbers.getString("second"));
            third.setText(numbers.getString("third"));
        } catch (Exception err) {
            System.out.println(err);
        }
        return view;
    }
}
