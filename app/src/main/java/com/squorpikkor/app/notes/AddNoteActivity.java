package com.squorpikkor.app.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import static com.squorpikkor.app.notes.MainActivity.notes;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private Spinner spinnerDayOfWeek;
    private RadioGroup radioGroupPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        spinnerDayOfWeek = findViewById(R.id.spinnerDayOfWeek);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);

        findViewById(R.id.buttonSaveNote).setOnClickListener(view -> saveNote());
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String dayOfWeek = spinnerDayOfWeek.getSelectedItem().toString();
        int radioButtonId = radioGroupPriority.getCheckedRadioButtonId();
        Log.e("TAG", "saveNote: "+radioButtonId);
        RadioButton radioButton = findViewById(radioButtonId);
        Log.e("TAG", "radioButton.getText() - "+radioButton.getText());
        int priority = Integer.parseInt(radioButton.getText().toString());
        Note note = new Note(title, description, dayOfWeek, priority);
        notes.add(note);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}