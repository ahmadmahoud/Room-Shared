package com.example.localdatabaseroom.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "NoteTable")
public class NoteEntity {

    @ColumnInfo (name = "name")
    private String name;

    @ColumnInfo (name = "content")
    private String content;

    @PrimaryKey (autoGenerate = true)
    private int id;

    public NoteEntity(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}


// ColumnInfo لو قررت تغير الاسم في اي وقت لا يكون مشكلة
// Entity الكيان او بمعنى اخر الاسم الذي يحمل الجدول
// PrimaryKey يستخدم للاشياء الفريدة من نوعها مثل الاي دي
