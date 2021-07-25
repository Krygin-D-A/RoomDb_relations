package com.example.notes_roomdb.roomdb;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//Krygin D.A., Room Db with relations (One To One)
@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String subject;
    private String content;
    private String date;
    @Ignore
    private Significance significance;

    public Note(long id, String subject, String content, String date, Significance significance) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.date = date;
        this.significance = significance;
    }

    public Note(String subject, String content, String date) {
        this.subject = subject;
        this.content = content;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Significance getSignificance() {
        return significance;
    }

    public void setSignificance(Significance significance) {
        this.significance = significance;
    }

    @Override
    public String toString() {
        return "id=" + id + "\n" +
                "subject='" + subject + "\n" +
                "content='" + content + "\n" +
                "date='" + date + "\n" +
                "significance=" + significance + "\n";
    }
}

