package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteWork extends AppCompatActivity {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_work);

        mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        button =(Button) findViewById(R.id.read_base);
        textView = (TextView) findViewById(R.id.text_base);

    }

    public void readBase(View view){
        String product = "";
        // Отправляем запрос в БД
        Cursor cursor = mDb.rawQuery("SELECT * FROM peoples", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            product += cursor.getString(1) + " | ";
            cursor.moveToNext();
        }
        cursor.close();
        textView.setText(product);
        updateList();
    }
public void updateList() {
    // Список клиентов
    ArrayList<HashMap<String, Object>> peoples = new ArrayList<HashMap<String, Object>>();

    // Список параметров конкретного клиента
    HashMap<String, Object> people;

    Cursor cursor = mDb.rawQuery("SELECT * FROM peoples", null);
    cursor.moveToFirst();

// Пробегаем по всем клиентам
    while (!cursor.isAfterLast()) {
        people = new HashMap<String, Object>();

        // Заполняем клиента
        people.put("name", cursor.getString(1));
        people.put("age", cursor.getString(2));

        // Закидываем клиента в список клиентов
        peoples.add(people);

        // Переходим к следующему клиенту
        cursor.moveToNext();
    }
    cursor.close();

    // Какие параметры клиента мы будем отображать в соответствующих
    // элементах из разметки adapter_item.xml
    String[] from = {"name", "age"};
    int[] to = {R.id.textView, R.id.textView2};

    // Создаем адаптер
    SimpleAdapter adapter = new SimpleAdapter(this, peoples, R.layout.adapter_item, from, to);
    ListView listView = (ListView) findViewById(R.id.listView);
    listView.setAdapter(adapter);

}
    public void addToBase(View v) {
        EditText editName =(EditText)findViewById(R.id.edit_name_base);
        EditText editAge =(EditText)findViewById(R.id.edit_age_base);
        String name1 = editName.getText().toString();
        String age = editAge.getText().toString();
        String query = "INSERT INTO peoples (name, age) VALUES ('"+ name1 + "', '"+ age +"')";
        mDb.execSQL(query);
        updateList();
    }

    public void deleteBase(View view){
        mDb.delete("peoples", null,null);
        textView = (TextView) findViewById(R.id.text_base);
        textView.setText("");
        updateList();
    }

}