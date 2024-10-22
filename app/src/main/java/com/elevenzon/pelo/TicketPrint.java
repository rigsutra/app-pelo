package com.elevenzon.pelo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.elevenzon.pelo.async.AsyncBluetoothEscPosPrint;
import com.elevenzon.pelo.async.AsyncEscPosPrinter;
import com.elevenzon.pelo.utils.SunmiPrintHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TicketPrint extends AppCompatActivity {

    Button print;

    FragmentManager manager = getSupportFragmentManager();
    Fragment frag = manager.findFragmentByTag("fragment_edit_name");

    View  print_preview;

    boolean printFlag = false;
    private BluetoothConnection selectedDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_print);

        print = findViewById(R.id.print_btn);
        print_preview = findViewById(R.id.print_me_layout);

        TextView all_sum = findViewById(R.id.all_sum);
        TextView ticket_date = findViewById(R.id.ticket_date);
        TextView seller_name = findViewById(R.id.seller_name);
        TextView lotter_and_ticket = findViewById(R.id.lotery_and_ticket);
        TextView companyName = findViewById(R.id.company_name);
        TextView address = findViewById(R.id.address);
        TextView phoneNumber = findViewById(R.id.phone_number);

        LinearLayout bonus_layout = findViewById(R.id.bonus_layout);

//        if(Public.selected_print.equals("Bluetooth Printer")) {
//            all_sum.setTypeface(null, Typeface.NORMAL);
//            ticket_date.setTypeface(null, Typeface.NORMAL);
//            seller_name.setTypeface(null, Typeface.NORMAL);
//            lotter_and_ticket.setTypeface(null, Typeface.NORMAL);
//            companyName.setTypeface(null, Typeface.NORMAL);
//            address.setTypeface(null, Typeface.NORMAL);
//            phoneNumber.setTypeface(null, Typeface.NORMAL);
//        }

        seller_name.setText("Seller: " + Public.sellerName);

        SimpleDateFormat format = new SimpleDateFormat("'on' yyyy-MM-dd 'at' HH:mm:ss");
        ticket_date.setText("date: " + format.format(new Date()));
        lotter_and_ticket.setText("Lot: " + Public.lotteryCategoryName + " // ticket ID: " + Public.ticketId);
        companyName.setText(Public.companyName);
        address.setText("Add: " + Public.address);
        phoneNumber.setText("Phone: " + Public.phoneNumber);

        LinearLayout ticket_info_list_panel = findViewById(R.id.ticket_info_list_panel);

        int sum = 0;
        boolean bonusFlag = false;

        // Iterate over the elements of the ticket_data Vector
        for (int i = 0; i < Public.respon_ticket_data.length(); i++) {
            try {
                // Get the JSONObject at the current index
                JSONObject jsonTicketObject = (JSONObject) Public.respon_ticket_data.get(i);

                if(!jsonTicketObject.getBoolean("bonus")) {

                    // Get the amount value from the JSONObject and add it to the sum
                    sum += jsonTicketObject.optInt("amount");

                    LinearLayout ticket_item_panel = new LinearLayout(this.getApplicationContext());
                    ticket_item_panel.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.weight = 1;

                    TextView gameName = new TextView(this.getApplicationContext());
                    TextView number = new TextView(this.getApplicationContext());
                    TextView amount = new TextView(this.getApplicationContext());

                    gameName.setHeight(40);
                    number.setHeight(40);
                    amount.setHeight(40);

                    amount.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    number.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    gameName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

                    gameName.setTextColor(Color.BLACK);
                    number.setTextColor(Color.BLACK);
                    amount.setTextColor(Color.BLACK);

                    if(Public.selected_print.equals("Bluetooth Printer")) {
                        gameName.setTypeface(null, Typeface.BOLD);
                        number.setTypeface(null, Typeface.BOLD);
                        amount.setTypeface(null, Typeface.BOLD);
                        gameName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                        number.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                        amount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    } else {
                        gameName.setTypeface(null, Typeface.BOLD);
                        number.setTypeface(null, Typeface.BOLD);
                        amount.setTypeface(null, Typeface.BOLD);
                        gameName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        number.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        amount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    }

                    gameName.setLayoutParams(layoutParams);
                    number.setLayoutParams(layoutParams);
                    amount.setLayoutParams(layoutParams);

                    gameName.setText(jsonTicketObject.getString("gameCategory"));
                    number.setText(jsonTicketObject.getString("number"));
                    amount.setText(jsonTicketObject.getString("amount"));

                    ticket_item_panel.addView(gameName);
                    ticket_item_panel.addView(number);
                    ticket_item_panel.addView(amount);

                    ticket_info_list_panel.addView(ticket_item_panel);
                } else {
                    LinearLayout bonus_item_layout = new LinearLayout(this.getApplicationContext());
                    bonus_item_layout.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.weight = 1;

                    TextView title = new TextView(this.getApplicationContext());
                    TextView number = new TextView(this.getApplicationContext());

                    title.setLayoutParams(layoutParams);
                    number.setLayoutParams(layoutParams);

                    title.setHeight(38);
                    number.setHeight(38);

                    number.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    title.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                    title.setTextColor(Color.BLACK);
                    number.setTextColor(Color.BLACK);

                    if(Public.selected_print.equals("Bluetooth Printer")) {
                        title.setTypeface(null, Typeface.NORMAL);
                        number.setTypeface(null, Typeface.NORMAL);
                        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                        number.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    } else {
                        title.setTypeface(null, Typeface.BOLD);
                        number.setTypeface(null, Typeface.BOLD);
                        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        number.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    }

                    number.setText(jsonTicketObject.getString("number"));
                    if(!bonusFlag) {
                        title.setText("Bonus: ");
                    }

                    bonus_item_layout.addView(title);
                    bonus_item_layout.addView(number);
                    bonus_layout.addView(bonus_item_layout);
                    bonusFlag = true;
                }
            } catch (Exception err){System.out.println("ticket item error: " + err);}
        }

        all_sum.setText(Public.numberFormat.format(sum));

        if(Public.block_data.length() > 0 || Public.limit_data.length() > 0) {
            if (frag != null) {
                manager.beginTransaction().remove(frag).commit();
            }
            TicketAlert ticketAlert = new TicketAlert();
            ticketAlert.show(manager, "fragment_edit_name");
        }
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(!printFlag) {
                        if(Public.selected_print.equals("Sunmi Printer")) {
                            SunmiPrintHelper.getInstance().initSunmiPrinterService(Public.activity);
                            Public.sendViewToPrinter(print_preview, Public.reuseBitmap);
                        } else if(Public.selected_print.equals("Bluetooth Printer")) {
                            printBluetooth();
                        } else {
                            Toast.makeText(Public.activity.getApplicationContext(), "This Printer Device is not support!", Toast.LENGTH_LONG).show();
                        }
                        printFlag = true;
                        Public.respon_ticket_data = new JSONArray();
                        Public.backendCheckFlag = true;
                        Public.calc_sum();
                    } else {
                        Toast.makeText(Public.activity.getApplicationContext(), "Pleas wait...", Toast.LENGTH_SHORT).show();
                        printFlag = false;
                    }
                } catch (Exception err) {
                    System.out.println("print button: " + err);
                }
            }
        });
    }

    public void printBluetooth() {
        new AsyncBluetoothEscPosPrint(this).execute(this.getAsyncEscPosPrinter(selectedDevice));
    }

    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 300, 48f, 24);

        return printer.setTextToPrint(
                "[L]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, Public.scaleImage(Public.convertViewToBitmap(print_preview, Public.reuseBitmap), Public.reuseBitmap)) + "</img>"
        );
    }

    @Override
    public void onBackPressed() {
        if(Public.respon_ticket_data.length() > 0) {
            Toast.makeText(Public.activity, "This ticket already saved to backend!\nPlease print this ticket!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Public.activity, VenteRapport.class);
            Public.activity.startActivity(intent);
        }
    }
}