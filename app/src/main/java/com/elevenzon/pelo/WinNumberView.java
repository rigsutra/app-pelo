package com.elevenzon.pelo;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class WinNumberView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_number_list);

        ListView winnumbers_container = findViewById(R.id.list_view);

        WinNumberCustemAdapterListView customAdapterListView = new WinNumberCustemAdapterListView(this.getApplicationContext());
        winnumbers_container.setAdapter(customAdapterListView);

    }
}
