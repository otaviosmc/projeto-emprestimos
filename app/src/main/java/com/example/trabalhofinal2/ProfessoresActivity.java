package com.example.trabalhofinal2;

import androidx.appcompat.app.AppCompatActivity;
import com.example.trabalhofinal2.R;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.example.trabalhofinal2.DatabaseHelper;


import java.util.ArrayList;
import java.util.List;

public class ProfessoresActivity extends AppCompatActivity {

    private List<String> listaProfessores = new ArrayList<>();
    private ArrayAdapter<String> professoresAdapter;

    private EditText editCpf;
    private EditText editNome;
    private ListView listViewProfessores;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professores);

        editCpf = findViewById(R.id.editCpf);
        editNome = findViewById(R.id.editNome);
        listViewProfessores = findViewById(R.id.listViewProfessores);

        professoresAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaProfessores);
        listViewProfessores.setAdapter(professoresAdapter);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    public void cadastrarProfessor(View view) {
        String cpf = editCpf.getText().toString();
        String nome = editNome.getText().toString();
        String professor = "CPF: " + cpf + ", Nome: " + nome;
        listaProfessores.add(professor);
        professoresAdapter.notifyDataSetChanged();
        limparCampos();

        String insertQuery = "INSERT INTO professores (cpf, nome) VALUES ('" + cpf + "', '" + nome + "')";
        database.execSQL(insertQuery);
    }

    public void listarProfessores(View view) {
        listaProfessores.clear();

        Cursor cursor = database.rawQuery("SELECT cpf, nome FROM professores", null);

        if (cursor.moveToFirst()) {
            do {
                String cpf = cursor.getString(0);
                String nome = cursor.getString(1);
                String professor = "CPF: " + cpf + ", Nome: " + nome;
                listaProfessores.add(professor);
            } while (cursor.moveToNext());
        }

        cursor.close();
        professoresAdapter.notifyDataSetChanged();
    }

    public void atualizarProfessor(View view) {
        String cpf = editCpf.getText().toString();
        String nome = editNome.getText().toString();

        String updateQuery = "UPDATE professores SET nome = '" + nome + "' WHERE cpf = '" + cpf + "'";
        database.execSQL(updateQuery);

        limparCampos();
        listarProfessores(null);
    }


    public void deletarProfessor(View view) {
        String cpf = editCpf.getText().toString();

        String deleteQuery = "DELETE FROM professores WHERE cpf = '" + cpf + "'";
        database.execSQL(deleteQuery);

        limparCampos();
        listarProfessores(null);
    }


    private void limparCampos() {
        editCpf.setText("");
        editNome.setText("");
    }
}
