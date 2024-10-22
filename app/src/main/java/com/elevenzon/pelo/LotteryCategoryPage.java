package com.elevenzon.pelo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LotteryCategoryPage extends AppCompatActivity implements ApiResponseListener {

    LinearLayout mainLayout, nestedLayout1, nestedLayout2;
    public static FrameLayout progress_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lottery_catetory_list);

        mainLayout = findViewById(R.id.main_layout);
        progress_layout = findViewById(R.id.progressBar_layout);

        // Create the first nested LinearLayout
        nestedLayout1 = new LinearLayout(this);
        nestedLayout1.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        nestedLayout1.setOrientation(LinearLayout.VERTICAL);

        // Create the second nested LinearLayout
        nestedLayout2 = new LinearLayout(this);
        nestedLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1
        ));
        nestedLayout2.setOrientation(LinearLayout.VERTICAL);

        // Create the TextView
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 100); // Set the bottom margin to 100 pixels
        textView.setLayoutParams(layoutParams);
        textView.setText("Vente");
        textView.setTextSize(35);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(null, Typeface.BOLD);

        // Add the TextView to the first nested LinearLayout
        nestedLayout1.addView(textView);

        // Add the second nested LinearLayout to the first nested LinearLayout
        nestedLayout1.addView(nestedLayout2);

        // Add the first nested LinearLayout to the main LinearLayout
        mainLayout.addView(nestedLayout1);

        if(Public.lotteryData.length() > 0) {
            onSuccess(Public.lotteryData);
        } else {
            ApiClient.getLotteryCategory(this);
        }
    }

    @Override
    public void onSuccess(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObject = response.getJSONObject(i);
                Button lottoNameBtn = new Button(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, // Set the width to match the parent view
                        100 // Set the height to 100 pixels
                );
                lottoNameBtn.setLayoutParams(layoutParams);
                lottoNameBtn.setText(jsonObject.getString("lotteryName"));
                lottoNameBtn.setTextSize(16);
                nestedLayout2.addView(lottoNameBtn);
                // Set an OnClickListener on the button
                lottoNameBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle the button click event here
                        try {
                            Public.lotteryCategoryName = jsonObject.getString("lotteryName").trim();
                            Public.ticket_data = new ArrayList();
                            progress_layout.setVisibility(View.VISIBLE);
                            ApiClient.lotteryTimeCheck(jsonObject.getString("_id"));
                        } catch (Exception err) {
                            System.out.println("Lottery Name Click err: " + err);
                        }
                    }
                });
            }
        } catch (Exception err) {
            System.out.println("on Success err: " + err);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), VenteRapport.class);
        startActivity(intent);
    }
}