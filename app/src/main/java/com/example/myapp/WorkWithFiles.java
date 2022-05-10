package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class WorkWithFiles extends AppCompatActivity {

    private EditText edit;
    private String text;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;
    private InputStreamReader inputStreamReader;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_with_files);
        edit =(EditText) findViewById(R.id.file_message);
        isEternalStorageReadable();
    }

    public boolean isEternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state) ||
        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            return true;
        }
        return false;
    }

    public void readMessageFile(View view){
        try {
            fileInputStream = openFileInput("save_text.txt");
            inputStreamReader =  new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            while ((lines=bufferedReader.readLine())!=null) {
                stringBuffer.append(lines + "\n");
            }
            edit.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMessageFile(View view){
        text = edit.getText().toString();
        try {
            fileOutputStream = openFileOutput("save_text.txt",MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();
            edit.setText("");
            Toast.makeText(WorkWithFiles.this,getString(R.string.saved),Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void fileDelete(View view){
        file = new File(getFilesDir(),"save_text.txt");
        file.delete();
        edit.setText("");
        Toast.makeText(WorkWithFiles.this,getString(R.string.deleted),Toast.LENGTH_LONG).show();
    }

    public void goToBase(View view){
        Intent intent = new Intent(this, SQLiteWork.class);
        startActivity(intent);
    }

}