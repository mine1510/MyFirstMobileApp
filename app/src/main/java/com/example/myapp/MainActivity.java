package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

   private SharedPreferences sharedPreferences;
   private final String SAVE_TEXT = "SAVE_TEXT";
   private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            messageSave = savedInstanceState.getString(EXTRA_MESSAGE);
        }
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.LOLLIPOP){
            ActionBar actionBar = getActionBar();
            actionBar.setHomeButtonEnabled(false);
        }
        setUpActionBar();
        init();
    }

    public void saveButton(View view){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(SAVE_TEXT, editText.getText().toString());
        edit.apply();
    }

    public void init(){
        sharedPreferences = getSharedPreferences("Test",Context.MODE_PRIVATE);
        editText = findViewById(R.id.edit_message);
        editText.setText(sharedPreferences.getString(SAVE_TEXT, ""));
    }

    public void reTextButton(View view){
        editText.setText("");
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(SAVE_TEXT, "");
        edit.apply();
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void setUpActionBar(){
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN){
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        messageSave = message;
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void fileSave(View view){
        Intent intent = new Intent(this, WorkWithFiles.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_search:
                //openSearch();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    public final static String EXTRA_MESSAGE= "com.mycompany.myfirstapp.MESSAGE";
    public static String messageSave;

    @Override
    public void onRestart(){
        super.onRestart();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(EXTRA_MESSAGE, messageSave);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}