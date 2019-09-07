package com.example.db_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("mylist");

        Toast.makeText(getBaseContext(), myList+"", Toast.LENGTH_LONG).show();
    }
}
