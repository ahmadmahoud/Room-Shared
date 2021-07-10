package com.example.localdatabaseroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.localdatabaseroom.Room.NoteDatabase;
import com.example.localdatabaseroom.Room.NoteEntity;
import com.example.localdatabaseroom.Room.RecyclerAdapter;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView postsRecyclerView;
    private List<NoteEntity> entities = new ArrayList<>();
    private NoteDatabase database;
    private Button insertBtn, getBtn, deldata;
    private EditText noteTitle, bodyEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        room_with_rx();

    }

    private void room_with_rx() {

        // لعمل الروم يجب ان تتوفر المتطلبات التالية
        // Entity ( Table class ).
        // DAO Data access ( interface ).
        // Database ( Abstract Class ).

        insertBtn = findViewById(R.id.insertButton);
        getBtn = findViewById(R.id.getButton);
        deldata = findViewById(R.id.delButton);
        noteTitle = findViewById(R.id.editTexttitle);
        bodyEt = findViewById(R.id.editTextBody);

        postsRecyclerView = findViewById(R.id.posts_recyclerView);
        RecyclerAdapter adapter = new RecyclerAdapter(entities, MainActivity.this);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postsRecyclerView.setAdapter(adapter);

        database = NoteDatabase.getInstance(this);
//        database.noteDAO().getnote()
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleObserver<List<NoteEntity>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(@NonNull List<NoteEntity> noteEntities) {
//                        adapter.setPostsList(noteEntities);
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//                });


//new NoteEntity(noteTitle.getEditableText().toString(),bodyEt.getEditableText().toString())

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.noteDAO().insertNote(new NoteEntity(Objects.requireNonNull(noteTitle.getEditableText()).toString(), bodyEt.getEditableText().toString()))
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
            }
        });


        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getdata(adapter);
            }
        });


        deldata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.noteDAO().deleteallnote()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                adapter.delete(entities);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
            }
        });


    }

    private void getdata(RecyclerAdapter adapter) {
        database.noteDAO().getnote()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<NoteEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<NoteEntity> posts) {
                        adapter.setPostsList(posts);
                        adapter.notifyDataSetChanged();
                        Log.i(TAG, "onSuccess: " + posts);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}







    /*
    // Shared Database
    private void shareduserdata() {

        SharedUser sharedUser = new SharedUser("gee","coders",".com");
        SharedUserData sharedUserData = new SharedUserData(MainActivity.this);
        sharedUserData.setUser(sharedUser);
        Log.i(TAG, "shareduserdata: "+sharedUserData.getsharedUser().getName());
        Log.i(TAG, "shareduserdata: "+sharedUserData.getsharedUser().getLasname());
        Log.i(TAG, "shareduserdata: "+sharedUserData.getsharedUser().getName());

        // مثال توضيحي لفكرة gson
//        Gson gson = new Gson();
//        String usergson = gson.toJson(sharedUser);
//        Log.i(TAG, "shareduserdata: "+usergson);
//
//        SharedUser newUser = gson.fromJson(usergson,SharedUser.class);
//        Log.i(TAG, "shareduserdata: "+newUser.getName());
//        Log.i(TAG, "shareduserdata: "+newUser.getLasname());
//        Log.i(TAG, "shareduserdata: "+newUser.getPhone());

    }

    // Shared Database 2
    private void sharedPreferences() {
        String name;
        SharedPreferences preferences = getSharedPreferences("name", MODE_PRIVATE);
        name = preferences.getString("name", "no name");
        Log.i(TAG, "onCreate: the name is "+name);

        preferences.edit().putString("name","Geecoders").apply();

        name = preferences.getString("name", "geecoders");
        Log.i(TAG, "onCreate: the name is "+name);

    }

    // Shared Database 3
    private void dataShared() {
        DataShared dataShared = new DataShared(MainActivity.this);
        dataShared.setData("ahmed");
        dataShared.setlastname("mahmoud");
        Log.i(TAG, "onCreate: " + dataShared.getData());
        Log.i(TAG, "onCreate: " + dataShared.getlastname());
    }
*/
