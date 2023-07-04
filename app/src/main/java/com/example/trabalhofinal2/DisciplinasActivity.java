package com.example.trabalhofinal2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.trabalhofinal2.R;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.example.trabalhofinal2.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DisciplinasActivity extends AppCompatActivity {

    private List<String> listaDisciplinas = new ArrayList<>();
    private ArrayAdapter<String> disciplinasAdapter;

    private EditText editCodigoDisciplina;
    private EditText editLocal;
    private EditText editCpfProfessorDisciplina;
    private EditText editNomeProfessorDisciplina;
    private ListView listViewDisciplinas;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);

        editCodigoDisciplina = findViewById(R.id.editCodigoDisciplina);
        editLocal = findViewById(R.id.editLocal);
        editCpfProfessorDisciplina = findViewById(R.id.editCpfProfessorDisciplina);
        editNomeProfessorDisciplina = findViewById(R.id.editNomeProfessorDisciplina);
        listViewDisciplinas = findViewById(R.id.listViewDisciplinas);

        disciplinasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDisciplinas);
        listViewDisciplinas.setAdapter(disciplinasAdapter);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
    }
    public void cadastrarDisciplina(View view) {
        String codigo = editCodigoDisciplina.getText().toString();
        String local = editLocal.getText().toString();
        String cpfProfessor = editCpfProfessorDisciplina.getText().toString();
        String nomeProfessor = editNomeProfessorDisciplina.getText().toString();
        String disciplina = "Código: " + codigo + ", Local: " + local + ", CPF do Professor: " + cpfProfessor + ", Nome do Professor: " + nomeProfessor;
        listaDisciplinas.add(disciplina);
        disciplinasAdapter.notifyDataSetChanged();
        limparCampos();

        String insertQuery = "INSERT INTO disciplinas (codigo, local, cpfProfessor, nomeProfessor) VALUES ('" + codigo + "', '" + local + "', '" + cpfProfessor + "', '" + nomeProfessor + "')";
        database.execSQL(insertQuery);
    }
    public void listarDisciplinas(View view) {

        listaDisciplinas.clear();

        Cursor cursor = database.rawQuery("SELECT codigo, local, cpfProfessor, nomeProfessor FROM disciplinas", null);

        if (cursor.moveToFirst()) {
            do {
                String codigo = cursor.getString(0);
                String local = cursor.getString(1);
                String cpfProfessor = cursor.getString(2);
                String nomeProfessor = cursor.getString(3);
                String disciplina = "Código: " + codigo + ", Local: " + local + ", CPF do Professor: " + cpfProfessor + ", Nome do Professor: " + nomeProfessor;
                listaDisciplinas.add(disciplina);
            } while (cursor.moveToNext());
        }

        cursor.close();
        disciplinasAdapter.notifyDataSetChanged();
    }
    public void atualizarDisciplina(View view) {
        String codigo = editCodigoDisciplina.getText().toString();
        String local = editLocal.getText().toString();
        String cpfProfessor = editCpfProfessorDisciplina.getText().toString();
        String nomeProfessor = editNomeProfessorDisciplina.getText().toString();

        String updateQuery = "UPDATE disciplinas SET local = '" + local + "', cpfProfessor = '" + cpfProfessor + "', nomeProfessor = '" + nomeProfessor + "' WHERE codigo = '" + codigo + "'";
        database.execSQL(updateQuery);

        limparCampos();
        listarDisciplinas(null);
    }
    public void deletarDisciplina(View view) {
        String codigo = editCodigoDisciplina.getText().toString();

        String deleteQuery = "DELETE FROM disciplinas WHERE codigo = '" + codigo + "'";
        database.execSQL(deleteQuery);

        limparCampos();
        listarDisciplinas(null);
    }
    private void limparCampos() {
        editCodigoDisciplina.setText("");
        editLocal.setText("");
        editCpfProfessorDisciplina.setText("");
        editNomeProfessorDisciplina.setText("");
    }
}
