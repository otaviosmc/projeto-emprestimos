package com.example.trabalhofinal2;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trabalhofinal2.R;
import android.os.Bundle;
import com.example.trabalhofinal2.DisciplinasActivity;
import com.example.trabalhofinal2.ProfessoresActivity;
import com.example.trabalhofinal2.EmprestimosActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirProfessoresActivity(View view) {
        Intent intent = new Intent(this, ProfessoresActivity.class);
        startActivity(intent);
    }

    public void abrirDisciplinasActivity(View view) {
        Intent intent = new Intent(this, DisciplinasActivity.class);
        startActivity(intent);
    }

    public void abrirEmprestimosActivity(View view) {
        Intent intent = new Intent(this, EmprestimosActivity.class);
        startActivity(intent);
    }
}
