package com.fetin.calendarone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MostraConvData extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_conversas);

        Intent intent = getIntent();
        int dia = intent.getIntExtra("day", 0);
        int mes = intent.getIntExtra("month", 0);
        int ano = intent.getIntExtra("year", 0);

        // Janeiro é tratado como zero
        mes += 1;
        // Anos nos txts to Whatsapp são tratados com os ultimos dois digitos do ano.
        if(ano > 2000) {
            ano = ano - 2000;
        }else
            if(ano > 1900){
                ano = ano - 1900;
            }
        // Transformando os dados em strings para comparação
        String diaToString = Integer.toString(dia);
        String mesToString = Integer.toString(mes);
        String anoToString = Integer.toString(ano);
        String dataComparar = mesToString + "/" + diaToString + "/" + anoToString;
        TextView msgMostr = findViewById(R.id.msgMostr);
        msgMostr.setText(dataComparar);
    }
}
