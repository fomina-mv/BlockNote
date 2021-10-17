package com.example.blocknote.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blocknote.R;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private final Context context;
    private final List<String> mainTextList;

    public MainAdapter(Context context) {
        this.context = context;
        mainTextList = new ArrayList<>();
    }

    //отрисовывает разметку
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false);
        return new ViewHolder(view);
    }

    //для каждой разметки берет элемент списка
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(mainTextList.get(position));
    }

    @Override
    public int getItemCount() {
        return mainTextList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.textTitle);
        }

        public void setData(String title) {
            titleTv.setText(title);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateAdapter(List<String> newList) {
        mainTextList.clear();
        mainTextList.addAll(newList);
        notifyDataSetChanged();
    }
}
