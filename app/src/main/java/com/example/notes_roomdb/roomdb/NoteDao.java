package com.example.notes_roomdb.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

//Krygin D.A., Room Db with relations (One To One)
@Dao
public abstract class NoteDao {

    //////////////// Note

    @Insert
    public abstract long insertNote(Note note);

    @Update
    public abstract void updateNote(Note note);

    @Query("SELECT * FROM notes WHERE id = :id")
    public abstract Note getNoteById(long id);

    @Query("SELECT * FROM notes")
    public abstract List<Note> getAllNotes();


    @Delete
    public abstract void deleteNote(Note note);

    //////////////// Significance

    @Insert
    public abstract long insertSignificance(Significance significance);

    @Update
    public abstract void updateSignificance(Significance significance);

    @Query("SELECT * FROM significances WHERE significanceNoteId = :noteId")
    public abstract Significance getSignificanceByNoteId(long noteId);

    @Query("SELECT * FROM significances")
    public abstract List<Significance> getAllSignificances();

    @Delete
    public abstract void deleteSignificance(Significance significance);

    //////////////// NoteWithSignificance

//    @Insert
//    public abstract void insertNoteWithSignificance(NoteWithSignificance noteWithSignificance);

    public void insertNoteWithSignificance(Note note, Significance significance) {
        long significanceId = insertSignificance(significance);
        significance.setId(significanceId);
        note.setSignificance(significance);
        long noteId = insertNote(note);
        significance.setSignificanceNoteId(noteId);
        updateSignificance(significance);
    }

    //    @Update
//    public abstract void updateNoteWithSignificance(NoteWithSignificance noteWithSignificance);

    public void updateNoteWithSignificance(Note note, Significance significance) {
        NoteWithSignificance noteWithSignificance = getNoteByIdWithSignificance(note.getId());
        Note noteToUpdate = noteWithSignificance.getNote();
        noteToUpdate.setSubject(note.getSubject());
        noteToUpdate.setContent(note.getContent());
        noteWithSignificance.setNote(noteToUpdate);
        Significance significanceToUpdate = noteWithSignificance.getSignificance();
        significanceToUpdate.setName(significance.getName());
        noteWithSignificance.setSignificance(significanceToUpdate);
        updateNote(noteWithSignificance.getNote());
        updateSignificance(noteWithSignificance.getSignificance());
    }

    @Transaction
    @Query("SELECT * FROM notes")
    public abstract List<NoteWithSignificance> getAllNotesWithSignificance();

    @Transaction
    @Query("SELECT * FROM notes WHERE notes.id = :id")
    public abstract NoteWithSignificance getNoteByIdWithSignificance(long id);

//    @Delete
//    public abstract void deleteNoteWithSignificance(NoteWithSignificance noteWithSignificance);

    public void deleteNoteWithSignificance(Note note) {
        NoteWithSignificance noteWithSignificance = getNoteByIdWithSignificance(note.getId());
        deleteNote(noteWithSignificance.getNote());
        deleteSignificance(noteWithSignificance.getSignificance());

    }
}
