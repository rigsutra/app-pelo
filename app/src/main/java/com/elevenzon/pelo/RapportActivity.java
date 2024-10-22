package com.elevenzon.pelo;

import android.app.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RapportActivity extends AppCompatActivity implements ApiResponseListener  {

    public static Button search;
    Button fromPickDate, toPickDate;
    EditText from, to;
    Spinner reportType, lotteryCategory;

    public static RelativeLayout progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rapport);

        reportType = findViewById(R.id.report_type);
        lotteryCategory = findViewById(R.id.lottery_dropdown);

        progress = findViewById(R.id.progressBar_layout);

        from = findViewById(R.id.from_date);
        to = findViewById(R.id.to_date);

        from.setKeyListener(null);
        to.setKeyListener(null);

        fromPickDate = findViewById(R.id.fromPickDate);
        toPickDate = findViewById(R.id.toPickDate);

        search = findViewById(R.id.search_btn);

        fromPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        RapportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, (monthOfYear + 1), year);
                                // on below line we are setting date to our text view.
                                from.setText(selectedDate);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        toPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        RapportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, (monthOfYear + 1), year);
                                // on below line we are setting date to our text view.
                                to.setText(selectedDate);
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        if(Public.lotteryData.length() > 0) {
            onSuccess(Public.lotteryData);
        } else {
            ApiClient.getLotteryCategory(this);
        }

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("DD/mm/yyyy", Locale.getDefault());
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("mm/DD/yyyy", Locale.getDefault());

        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String fromDateStr = from.getText().toString().trim();
                String toDateStr = to.getText().toString().trim();
                try {
                    progress.setVisibility(View.VISIBLE);
                    search.setEnabled(false);
                    Date fromDateObj = inputDateFormat.parse(fromDateStr);
                    String fromDate = outputDateFormat.format(fromDateObj);

                    Date toDateObj = inputDateFormat.parse(toDateStr);
                    String toDate = outputDateFormat.format(toDateObj);

                    Public.fromDate = fromDateStr;
                    Public.toDate = toDateStr;

                    String lottery = lotteryCategory.getSelectedItem().toString().trim();
                    String report = reportType.getSelectedItem().toString().trim();
                    Public.lotteryCategoryName = lottery;
                    if ( report.equals("Winning Numbers") ) {
                        ApiClient.getWinNumber(lottery, fromDate, toDate);
                    } else if ( report.equals("Sale Report") ) {
                        ApiClient.getReports(lottery, fromDate, toDate);
                    } else if( report.equals("Sold Tickets") ) {
                        ApiClient.getTicket(lottery, fromDate, toDate);
                    } else if ( report.equals("Winning Tickets") ) {
                        ApiClient.getWinTicket(lottery, fromDate, toDate);
                    } else if( report.equals("Deleted Tickets") ) {
                        ApiClient.getDeletedTicket(lottery, fromDate, toDate);
                    }

                } catch (ParseException e) {
                    progress.setVisibility(View.GONE);
                    search.setEnabled(true);
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void onSuccess(JSONArray response) {
        try {
            List<String> options = new ArrayList<>();
            options.add("All Category");
            for (int i = 0; i < response.length(); i++) {
               JSONObject jsonObject = response.getJSONObject(i);
               options.add(jsonObject.getString("lotteryName"));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
            lotteryCategory.setAdapter(adapter);
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