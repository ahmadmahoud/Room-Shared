package com.example.localdatabaseroom.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = NoteEntity.class, version = 2 , exportSchema = false)
@TypeConverters(RoomConverter.class)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;
    public abstract NoteDAO noteDAO();

    public static synchronized NoteDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "NoteDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
