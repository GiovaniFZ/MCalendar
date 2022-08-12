package com.fetin.calendarone;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ListPessoasData extends AppCompatActivity {

    // Variaveis globais
    SQLiteDatabase db;
    ListView listViewPessoas;
    ArrayList<Integer> arrayIds;

    // Definicoes do banco de dados
    private static final String BANCO_NOME = "bd_pessoa";
    private static final String NOME_TABELA = "tb_pessoa";
    private static final String COLUNA_NOME = "Nome";
    private static final String COLUNA_CODIGO = "id";
    private static final String COLUNA_MENSAGENS = "Mensagem";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_pessoa);
        listViewPessoas = findViewById(R.id.listViewPessoas2);

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

        ListarPessoas2(dataComparar);

        listViewPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent(getApplicationContext(), MostraConversasData.class);
                intent2.putExtra("dataComparar", dataComparar);
                intent2.putExtra("position", position);
                startActivity(intent2);
            }
        });

    }

    public void ListarPessoas2(String dataComparar){
        try {
            arrayIds = new ArrayList<>();
            db = openOrCreateDatabase(BANCO_NOME, MODE_PRIVATE, null);
            String query = "SELECT " + COLUNA_CODIGO + ", " + COLUNA_NOME + " FROM " + NOME_TABELA + " WHERE " +
                    COLUNA_MENSAGENS + " LIKE '%" + dataComparar + "%'";
            Cursor meuCursor = db.rawQuery(query, null);
            ArrayList<String> linhas = new ArrayList<String>();
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, linhas
            );
            listViewPessoas.setAdapter(adapter);
            meuCursor.moveToFirst();
            while(meuCursor!=null){
                linhas.add(meuCursor.getString(1));
                arrayIds.add(meuCursor.getInt(0));
                meuCursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
