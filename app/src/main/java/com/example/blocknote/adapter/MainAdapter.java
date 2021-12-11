package com.example.blocknote.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blocknote.EditActivity;
import com.example.blocknote.R;
import com.example.blocknote.db.DbConstants;
import com.example.blocknote.db.DbManager;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private final Context context;
    private final List<Note> mainTextList;

    public MainAdapter(Context context) {
        this.context = context;
        mainTextList = new ArrayList<>();
    }

    //отрисовывает разметку
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false);
        return new MyViewHolder(view, context, mainTextList);
    }

    //для каждой разметки берет элемент списка
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(mainTextList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mainTextList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter(List<Note> newList) {
        mainTextList.clear();
        mainTextList.addAll(newList);
        notifyDataSetChanged();
    }

    public void removeItem(int position, DbManager manager) {
        manager.deleteValues(mainTextList.get(position).getId());
        mainTextList.remove(position);
        notifyItemChanged(0, mainTextList.size());
        notifyItemRemoved(position);

    }
}
