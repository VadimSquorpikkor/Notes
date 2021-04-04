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
    //Объект нашего слушателя
    private OnNoteClickListener onNoteClickListener;

    //В RecyclerView, в отличии от ListView, нет метода setOnItemClickListener, но это пофиг, его
    // можно сделать!!! Для этого создаем интерфейс, он будет слушать щелчки на элементы
    interface OnNoteClickListener {
        void onNoteClick(int position);
        void onLongClick(int position);
    }

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
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
        holder.textViewDayOfWeek.setText(Note.getDayAsString(note.getDayOfWeek()));
        int colorId;
        int priority = note.getPriority();
        switch (priority) {
            case 1: colorId = holder.itemView.getResources().getColor(android.R.color.holo_red_light);break;
            case 2: colorId = holder.itemView.getResources().getColor(android.R.color.holo_orange_light);break;
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

            //для работы OnNoteClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onNoteClickListener != null) {
                        onNoteClickListener.onNoteClick(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onNoteClickListener != null) {
                        onNoteClickListener.onNoteClick(getAdapterPosition());
                    }
                    //не забыть поменять на true, иначе после onLongClick сработает onClick
                    return true;
                }
            });

        }
    }
}
