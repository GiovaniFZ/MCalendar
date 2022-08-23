package com.fetin.calendarone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SelecionarPessoa extends AppCompatActivity {
    ListView listViewPessoas;
    ArrayList<Integer> arrayIds;
    SQLiteDatabase db;
    public Integer idSelecionado;

    private static final String BANCO_NOME = "bd_pessoa";
    private static final String NOME_TABELA = "tb_pessoa";
    private static final String COLUNA_NOME = "Nome";
    private static final String COLUNA_CODIGO = "id";
    private static final String COLUNA_MENSAGENS = "Mensagem";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listapessoas);

        setTitle(getResources().getString(R.string.selecionar_pessoa));
        listViewPessoas = findViewById(R.id.ListaPessoas);
        Button criarBot = findViewById(R.id.criarBot);
        EditText CampoPessoa = findViewById(R.id.campoPessoa);

        Intent intent = getIntent();
        String Mensagem = intent.getStringExtra("sharedText");
        ListarPessoas();

        criarBot.setOnClickListener(view -> {
            String NomeDigitado = CampoPessoa.getText().toString();
            if (NomeDigitado.isEmpty()) {
                Toast.makeText(this, getResources().getString(R.string.inserir_nome), Toast.LENGTH_SHORT).show();
            } else {
                criarPessoa(NomeDigitado);
                Toast.makeText(SelecionarPessoa.this, getResources().getString(R.string.pessoa_adicionada), Toast.LENGTH_SHORT).show();
                ListarPessoas();
            }
        });


        listViewPessoas.setOnItemClickListener((parent, view, position, id) -> {
            idSelecionado = arrayIds.get(position);
            adicionarMensagem(idSelecionado, Mensagem);
            Toast.makeText(SelecionarPessoa.this, getResources().getString(R.string.mensagens_adicionadas_a) + ' ' + nome(position) , Toast.LENGTH_LONG).show();
            Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent2);
        });
    }

    public void ListarPessoas(){
        try {
            arrayIds = new ArrayList<>();
            db = openOrCreateDatabase(BANCO_NOME, MODE_PRIVATE, null);
            String query = "SELECT " + COLUNA_CODIGO + ", " + COLUNA_NOME + " FROM " + NOME_TABELA;
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
    public void adicionarMensagem(int id, String Mensagem){
        try{
        db = openOrCreateDatabase(BANCO_NOME, MODE_PRIVATE, null);
        String sql = "UPDATE " + NOME_TABELA + " SET " + COLUNA_MENSAGENS + "=? WHERE " + COLUNA_CODIGO + "=?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, Mensagem);
        stmt.bindLong(2, id);
        stmt.executeUpdateDelete();
        db.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void criarPessoa(String NomeDigitado){
        try{
            db = openOrCreateDatabase(BANCO_NOME, MODE_PRIVATE, null);
            String sql = "INSERT INTO " + NOME_TABELA + " (" + COLUNA_NOME + ") VALUES (?)";
            SQLiteStatement stmt = db.compileStatement(sql);
            stmt.bindString(1,NomeDigitado);
            stmt.executeInsert();
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @SuppressLint("Range")
    String nome(int position){
        db = openOrCreateDatabase(BANCO_NOME, MODE_PRIVATE, null);
        String query = "SELECT " + COLUNA_CODIGO + ", " + COLUNA_NOME + " FROM " + NOME_TABELA;
        Cursor cur = db.rawQuery(query, null);
        cur.moveToPosition(position);
        String string_fim = cur.getString(cur.getColumnIndex(COLUNA_NOME));
        cur.close();
        return string_fim;
    }


}
