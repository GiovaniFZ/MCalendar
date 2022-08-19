package com.fetin.calendarone;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MostrarTodasConv extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receber_conversas);
        TextView mensagem = findViewById(R.id.msgMostr);
        mensagem.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        String msgMandada = intent.getStringExtra("msgMandada");
        setTitle(getResources().getString(R.string.Conversas));
        mensagem.setText(msgMandada);
    }
}
