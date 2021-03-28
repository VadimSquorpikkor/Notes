package com.squorpikkor.app.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import static com.squorpikkor.app.notes.Note.MONDAY;
import static com.squorpikkor.app.notes.Note.ОЧЕНЬ_СРОЧНО;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    //public static final это конечно вообще не правильно, сделано пока, как затычка
    public static final ArrayList<Note> notes = new ArrayList<>();

    private NotesDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);

        dbHelper = new NotesDBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        if (notes.isEmpty()) {
            notes.add(new Note("Парикмахерская", "Подстричся", MONDAY, ОЧЕНЬ_СРОЧНО));
            notes.add(new Note("Парикмахерская", "Подстричся", MONDAY, ОЧЕНЬ_СРОЧНО));
            notes.add(new Note("Парикмахерская", "Подстричся", MONDAY, ОЧЕНЬ_СРОЧНО));
        }

        for (Note note : notes) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, note.getTitle());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, note.getDescription());
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, note.getDayOfWeek());
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, note.getPriority());
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);
        }
        ArrayList<Note> notesFromDB = new ArrayList<>();
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME, null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            String dayOfWeek = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
            Note note = new Note(title, description, dayOfWeek, priority);
            notesFromDB.add(note);
        }
        cursor.close();
//        NotesAdapter notesAdapter = new NotesAdapter(notes);
        NotesAdapter notesAdapter = new NotesAdapter(notesFromDB);
            //  Указать, как будут располагаться элеементы в RecycleView: по вертикали, горизонтали или сеткой
            //
            //  LinearLayoutManager arranges the items in a one-dimensional list
            //  GridLayoutManager arranges all items in a two-dimensional grid
            //  StaggeredGridLayoutManager is similar to GridLayoutManager, but it does not require that
            //items in a row have the same height (for vertical grids) or items in the same column have
            //the same width (for horizontal grids). The result is that the items in a row or column
            //can end up offset from each other
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
            //Это, если нужно сделать RecycleView по горизонтали (если поставить true, порядок элементов будет реверсивным (справа на лево)):
            //recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            //В несколько столюцов, в примере - 3. Это всё работает с тем же ArrayList, ничего не нужно переделывать
            //recyclerViewNotes.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewNotes.setAdapter(notesAdapter);

        findViewById(R.id.buttonAddNote).setOnClickListener(view -> addNote());
    }

    private void addNote() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
}