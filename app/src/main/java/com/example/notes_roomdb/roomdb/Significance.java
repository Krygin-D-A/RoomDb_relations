package com.example.notes_roomdb.roomdb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.notes_roomdb.Significances;

//Krygin D.A., Room Db with relations (One To One)
@Entity(tableName = "significances")
public class Significance {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long significanceNoteId;
    private Significances name;

    public Significance(Significances name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSignificanceNoteId() {
        return significanceNoteId;
    }

    public void setSignificanceNoteId(long significanceNoteId) {
        this.significanceNoteId = significanceNoteId;
    }

    public Significances getName() {
        return name;
    }

    public void setName(Significances name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Significance{" +
                "id=" + id +
                ", significanceNoteId=" + significanceNoteId +
                ", name=" + name +
                '}';
    }
}

