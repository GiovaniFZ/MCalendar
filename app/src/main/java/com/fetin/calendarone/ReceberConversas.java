package com.fetin.calendarone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ReceberConversas extends AppCompatActivity {
    TextView texto2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversas);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        handleSend(intent);
    }

        void handleSend (Intent intent){
            Uri fileUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            if (fileUri != null) {
                /*try {
                    InputStream t = getContentResolver().openInputStream(fileUri);
                    String res = lerUri(t);
                    texto2.setText(res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                 */
                String caminho = fileUri.getPath();
                texto2.setText(caminho);
            }
        }

        String lerUri (InputStream inputStream){
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            return result;
        }
    }
