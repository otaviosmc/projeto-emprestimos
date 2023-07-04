package com.example.trabalhofinal2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_PROFESSORES =
            "CREATE TABLE professores (cpf TEXT PRIMARY KEY, nome TEXT)";

    private static final String CREATE_TABLE_DISCIPLINAS =
            "CREATE TABLE disciplinas (codigo TEXT PRIMARY KEY, local TEXT, cpfProfessor TEXT, nomeProfessor TEXT)";

    private static final String CREATE_TABLE_EMPRESTIMOS =
            "CREATE TABLE emprestimos (codigo TEXT PRIMARY KEY, cpfProfessor TEXT, nomeProfessor TEXT, horarioPegouChave TEXT, horarioDevolucaoChave TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROFESSORES);
        db.execSQL(CREATE_TABLE_DISCIPLINAS);
        db.execSQL(CREATE_TABLE_EMPRESTIMOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS professores");
        db.execSQL("DROP TABLE IF EXISTS disciplinas");
        db.execSQL("DROP TABLE IF EXISTS emprestimos");
        onCreate(db);
    }
}
