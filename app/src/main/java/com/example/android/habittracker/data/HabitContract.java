package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by phartmann on 27/02/2018.
 */
/* Classe contrato que armazena as informações base da tabela */
public class HabitContract {

    private HabitContract(){}

    public static class HabitEntry implements BaseColumns{

        /* Cria uma constante para o nome da tabela */
        public static final String TABLE_NAME = "tracker";

        /* Cria uma constantes com o nome das colunas da tabela */
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_ACTIVITY = "activity";
        public static final String COLUMN_HABIT_TIME = "time";

    }
}
