package com.example.notes_roomdb.roomdb;

import androidx.room.Embedded;
import androidx.room.Relation;

//Krygin D.A., Room Db with relations (One To One)
public class NoteWithSignificance {
    @Embedded
    private Note note;
    @Relation(
            parentColumn = "id",
            entityColumn = "significanceNoteId",
            entity = Significance.class
    )
    private Significance significance;

    public NoteWithSignificance(Note note, Significance significance) {
        this.note = note;
        this.significance = significance;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Significance getSignificance() {
        return significance;
    }

    public void setSignificance(Significance significance) {
        this.significance = significance;
    }

    @Override
    public String toString() {
        return "NoteWithSignificance{" +
                "note=" + note +
                ", significance=" + significance +
                '}';
    }
}
