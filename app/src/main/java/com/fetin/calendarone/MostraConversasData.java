package com.fetin.calendarone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MostraConversasData extends AppCompatActivity {
    SQLiteDatabase db;
    private static final String BANCO_NOME = "bd_pessoa";
    private static final String NOME_TABELA = "tb_pessoa";
    private static final String COLUNA_MENSAGENS = "Mensagem";
    String mensagem;

    protected void onCreate(Bundle savedInstanceState) {
        db = openOrCreateDatabase(BANCO_NOME, MODE_PRIVATE, null);

        Intent intent = getIntent();
        String dataComparar = intent.getStringExtra("dataComparar");
        String dataOrg = intent.getStringExtra("dataOrg");
        int posicao = intent.getIntExtra("position", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receber_conversas);

        setTitle(getResources().getString(R.string.Conversas_em) + ' ' + dataOrg);

        TextView msgMostr = findViewById(R.id.msgMostr);
        msgMostr.setMovementMethod(new ScrollingMovementMethod());

        // Montando mensagens
        montaMensagens(dataComparar, posicao);
        String mensagem2 = mostraMsgData(dataComparar);
        // Log.d("Montando mensagem: ", mensagem.substring(0,7)); 3/8/22
        msgMostr.setText(mensagem2);
        db.close();
    }

    @SuppressLint("Range")
    void montaMensagens(String dataComparar, int posicao) {
        String query = "SELECT " + COLUNA_MENSAGENS + " FROM " + NOME_TABELA + " WHERE " +
                COLUNA_MENSAGENS + " LIKE '%" + dataComparar + "%'";
        Cursor cur = db.rawQuery(query, null);
        cur.moveToPosition(posicao);
        mensagem = cur.getString(cur.getColumnIndex(COLUNA_MENSAGENS));
        // Fechando cursor e database
        db.close();
        cur.close();
    }

    String mostraMsgData(String dataComparar) {
        StringBuilder mensagem2 = new StringBuilder();
        int comp = dataComparar.length();
        Pattern pt;
        if(Locale.getDefault().getDisplayLanguage().equals("English")) {
            pt = Pattern.compile("^[1-9]+[/][1-9]+[/][0-2][0-2]$");
        }
        else{
            pt = Pattern.compile("^[0-9][0-9][/][0-9][0-9][/][0-2][0-2][0-2][0-2]$");
        }

        boolean encontrou = false; // A data ainda não foi encontrada
        int comparador;

        for (int i = 0; i < mensagem.length(); i++) {
            comparador = Character.compare(mensagem.charAt(i), '\n');

            if (i + comp <= mensagem.length()) {
                if (mensagem.substring(i, i+comp).equals(dataComparar)) { // Caso a data selecionada já esteja nos primeiros caracteres
                    encontrou = true;
                }
            }

            if (comparador == 0 && i + comp <= mensagem.length()) { // Se houve quebra de linha
                i++;
                boolean data = pt.matcher(mensagem.substring(i, i+comp)).matches();

                if (mensagem.substring(i, i + comp).equals(dataComparar)) { // Caso os proximos caracteres sejam a data selecionada
                    encontrou = true;
                    mensagem2.append('\n');
                }else{
                    if(encontrou && data) { // Caso ele ja tenha encontrado a data, e os proximos caracteres sejam a data, mas não sejam a selecionada
                        break;
                    }else {
                        if (encontrou && !data) { // Caso ele já tenha encontrado a data, mas os proximos caracteres não sejam uma data
                            mensagem2.append('\n');
                        }
                    }
                }
            }
            if (encontrou) {
                mensagem2.append(mensagem.charAt(i));
            }
        }
        return mensagem2.toString();
    }
}
