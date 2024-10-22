package com.elevenzon.pelo;

import android.app.DatePickerDialog;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReplayTicket extends AppCompatActivity implements ApiResponseListener {

    public static Button search;
    Button pickDate;
    EditText date, ticketId;
    Spinner lottery, newLottery;

    public static RelativeLayout progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay_panel);

        lottery = findViewById(R.id.lottery_dropdown);
        newLottery = findViewById(R.id.new_lottery_dropdown);
        ticketId = findViewById(R.id.ticket_id_input);

        progress = findViewById(R.id.progressBar_layout);

        date = findViewById(R.id.date);
        date.setKeyListener(null);
        pickDate = findViewById(R.id.fromPickDate);

        search = findViewById(R.id.search_btn);

        pickDate.setOnClickListener(new View.OnClickListener() {
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
                        ReplayTicket.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, (monthOfYear + 1), year);
                                // on below line we are setting date to our text view.
                                date.setText(selectedDate);

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
        SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String fromDateStr = date.getText().toString().trim();
                try {
                    progress.setVisibility(View.VISIBLE);
                    search.setEnabled(false);
                    Date fromDateObj = inputDateFormat.parse(fromDateStr);
                    String fromDate = outputDateFormat.format(fromDateObj);

                    Public.fromDate = fromDateStr;

                    String lotteryStr = lottery.getSelectedItem().toString().trim();
                    String newLotteryStr = newLottery.getSelectedItem().toString().trim();
                    Public.lotteryCategoryName = newLotteryStr;

                    JSONObject jsonObjectTicketData = new JSONObject();
                    jsonObjectTicketData.put("date", format_date.format(new Date(fromDate)));
                    jsonObjectTicketData.put("lottery", lotteryStr.trim());
                    jsonObjectTicketData.put("newLottery", newLotteryStr.trim());
                    jsonObjectTicketData.put("tId", ticketId.getText().toString().trim());

                    Public.ticket_data = new ArrayList<>();

                    ApiClient.replayTicket(jsonObjectTicketData);
                    progress.setVisibility(View.VISIBLE);
                    search.setEnabled(false);

                } catch (Exception e) {
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
            for (int i = 0; i < response.length(); i++) {
                JSONObject jsonObject = response.getJSONObject(i);
                options.add(jsonObject.getString("lotteryName"));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
            lottery.setAdapter(adapter);
            newLottery.setAdapter(adapter);
        } catch (Exception err) {
            System.out.println("on Success err: " + err);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
