package com.squorpikkor.app.notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import static com.squorpikkor.app.notes.NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK;
import static com.squorpikkor.app.notes.NotesContract.NotesEntry.COLUMN_DESCRIPTION;
import static com.squorpikkor.app.notes.NotesContract.NotesEntry.COLUMN_PRIORITY;
import static com.squorpikkor.app.notes.NotesContract.NotesEntry.COLUMN_TITLE;
import static com.squorpikkor.app.notes.NotesContract.NotesEntry.TABLE_NAME;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerDayOfWeek;
    private RadioGroup radioGroupPriority;
    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDayOfWeek = findViewById(R.id.spinnerDayOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        dbHelper = new NotesDBHelper(this);
        database = dbHelper.getWritableDatabase();
        findViewById(R.id.buttonSaveNote).setOnClickListener(view -> saveNote());
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
//        String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
        int dayOfWeek = spinnerDayOfWeek.getSelectedItemPosition();
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        Log.e("TAG", "saveNote: "+radioButtonId);
        RadioButton radioButton = findViewById(radioButtonId);
        Log.e("TAG", "radioButton.getText() - "+radioButton.getText());
        int priority = Integer.parseInt(radioButton.getText().toString());
        insertData(title, description, dayOfWeek, priority);
    }

    void insertData(String title, String description, int dayOfWeek, int priority) {
        if (!title.isEmpty() && !description.isEmpty()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_TITLE, title);
            contentValues.put(COLUMN_DESCRIPTION, description);
            contentValues.put(COLUMN_DAY_OF_WEEK, dayOfWeek);
            contentValues.put(COLUMN_PRIORITY, priority);
            database.insert(TABLE_NAME, null, contentValues);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.warning_fill_fields, Toast.LENGTH_SHORT).show();
        }
    }

}