package com.example.webappjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button find = findViewById(R.id.findButton);
        find.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView editText = findViewById(R.id.findText);
                String message = editText.getText().toString();

                Intent intent = new Intent(getBaseContext(), FindActivity.class);
                intent.putExtra("name", message);
                startActivity(intent);
            }
        });

        final Button delete = findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView editText = findViewById(R.id.deleteText);
                String message = editText.getText().toString();

                Intent intent = new Intent(getBaseContext(), DeleteActivity.class);
                intent.putExtra("name", message);
                startActivity(intent);
            }
        });

        final Button create = findViewById(R.id.createButton);
        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CreateActivity.class);
                startActivity(intent);
            }
        });

        final Button viewAll = findViewById(R.id.viewAllButton);
        viewAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ViewAllActivity.class);
                startActivity(intent);
            }
        });
    }
}
