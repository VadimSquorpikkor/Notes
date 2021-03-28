package com.squorpikkor.app.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

class NotesDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "notes.db";
    public static final int DB_VERSION = 1;

    public NotesDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotesContract.NotesEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(NotesContract.NotesEntry.DROP_COMMAND);
        onCreate(db);
    }
}
