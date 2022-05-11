package com.example.myapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;

import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;
import java.util.ListResourceBundle;

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
        Intent intentCalendar = new Intent(Intent.ACTION_INSERT,
                CalendarContract.Events.CONTENT_URI);
        startActivity(intentCalendar);
    }

    public boolean checkIntent(Intent intent){
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;
        return isIntentSafe;
    }

    static final int PICK_CONTACT_REQUEST = 1;

    public void findNumber(View view){
        Intent pickContact = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        //показывает только номера телефонов
        startActivityForResult(pickContact, PICK_CONTACT_REQUEST);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_CONTACT_REQUEST) {
                Uri contactUri = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri, projection, null,
                        null, null);
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                EditText editCall = (EditText) findViewById(R.id.edit_call);
                editCall.setText(number);
            }
        }
    }
}