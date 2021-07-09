package com.example.localdatabaseroom.Room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NoteDAO {

    @Insert
    Completable insertNote (NoteEntity note);

    @Query("Select * from NoteTable")
    Single <List<NoteEntity>> getnote ();


    @Delete
    Completable deletenote(NoteEntity note);

    //delete all query
    @Delete
    Completable del(List<NoteEntity> noteEntities);

    //update
    @Query("UPDATE NoteTable SET name = :sname , content = :scontent WHERE id =:sid ")
    Completable update (String sname , String scontent , int sid );

    @Query("Delete from NoteTable")
    Completable deleteallnote ();
}
