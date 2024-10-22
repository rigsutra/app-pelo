package com.elevenzon.pelo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;

import com.elevenzon.pelo.utils.SunmiPrintHelper;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    EditText key, password;
    Button login;
    boolean isPasswordValid, isKeyValid;
    TextInputLayout keyError, passError;
    GetImeiDevice getimei;

    RelativeLayout progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AndroidNetworking.initialize(getApplicationContext());
        getimei = new GetImeiDevice(this);
        getimei.getImeiNumber();

        key = (EditText) findViewById(R.id.key);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        keyError = (TextInputLayout) findViewById(R.id.keyError);
        passError = (TextInputLayout) findViewById(R.id.passError);
        progress = findViewById(R.id.progressBar_layout);

        key.setText(Public.device_imei);
//        key.setText("9eacf4e3ed6b6493");
//        key.setText("ef33422747909b0e");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

        Public.activity = this;

        password.requestFocus();
    }

    public void SetValidation() {

        //check for a valid Key
        if(key.getText().toString().isEmpty()) {
            keyError.setError(getResources().getString(R.string.key_error));
            isKeyValid = false;
        } else if (key.getText().length() < 4) {
            keyError.setError("Please enter a minmum password of 4 characters");
            isKeyValid = false;
        } else {
            isKeyValid = true;
            keyError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (password.getText().toString().isEmpty()) {
            passError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (password.getText().length() < 4) {
            passError.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        if (isKeyValid && isPasswordValid ) {
            try {
                ApiClient.login(key.getText().toString(), password.getText().toString(), this);
                progress.setVisibility(View.VISIBLE);
                login.setEnabled(false);
            }catch (Exception ex){
                Toast.makeText(Public.activity.getApplicationContext(), "Not a registered seller!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            ApiClient.logout();
        }catch (Exception ex) {
            System.out.println("app exit: " + ex);
        }
    }
}