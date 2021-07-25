package com.example.notes_roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.notes_roomdb.roomdb.App;
import com.example.notes_roomdb.roomdb.AppDatabase;
import com.example.notes_roomdb.roomdb.Note;
import com.example.notes_roomdb.roomdb.NoteDao;
import com.example.notes_roomdb.roomdb.NoteWithSignificance;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //Krygin D.A., Room Db with relations (One To One)

    private Button btnAddNote;
    private GridView gvNotes;

    private AppDatabase database;
    public static NoteDao noteDao;

    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initListeners();
        initDb();
    }

    @Override
    protected void onResume() {
        super.onResume();

        readNotesFromDb();
    }

    private void initDb() {
        database = App.getInstance().getDatabase();
        noteDao = database.noteDao();
    }

    private void readNotesFromDb() {
        List<NoteWithSignificance> notesWithSignificance = noteDao.getAllNotesWithSignificance();

        notes = new ArrayList<>();
        for (NoteWithSignificance noteWithSignificance: notesWithSignificance) {
            Note note = noteWithSignificance.getNote();
            note.setSignificance(noteWithSignificance.getSignificance());
            notes.add(note);
        }

        ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(
                this,
                android.R.layout.simple_list_item_1,
                notes
        );

        gvNotes.setAdapter(adapter);
    }

    private void initViews() {
        btnAddNote = findViewById(R.id.btnAddNote);
        gvNotes = findViewById(R.id.gvNotes);
    }

    private void initListeners() {
        btnAddNote.setOnClickListener(this::addNote);
        gvNotes.setOnItemClickListener((parent, view, position, id) -> {
            openNoteEditor(position);
        });
    }

    private void addNote(View view) {
        openNoteEditor(-1);
    }

    private void openNoteEditor(int position) {
        Intent noteEditorIntent = new Intent(this, NoteEditorActivity.class);

        if (position != -1) {
            noteEditorIntent.putExtra("id", notes.get(position).getId());
            noteEditorIntent.putExtra("subject", notes.get(position).getSubject());
            noteEditorIntent.putExtra("content", notes.get(position).getContent());
            noteEditorIntent.putExtra("date", notes.get(position).getDate());
            noteEditorIntent.putExtra("significanceName", notes.get(position).getSignificance().getName().toString());
        }

        startActivity(noteEditorIntent);
    }
}