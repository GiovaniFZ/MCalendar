package com.fetin.calendarone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = findViewById(R.id.botaoSelect);
        Button b2 = findViewById(R.id.botaoAbrir);
        FloatingActionButton b3 = findViewById(R.id.BotFlut);

        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }


        b1.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        b2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Conversas.class);
            startActivity(intent);
        });

        b3.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ListaPessoas.class);
            startActivity(intent);
        });


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        Intent intent = new Intent(this, ListPessoasData.class);
        intent.putExtra("day", day);
        intent.putExtra("month", month);
        intent.putExtra("year", year);
        startActivity(intent);
    }

    // Menus
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.tutorial:
                Intent intent = new Intent(this, Tutorial.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}