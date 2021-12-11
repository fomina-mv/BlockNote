package com.example.blocknote.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blocknote.EditActivity;
import com.example.blocknote.R;

import java.util.List;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private final TextView titleTv;
    private final Context context;
    private final List<Note> mainTextList;

    public MyViewHolder(@NonNull View itemView, Context context, List<Note> mainTextList) {
        super(itemView);
        this.context = context;
        this.mainTextList = mainTextList;
        titleTv = itemView.findViewById(R.id.textTitle);
        itemView.setOnClickListener(this);
    }

    public void setData(String title) {
        titleTv.setText(title);
    }

    @Override
    public void onClick(View v) {
        Intent shownote = new Intent(context, EditActivity.class);
        shownote.putExtra(AdapterConstants.NOTE_INTENT, mainTextList.get(getAdapterPosition()));
        shownote.putExtra(AdapterConstants.EDIT_STATE, false);
        context.startActivity(shownote);
    }
}
