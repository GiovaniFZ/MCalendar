package com.fetin.calendarone;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReceberConversas extends AppCompatActivity {
    TextView texto2;
    String Msg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversas);

        texto2 = findViewById(R.id.TextoRecebido);
        texto2.setMovementMethod(new ScrollingMovementMethod());
        FloatingActionButton botaoEnviar = findViewById(R.id.enviarBotao);

        Intent intent = getIntent();
        // Action: intent.action.SEND_MULTIPLE();
        // Type: text/*
        handleSend(intent);
        botaoEnviar.setOnClickListener(v -> {
            Intent i2 = new Intent(getApplicationContext(), SelecionarPessoa.class);
            i2.putExtra("sharedText", Msg);
            startActivity(i2);
        });

    }

    void handleSend(Intent intent) {
        ArrayList<Uri> uris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        for(Uri i: uris){
            String mimeType = getContentResolver().getType(i);
            if(mimeType.equals("text/plain")){
                lerTexto(uris);
                Msg = lerTexto(uris);
                texto2.setText(Msg);
            }
        }
        }

        private String lerTexto(ArrayList<Uri> uris){
            ContentResolver cr = getApplicationContext().getContentResolver();
            StringBuilder total = new StringBuilder();
            try {
                InputStream is = cr.openInputStream(uris.get(0));
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                for (String line; (line = r.readLine()) != null; ) {
                    total.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return total.toString();
    }
}
