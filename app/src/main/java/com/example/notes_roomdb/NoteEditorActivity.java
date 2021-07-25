package com.example.notes_roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.notes_roomdb.roomdb.Note;
import com.example.notes_roomdb.roomdb.NoteWithSignificance;
import com.example.notes_roomdb.roomdb.Significance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class NoteEditorActivity extends AppCompatActivity implements View.OnClickListener {

    //Krygin D.A., Room Db with relations (One To One)

    private Spinner spSignificance;
    private TextView txtNoteSubject;
    private TextView txtNoteContent;
    private TextView txtNoteDate;
    private Button btnSaveNote;
    private Button btnDeleteNote;
    private Button btnCloseEditor;

    private long noteToEditId = 0;
    private int noteSignificanceIndex;
    private Significance noteSignificance;
    private String noteSubject;
    private String noteContent;
    private String noteDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        initViews();
        Bundle intentData = getIntent().getExtras();

        if (intentData != null) {
            noteToEditId = intentData.getLong("id", 0);
            noteSubject = intentData.getString("subject", "");
            noteContent = intentData.getString("content", "");
            noteDate = intentData.getString("date", "");
            String significanceStr = intentData.getString("significanceName", "");
            noteSignificanceIndex = Significances.valueOf(significanceStr).ordinal();
            readNoteToEdit();
        }
    }

    private void initViews() {
        spSignificance = findViewById(R.id.spSignificance);
        txtNoteSubject = findViewById(R.id.txtNoteSubject);
        txtNoteContent = findViewById(R.id.txtNoteContent);
        txtNoteDate = findViewById(R.id.txtDate);
        btnSaveNote = findViewById(R.id.btnSaveNote);
        btnDeleteNote = findViewById(R.id.btnDeleteNote);
        btnCloseEditor = findViewById(R.id.btnCloseEditor);

        btnDeleteNote.setOnClickListener(this);
        btnCloseEditor.setOnClickListener(this);
        btnSaveNote.setOnClickListener(this);

        Significances[] sign = Significances.values();
        String[] significances = new String[sign.length];

        for (int i = 0; i < sign.length; i++) {
            significances[i] = sign[i].toString();
        }

        ArrayAdapter<String> significanceAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, significances);
        spSignificance.setAdapter(significanceAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnDeleteNote: {

                deleteNote();
                break;
            }
            case R.id.btnSaveNote: {

                saveNote();
                break;
            }
            case R.id.btnCloseEditor: {

                finish();
                break;
            }
        }
    }

    private void readNoteToEdit() {
        spSignificance.setSelection(noteSignificanceIndex);
        txtNoteSubject.setText(noteSubject);
        txtNoteContent.setText(noteContent);
        txtNoteDate.setText(noteDate);
    }

    private void getNoteData() {
        noteSignificanceIndex = spSignificance.getSelectedItemPosition();
        noteSubject = txtNoteSubject.getText().toString();
        noteContent = txtNoteContent.getText().toString();
        noteSignificance = new Significance(Significances.values()[noteSignificanceIndex]);
    }

    private void saveNote() {
        getNoteData();

        if (noteToEditId == 0) {
            Date date = new Date();
            String currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(date);
            MainActivity.noteDao.insertNoteWithSignificance(
                    new Note(0L, noteSubject, noteContent, currentDateTime, noteSignificance),
                    noteSignificance
            );
        } else {
            MainActivity.noteDao.updateNoteWithSignificance(
                    new Note(noteToEditId, noteSubject, noteContent, noteDate, noteSignificance),
                    noteSignificance
            );
        }

        finish();
    }

    private void deleteNote() {
        MainActivity.noteDao.deleteNoteWithSignificance(
                new Note(noteToEditId, noteSubject, noteContent, noteDate, noteSignificance)
        );

        finish();
    }
}