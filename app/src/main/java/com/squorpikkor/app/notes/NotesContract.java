package com.squorpikkor.app.notes;

import android.provider.BaseColumns;

class NotesContract {

//    CREATE TABLE IF NOT EXISTS notes (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, day_of_week TEXT, priority INTEGER)

//    SELECT * FROM notes WHERE day_of_week == 'Понедельник'

//    INSERT INTO notes (title, description...) VALUES ('заголовок', 'описание'...)

//    DELETE FROM notes WHERE priority>1

    //Для каждой таблице создается отдельный внутренний класс
    public static final class NotesEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DAY_OF_WEEK = "day_of_week";
        public static final String COLUMN_PRIORITY = "priority";
        //Так как реализован интерфейс BaseColumns, то создавать колонку "id" не нужно, она будет создана автоматом

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";


        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS" + TABLE_NAME + "(" +
                _ID + " " + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " " + TYPE_TEXT + ", " +
                COLUMN_DESCRIPTION + " " + TYPE_TEXT + ", " +
                COLUMN_DAY_OF_WEEK + " " + TYPE_TEXT + ", " +
                COLUMN_PRIORITY + " " + TYPE_INTEGER + ")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
