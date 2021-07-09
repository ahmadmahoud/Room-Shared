package com.example.localdatabaseroom.Room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class RoomConverter {

    @TypeConverter
    public String setNoteDetails(NoteDetails noteDetails) {
        return new Gson().toJson(noteDetails);
    }

    @TypeConverter
    public NoteDetails getNoteDetails(String gson) {
        return new Gson().fromJson(gson, NoteDetails.class);
    }

}
