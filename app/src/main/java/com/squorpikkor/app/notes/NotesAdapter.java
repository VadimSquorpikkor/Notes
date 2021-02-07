package com.squorpikkor.app.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{// В <> только что созданный ViewHolder

    private ArrayList<Note> notes;

    //Конструктор, в котором передаем ArrayList для RecyclerView
    public NotesAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    //Присваиваем xml лэйаут к итему RecyclerView
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    //Принимает объект ViewHolder (holder) и порядковый номер элемента массива (position)
    //т.е. у 1-ого элемента View будет порядковый номер 0, он возмёт элемент с этим индексом (заметку)
    //и у ViewHolder'а установить все значения (присвоить значения к TextView)
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewDescription.setText(note.getDescription());
        holder.textViewDayOfWeek.setText(note.getDayOfWeek());
        int colorId;
        int priority = note.getPriority();
        switch (priority) {
            case 0: colorId = holder.itemView.getResources().getColor(android.R.color.holo_red_light);break;
            case 1: colorId = holder.itemView.getResources().getColor(android.R.color.holo_orange_light);break;
            //Если не поставить default, а 2, то дальше в коде будет ошибка, так как будет возможен вариант, когда переменная не проинициализирована
            default: colorId = holder.itemView.getResources().getColor(android.R.color.holo_green_light);break;
        }
        holder.textViewTitle.setBackgroundColor(colorId);

    }

    //Просто возвращает кол-во элементов в массиве
    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDayOfWeek;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
        }
    }
}
