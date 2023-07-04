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

public class EmprestimosActivity extends AppCompatActivity {

    private List<String> listaEmprestimos = new ArrayList<>();
    private ArrayAdapter<String> emprestimosAdapter;

    private EditText editCodigoEmprestimo;
    private EditText editCpfProfessorEmprestimo;
    private EditText editNomeProfessorEmprestimo;
    private EditText editHorarioPegouChave;
    private EditText editHorarioDevolucaoChave;
    private ListView listViewEmprestimos;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emprestimos);

        editCodigoEmprestimo = findViewById(R.id.editCodigoEmprestimo);
        editCpfProfessorEmprestimo = findViewById(R.id.editCpfProfessorEmprestimo);
        editNomeProfessorEmprestimo = findViewById(R.id.editNomeProfessorEmprestimo);
        editHorarioPegouChave = findViewById(R.id.editHorarioPegouChave);
        editHorarioDevolucaoChave = findViewById(R.id.editHorarioDevolucaoChave);
        listViewEmprestimos = findViewById(R.id.listViewEmprestimos);

        emprestimosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaEmprestimos);
        listViewEmprestimos.setAdapter(emprestimosAdapter);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    public void cadastrarEmprestimo(View view) {
        String codigo = editCodigoEmprestimo.getText().toString();
        String cpfProfessor = editCpfProfessorEmprestimo.getText().toString();
        String nomeProfessor = editNomeProfessorEmprestimo.getText().toString();
        String horarioPegouChave = editHorarioPegouChave.getText().toString();
        String horarioDevolucaoChave = editHorarioDevolucaoChave.getText().toString();
        String emprestimo = "Código: " + codigo + ", CPF do Professor: " + cpfProfessor + ", Nome do Professor: " + nomeProfessor + ", Horário de pegou a chave: " + horarioPegouChave + ", Horário da devolução da chave: " + horarioDevolucaoChave;
        listaEmprestimos.add(emprestimo);
        emprestimosAdapter.notifyDataSetChanged();
        limparCampos();

        String insertQuery = "INSERT INTO emprestimos (codigo, cpfProfessor, nomeProfessor, horarioPegouChave, horarioDevolucaoChave) VALUES ('" + codigo + "', '" + cpfProfessor + "', '" + nomeProfessor + "', '" + horarioPegouChave + "', '" + horarioDevolucaoChave + "')";
        database.execSQL(insertQuery);
    }

    public void listarEmprestimos(View view) {
        listaEmprestimos.clear();

        Cursor cursor = database.rawQuery("SELECT codigo, cpfProfessor, nomeProfessor, horarioPegouChave, horarioDevolucaoChave FROM emprestimos", null);

        if (cursor.moveToFirst()) {
            do {
                String codigo = cursor.getString(0);
                String cpfProfessor = cursor.getString(1);
                String nomeProfessor = cursor.getString(2);
                String horarioPegouChave = cursor.getString(3);
                String horarioDevolucaoChave = cursor.getString(4);
                String emprestimo = "Código: " + codigo + ", CPF do Professor: " + cpfProfessor + ", Nome do Professor: " + nomeProfessor + ", Horário de pegou a chave: " + horarioPegouChave + ", Horário da devolução da chave: " + horarioDevolucaoChave;
                listaEmprestimos.add(emprestimo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        emprestimosAdapter.notifyDataSetChanged();
    }

    public void atualizarEmprestimo(View view) {
        String codigo = editCodigoEmprestimo.getText().toString();
        String cpfProfessor = editCpfProfessorEmprestimo.getText().toString();
        String nomeProfessor = editNomeProfessorEmprestimo.getText().toString();
        String horarioPegouChave = editHorarioPegouChave.getText().toString();
        String horarioDevolucaoChave = editHorarioDevolucaoChave.getText().toString();

        String updateQuery = "UPDATE emprestimos SET cpfProfessor = '" + cpfProfessor + "', nomeProfessor = '" + nomeProfessor + "', horarioPegouChave = '" + horarioPegouChave + "', horarioDevolucaoChave = '" + horarioDevolucaoChave + "' WHERE codigo = '" + codigo + "'";
        database.execSQL(updateQuery);

        limparCampos();
        listarEmprestimos(null);
    }


    public void deletarEmprestimo(View view) {
        String codigo = editCodigoEmprestimo.getText().toString();

        String deleteQuery = "DELETE FROM emprestimos WHERE codigo = '" + codigo + "'";
        database.execSQL(deleteQuery);

        limparCampos();
        listarEmprestimos(null);
    }

    private void limparCampos() {
        editCodigoEmprestimo.setText("");
        editCpfProfessorEmprestimo.setText("");
        editNomeProfessorEmprestimo.setText("");
        editHorarioPegouChave.setText("");
        editHorarioDevolucaoChave.setText("");
    }
}
