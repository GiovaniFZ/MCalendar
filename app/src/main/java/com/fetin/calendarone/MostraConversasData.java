package com.fetin.calendarone;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MostraConversasData extends AppCompatActivity {
    SQLiteDatabase db;
    private static final String BANCO_NOME = "bd_pessoa";
    private static final String NOME_TABELA = "tb_pessoa";
    private static final String COLUNA_NOME = "Nome";
    private static final String COLUNA_CODIGO = "id";
    private static final String COLUNA_MENSAGENS = "Mensagem";

    protected void onCreate(Bundle savedInstanceState) {
        db = openOrCreateDatabase(BANCO_NOME, MODE_PRIVATE, null);

        Intent intent = getIntent();
        String dataComparar = intent.getStringExtra("dataComparar");
        int posicao = intent.getIntExtra("position", 0);
        String mensagem = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receber_conversas);
        String query = "SELECT " + COLUNA_MENSAGENS + " FROM " + NOME_TABELA + " WHERE " +
                COLUNA_MENSAGENS + " LIKE '%" + dataComparar + "%'";
        TextView msgMostr = findViewById(R.id.msgMostr);
        msgMostr.setMovementMethod(new ScrollingMovementMethod());

        Cursor cur = db.rawQuery(query, null);

            if (cur.moveToFirst()) {
                do {
                    mensagem = cur.getString(posicao);
                } while (cur.moveToNext());
        }
            msgMostr.setText(mensagem);
    }
}
