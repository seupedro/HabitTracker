package com.example.android.habittracker.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.habittracker.data.HabitContract.HabitEntry;

/**
 * Created by phartmann on 27/02/2018.
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    /* Versão da Tabela, usado para propósitos de atualização da tabela */
    public static final int DATABASE_VERSION = 2;
    /* Constante com o nome da tabela */
    public static final String DATABASE_NAME = "habit";
    /* Constante para criar uma entrada na database, nesse case a tabela e suas colunas */
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HabitEntry.TABLE_NAME + "( "
            + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HabitEntry.COLUMN_HABIT_ACTIVITY + " TEXT NOT NULL, "
            + HabitEntry.COLUMN_HABIT_TIME + " INTEGER NOT NULL DEFAULT 0)";
    /* Constante para deletar esta tabela */
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;

    /* Construtor da Database */
    public HabitDbHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Na criação da db, chama o método responsavel por passar uma entrada com informações válidas */
    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /* Quando a tabela é atualizada, será excluida para se criar uma nova */
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public Cursor cursor(){

        /* Obtém a database */
        SQLiteDatabase db = this.getReadableDatabase();


        /* Cria um cursor para recuperar dados da database*/
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        return cursor;
    }
}
