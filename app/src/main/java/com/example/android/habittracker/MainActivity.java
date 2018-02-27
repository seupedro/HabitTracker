package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    /* Variavel necessária para fazer o loop infito*/
    private int i = 0;
    /* Torna o Helper(Auxiliador Global (Acessivel por todos os métodos) )*/
    private HabitDbHelper helper;
    /* Cria um Array com 5 Strings */
    private String[] activitys = {"programar", "dançar", "cantar", "malhar", "estudar"};
    /* Cria um Array com 5 ints*/
    private int[] time = {60, 60, 30, 60, 60};

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Encontra o FloatButton no XML e adiciona um Listener */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick( View v ) {
                /* Insere Dados na Database e Mostra no App */
                insertData();
                readData();
            }
        });

    }

    @Override
    protected void onResume() {
        /*
        * Recria uma database limpa toda vez que o app é (re)iniciado
        * para propósito de demonstração.
        * */
        helper = new HabitDbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL(HabitDbHelper.SQL_DELETE_ENTRIES);
        db.execSQL(HabitDbHelper.SQL_CREATE_ENTRIES);
        super.onResume( );
    }

    /* Inseri dados na Database infinitamente */
    private void insertData(){

        /* Faz um Loop infinito para sempre ficar adicionando informações na database */
        if (i == 5) i = 0;

        /* Obtém a database */
        SQLiteDatabase db = helper.getReadableDatabase();

        /* Adiciona informações na database cada vez que é chamado */
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_ACTIVITY, activitys[i]);
        values.put(HabitEntry.COLUMN_HABIT_TIME, time[i]);

        db.insert(HabitEntry.TABLE_NAME, null, values);
        i++;

    }

    /* Mostra Informações da database no app */
    private void readData(){

        /* Obtém a database */
        SQLiteDatabase db = helper.getReadableDatabase();

        /* Cria um cursor para recuperar dados da database*/
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        try {
            /* Encontra uma TextView no Layout para exibir dados da database */
            TextView dbInfo = findViewById(R.id.db_info);
            dbInfo.setText("Dados da database: \n\n" );
            dbInfo.append(
                            HabitEntry._ID + " || " +
                            HabitEntry.COLUMN_HABIT_ACTIVITY  + " || " +
                            HabitEntry.COLUMN_HABIT_TIME + "\n");

            /* Informa as indíces e as colunas em que o cursor deve se mover */
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int activityColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_ACTIVITY);
            int timeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TIME);

            /* Executa e move o cursos para recuperar dados entre as linhas (rows) */
            while(cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                String currentActivity = cursor.getString(activityColumnIndex);
                int currentTime = cursor.getInt(timeColumnIndex);

                /* Acrescenta informações da database ao aplicativo */
                dbInfo.append(
                        currentID + " || " +
                        currentActivity + " || " +
                        currentTime + "\n"
                );
            }

        } finally {
            /* Fecha o cursor para evitar desperdicios de memória */
            cursor.close();
        }
    }
}
