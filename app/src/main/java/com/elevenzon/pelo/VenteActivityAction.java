package com.elevenzon.pelo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Vector;

public class VenteActivityAction extends AppCompatActivity {

    Button bt_l3c, bt_blt, bt_mrg, bt_fini;
    Spinner bt_l4c, bt_l5c;
    public static Context get_context = null;
    public static ListView listView = null;
    public static TextView total_paris = null;
    public static TextView valueur = null;

    FragmentManager manager = getSupportFragmentManager();
    Fragment frag = manager.findFragmentByTag("fragment_edit_name");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vente);

        bt_l3c =  (Button) findViewById(R.id.bt_l3c);
        bt_mrg =  (Button) findViewById(R.id.bt_mrg);
        bt_blt =  (Button) findViewById(R.id.bt_blt);
        bt_fini = (Button) findViewById(R.id.bt_fini);

        bt_l4c = findViewById(R.id.bt_l4c);
        ArrayAdapter<CharSequence> l4c_adapter = ArrayAdapter.createFromResource(this,
                R.array.l4c_dropdown_items, android.R.layout.simple_spinner_item);
        l4c_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bt_l4c.setAdapter(l4c_adapter);

        Public.flag = 0;
        bt_l4c.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if(position > 0) {
                    String selectedItem = parent.getItemAtPosition(position).toString();

                    if (frag != null) {
                        manager.beginTransaction().remove(frag).commit();
                    }

                    L4cDialog l4c_dialog = new L4cDialog(selectedItem);
                    l4c_dialog.show(manager, "fragment_edit_name");

                    bt_l4c.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        bt_l5c = findViewById(R.id.bt_l5c);
        ArrayAdapter<CharSequence> l5c_adapter = ArrayAdapter.createFromResource(this,
                R.array.l5c_dropdown_items, android.R.layout.simple_spinner_item);
        l5c_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bt_l5c.setAdapter(l5c_adapter);
        bt_l5c.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if(position > 0) {
                    String selectedItem = parent.getItemAtPosition(position).toString();

                    if (frag != null) {
                        manager.beginTransaction().remove(frag).commit();
                    }

                    L5cDialog l5c_dialog = new L5cDialog(selectedItem);
                    l5c_dialog.show(manager, "fragment_edit_name");

                    bt_l5c.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        get_context = getApplicationContext();
        listView = (ListView) findViewById(R.id.list_view);
        total_paris = (TextView) findViewById(R.id.tds_count);
        valueur = (TextView) findViewById(R.id.vt_sum);

        bt_blt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }

                BltDialog blt_dialog = new BltDialog();
                blt_dialog.show(manager, "fragment_edit_name");
            }
        });

        bt_mrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }

                MrgDialog mrg_dialog = new MrgDialog();
                mrg_dialog.show(manager, "fragment_edit_name");
            }
        });

        bt_mrg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }

                AutoMrg autoMrg = new AutoMrg();
                autoMrg.show(manager, "fragment_edit_name");

                return true;
            }
        });

        bt_l3c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }

                L3cDialog l3c_dialog = new L3cDialog();
                l3c_dialog.show(manager, "fragment_edit_name");
            }
        });

        bt_fini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Public.ticket_data.isEmpty()) {
                    Public.flag = 1;

                    if (frag != null) {
                        manager.beginTransaction().remove(frag).commit();
                    }

                    FiniDialog finiDialog = new FiniDialog();
                    finiDialog.show(manager, "fragment_edit_name");
                } else  {
                    Toast.makeText(getApplicationContext(), "Empty is billet data!\nPlease enter billet data!", Toast.LENGTH_LONG).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (frag != null) {
                    manager.beginTransaction().remove(frag).commit();
                }

                SelectDialog selectDialog = new SelectDialog();
                selectDialog.set_data(i);
                selectDialog.show(manager, "fragment_edit_name");
            }
        });

        Public.calc_sum();
    }

    @Override
    public void onBackPressed() {
        if(!Public.ticket_data.isEmpty()) {

            if (frag != null) {
                manager.beginTransaction().remove(frag).commit();
            }
            ResetTicketInfo resetTicketInfo = new ResetTicketInfo();
            resetTicketInfo.show(manager, "fragment_edit_name");
        } else {
            super.onBackPressed();
        }
    }
}