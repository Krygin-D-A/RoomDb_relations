package com.example.notes_roomdb.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//Krygin D.A., Room Db with relations (One To One)
@Database(entities = {Note.class, Significance.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
