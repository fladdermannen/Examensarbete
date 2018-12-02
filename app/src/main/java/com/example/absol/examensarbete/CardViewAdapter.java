package com.example.absol.examensarbete;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.DumbViewHolder> {

    private static final String TAG = "CardViewAdapter";

    private ArrayList<String> mLists;

    CardViewAdapter(ArrayList<String> lists)
    {
        this.mLists = lists;
    }

    class DumbViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        DumbViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
        }
    }

    @NonNull
    @Override
    public CardViewAdapter.DumbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        return new CardViewAdapter.DumbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdapter.DumbViewHolder holder, int position) {
        String name = mLists.get(position);
        holder.textView.setText(name);
    }


    @Override
    public int getItemCount() {
        return mLists.size();
    }
}
