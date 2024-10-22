package com.elevenzon.pelo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.elevenzon.pelo.utils.SunmiPrintHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportsData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports);

        TextView lottery = findViewById(R.id.lotteCategory);
        TextView profit = findViewById(R.id.profit);
        TextView paid = findViewById(R.id.paid);
        TextView total = findViewById(R.id.total);

        TextView company = findViewById(R.id.company_name);
        TextView address = findViewById(R.id.address);
        TextView seller = findViewById(R.id.seller_name);
        TextView lotteryName = findViewById(R.id.lottery);
        TextView searchFor = findViewById(R.id.searchFor);
        TextView date = findViewById(R.id.date);
        TextView phone = findViewById(R.id.phone_number);

        Button print_btn = findViewById(R.id.print_btn);

        phone.setText(Public.phoneNumber);
        company.setText(Public.companyName);
        address.setText(Public.address);
        seller.setText(Public.sellerName);
        lotteryName.setText(Public.lotteryCategoryName);

        SimpleDateFormat format = new SimpleDateFormat("'on' yyyy-MM-dd 'at' HH:mm:ss");
        date.setText("date: " + format.format(new Date()));

        searchFor.setText(Public.fromDate + " ~ " + Public.toDate);

        lottery.setText(Public.lotteryCategoryName);
        try {
            int totalInt = Public.responJsonObj.getInt("sum");
            int paidInt = Public.responJsonObj.getInt("paid");

            profit.setText(Integer.toString( totalInt - paidInt));
            paid.setText(Integer.toString(paidInt));
            total.setText(Integer.toString(totalInt));

        } catch (Exception err) {
            System.out.println("report err: " + err);
        }

        View print_preview = findViewById(R.id.print_me_layout);

        print_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SunmiPrintHelper.getInstance().initSunmiPrinterService(Public.activity);
                Public.sendViewToPrinter(print_preview, Public.reuseBitmap);
                Intent intent = new Intent(Public.activity, RapportActivity.class);
                Public.activity.startActivity(intent);

            }
        });
    }
}
