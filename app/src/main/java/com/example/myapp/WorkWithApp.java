package com.example.myapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.EditText;

import java.io.InputStreamReader;
import java.util.Calendar;

public class WorkWithApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_with_app);
    }

    public void callButton(View view){
        EditText editNumber = (EditText) findViewById(R.id.edit_call);
        String numberCall = editNumber.getText().toString();
        Uri number = Uri.parse("tel:"+numberCall);
        Intent callIntent = new Intent(Intent.ACTION_DIAL,number);
        startActivity(callIntent);
    }
    public void openBrowser(View view){
        Uri webpage = Uri.parse("https://yandex.ru/");
        Intent intentWeb = new Intent(Intent.ACTION_VIEW,webpage);
        startActivity(intentWeb);
    }

    public void openMap(View view){
        Uri location = Uri.parse("geo:0,0?q=РТУ МИРЭА, 78");
        Intent intentMap = new Intent(Intent.ACTION_VIEW, location);
        startActivity(intentMap);
    }

    public void openCalendar(View view){
        Intent intentCalendar = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        startActivity(intentCalendar);
    }
}