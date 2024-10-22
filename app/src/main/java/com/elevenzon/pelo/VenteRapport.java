package com.elevenzon.pelo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.elevenzon.pelo.utils.SunmiPrintHelper;

public class VenteRapport extends AppCompatActivity {

    Button vente, rapport, replay, option;

    FragmentManager manager = getSupportFragmentManager();
    Fragment frag = manager.findFragmentByTag("fragment_edit_name");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vente_rapport);

        vente = (Button) findViewById(R.id.bt_vente);
        rapport = (Button) findViewById(R.id.bt_rapport);
        replay = findViewById(R.id.bt_replay);
        option = (Button)findViewById(R.id.print_option);

        Public.sharedPreferences = getSharedPreferences("LOCAL_DATA", MODE_PRIVATE);

        Public.selected_print = Public.sharedPreferences.getString("printer_device", "");

        //vente on touch
        vente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vente_page_call();
            }
        });

        //rapport on touch
        rapport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Public.selected_print == "") {
                    Toast.makeText(Public.activity.getApplicationContext(), "You have to choose the printer device!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Public.activity.getApplicationContext(), Public.selected_print + " Device choosed for printer!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), RapportActivity.class);
                    startActivity(intent);
                }
            }
        });

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Public.selected_print == "") {
                    Toast.makeText(Public.activity.getApplicationContext(), "You have to choose the printer device!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Public.activity.getApplicationContext(), Public.selected_print + " Device choosed for printer!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ReplayTicket.class);
                    startActivity(intent);
                }
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }

                PrinterOption printerOption = new PrinterOption();
                printerOption.show(manager, "fragment_edit_name");
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void vente_page_call() {
        if(Public.selected_print == "") {
            Toast.makeText(Public.activity.getApplicationContext(), "You have to choose the printer device!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(Public.activity.getApplicationContext(), Public.selected_print + " Device choosed for printer!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), LotteryCategoryPage.class);
            startActivity(intent);
        }
    }
}