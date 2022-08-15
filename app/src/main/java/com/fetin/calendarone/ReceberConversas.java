package com.fetin.calendarone;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class ReceberConversas extends AppCompatActivity {
    TextView texto2;
    private static final int READ_REQUEST_CODE = 42;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversas);

        texto2 = findViewById(R.id.TextoRecebido);

        Intent intent = getIntent();
        // Action: intent.action.SEND_MULTIPLE();
        // Type: text/*

        handleSend(intent);
    }

    void handleSend(Intent intent) {
        ArrayList<Uri> uris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        for(Uri i: uris){
            String mimeType = getContentResolver().getType(i);
            if(mimeType.equals("text/plain")){
                lerTexto(uris);
                String Teste = lerTexto(uris);
                texto2.setText(Teste);
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
