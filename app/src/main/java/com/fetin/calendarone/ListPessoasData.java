package com.fetin.calendarone;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

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
        String dataComparar;
        String dataOrg;
        // Obtendo dia, mes e ano
        int dia = intent.getIntExtra("day", 0);
        int mes = intent.getIntExtra("month", 0);
        int ano = intent.getIntExtra("year", 0);

        // Janeiro é tratado como zero, no nosso app
        mes += 1;
        // Transformando os dados em strings para comparação
        String diaToString = Integer.toString(dia);
        String mesToString = Integer.toString(mes);
        String anoToString = Integer.toString(ano);

        if(Locale.getDefault().getDisplayLanguage().equals("English")) {
            // Anos nos txts to Whatsapp são tratados com os ultimos dois digitos do ano, se estiver em ingles
            int ano2 = ano - 2000;
            anoToString = Integer.toString(ano2);
            dataComparar = mesToString + "/" + diaToString + "/" + anoToString;
            dataOrg = diaToString + "/" + mesToString + "/" + anoToString;
            setTitle(dataOrg); // Colocando o titulo da activity como o dia selecionado
        }else {
            String mesPt;
            String diaPt;

            if(mes < 10){
                mesPt = '0' + mesToString;
            }else{
                mesPt = mesToString;
            }

            if(dia < 10){
                diaPt = '0' + diaToString;
            }else{
                diaPt = diaToString;
            }
            dataComparar = diaPt + "/" + mesPt + "/" + anoToString;
            dataOrg = dataComparar;
            setTitle(dataComparar);
        }


        ListarPessoas2(dataComparar);
        listViewPessoas.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent2 = new Intent(getApplicationContext(), MostraConversasData.class);
            intent2.putExtra("dataComparar", dataComparar);
            intent2.putExtra("dataOrg", dataOrg);
            intent2.putExtra("position", position);
            startActivity(intent2);
        });

    }

    public void ListarPessoas2(String dataComparar){
        try {
            arrayIds = new ArrayList<>();
            db = openOrCreateDatabase(BANCO_NOME, MODE_PRIVATE, null);
            String query = "SELECT " + COLUNA_CODIGO + ", " + COLUNA_NOME + " FROM " + NOME_TABELA + " WHERE " +
                    COLUNA_MENSAGENS + " LIKE '%" + dataComparar + "%'";
            Cursor meuCursor = db.rawQuery(query, null);
            ArrayList<String> linhas = new ArrayList<>();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, linhas
            );
            listViewPessoas.setAdapter(adapter);
            meuCursor.moveToFirst();
            while(meuCursor!=null){
                linhas.add(meuCursor.getString(1));
                arrayIds.add(meuCursor.getInt(0));
                meuCursor.moveToNext();
            }
                meuCursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
