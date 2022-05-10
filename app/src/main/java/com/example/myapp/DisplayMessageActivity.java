package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    private Button but1,but2;
    private FragmentOne fragmentOne = new FragmentOne();
    private FragmentTwo fragmentTwo = new FragmentTwo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Intent intent = getIntent(); //Получаем сообщение из Intent
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView =findViewById(R.id.TextView);
        textView.setTextSize(40);
        textView.setText(message);

        but1 = findViewById(R.id.but_fr_1);
        but2 = findViewById(R.id.but_fr_2);

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewFragment(fragmentOne);
            }
        });
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewFragment(fragmentTwo);
            }
        });
    }

    private void setNewFragment(Fragment fragment) {
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fr_message, fragment);
        ft.commit();
    }

}