package com.example.localdatabaseroom.Room;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localdatabaseroom.R;

import java.util.Collection;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PostsViewHolder> {

    private List<NoteEntity> postsList;
    private Activity context;
    private NoteDatabase noteDatabase;

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false));
    }

    public RecyclerAdapter(List<NoteEntity> postsList, Activity context) {
        this.postsList = postsList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        holder.titleTV.setText(postsList.get(position).getName());
        holder.bodyTV.setText(postsList.get(position).getContent());
        noteDatabase = NoteDatabase.getInstance(context);
        NoteEntity entity = postsList.get(position);

        // تعديل على عنصر في note
        holder.image_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NoteEntity entity = postsList.get(holder.getAdapterPosition());
                int sid = entity.getId();
                String sname = entity.getName();
                String scontent = entity.getContent();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int higth = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, higth);
                dialog.show();

                EditText name_edit = dialog.findViewById(R.id.editTexttitle_edite);
                EditText body_edit = dialog.findViewById(R.id.editTextBody_edite);
                Button button_edit = dialog.findViewById(R.id.getButton_edit);

                name_edit.setText(sname);
                body_edit.setText(scontent);

                button_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        noteDatabase.noteDAO().update(name_edit.getEditableText().toString(), body_edit.getEditableText().toString(), sid).subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                            }
                        });
                        postsList.clear();
                        notifyDataSetChanged();
                    }
                });
            }
        });

        // حذف عنصر من note
        holder.image_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteEntity entity = postsList.get(holder.getAdapterPosition());
                noteDatabase.noteDAO().deletenote(entity);
                int position = holder.getAdapterPosition();
                postsList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,postsList.size());

            }
        });

    }


    public void setPostsList(List<NoteEntity> postsList) {
        this.postsList = postsList;
        notifyDataSetChanged();
    }



    public class PostsViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTV, bodyTV;
        private ImageView image_delete, image_edit;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.item_title_textView);
            bodyTV = itemView.findViewById(R.id.item_body_textView);
            image_delete = itemView.findViewById(R.id.ic_delete);
            image_edit = itemView.findViewById(R.id.ic_edit);
        }
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }
}
