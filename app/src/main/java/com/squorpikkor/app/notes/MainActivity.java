package com.squorpikkor.app.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import static com.squorpikkor.app.notes.Note.MONDAY;
import static com.squorpikkor.app.notes.Note.ОЧЕНЬ_СРОЧНО;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNotes;
    private ArrayList<Note> notes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        notes.add(new Note("Парикмахерская", "Подстричся", MONDAY, ОЧЕНЬ_СРОЧНО));
        notes.add(new Note("Парикмахерская", "Подстричся", MONDAY, ОЧЕНЬ_СРОЧНО));
        notes.add(new Note("Парикмахерская", "Подстричся", MONDAY, ОЧЕНЬ_СРОЧНО));

        NotesAdapter notesAdapter = new NotesAdapter(notes);
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
    }
}