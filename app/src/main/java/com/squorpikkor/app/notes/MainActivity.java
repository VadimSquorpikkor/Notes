package com.squorpikkor.app.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

import static com.squorpikkor.app.notes.NotesContract.NotesEntry.COLUMN_PRIORITY;
import static com.squorpikkor.app.notes.NotesContract.NotesEntry.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    //public static final это конечно вообще не правильно, сделано пока, как затычка
    private final ArrayList<Note> notes = new ArrayList<>();
    private NotesAdapter adapter;
    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();
        //database.delete(NotesContract.NotesEntry.TABLE_NAME, null, null);
        getData();
        adapter = new NotesAdapter(notes);
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
        recyclerViewNotes.setAdapter(adapter);
        //Добавляем созданный ручками лисенер (см. NotesAdapter), которого изначально у RecyclerView нет
        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                //что-то делаем
            }

            @Override
            public void onLongClick(int position) {
                //что-то ещё делаем
            }
        });

        //Чтобы пользоваться свайпом, добавляем
        //2-й параметр -- это направление
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //что-то делаем по свайпу вправо или влево
                removeItem(viewHolder.getAdapterPosition());
            }
        });

        //присваиваем хелпер к ресайклеру
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);

        findViewById(R.id.buttonAddNote).setOnClickListener(view -> addNote());
    }

    private void removeItem (int position) {
        int id = notes.get(position).getId();
        String where = NotesContract.NotesEntry._ID + " = ?"; //где id=?, вместо ? подставляем массив строк:
        String[] whereArgs = new String[]{Integer.toString(id)};
        database.delete(TABLE_NAME, where, whereArgs);
        getData();
        //После удаления/добавления элементов обязательно вызывать метод notifyDataSetChanged
        // у адаптера, иначе приложение упадет
        adapter.notifyDataSetChanged();

        //не нужно, массив должен зависить от БД
        //notes.remove(position);
        //После удаления/добавления элементов обязательно вызывать метод notifyDataSetChanged
        // у адаптера, иначе приложение упадет
        //adapter.notifyDataSetChanged();
    }

    private void addNote() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void getData() {
        notes.clear();

        //Если нужно отсортировать по какой-то из колонок
        String order = COLUMN_PRIORITY;

        //если нужно вывести только заметки с приоритетом < 2,
        String selection = COLUMN_PRIORITY + " < ?"; // если сравнить со значением, то " == ?"
        String[] selectionArgs = new String[]{"2"};
        //оба стринга вставить в курсор в соответствующие параметры

        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, order, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            int dayOfWeek = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
            Note note = new Note(id, title, description, dayOfWeek, priority);
            notes.add(note);
        }
        cursor.close();
    }
}