package com.example.absol.examensarbete;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.DumbViewHolder> {

    private static final String TAG = "CardViewAdapter";

    private ArrayList<String> mLists;
    private CardViewAdapterListener listener;

    public interface CardViewAdapterListener {
        void onCardSelected(int position);
        void onNewListSelected();
        void onCardLongClick(int position);
    }


    CardViewAdapter(ArrayList<String> lists, CardViewAdapterListener listener)
    {
        this.mLists = lists;
        this.listener = listener;
    }

    class DumbViewHolder extends RecyclerView.ViewHolder {

        Button button;
        ImageButton imageButton;

        DumbViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
            imageButton = itemView.findViewById(R.id.imageButton);
        }
    }

    @NonNull
    @Override
    public CardViewAdapter.DumbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if(viewType == R.layout.cardview_layout) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_button, parent, false);
        }
        return new CardViewAdapter.DumbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewAdapter.DumbViewHolder holder, int position) {
        if(position == mLists.size()) {
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNewListSelected();
                }
            });
        } else {
            final int pos = position;
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCardSelected(pos);
                }
            });
            holder.button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onCardLongClick(pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mLists.size()) ? R.layout.cardview_button : R.layout.cardview_layout;
    }


    @Override
    public int getItemCount() {
        return mLists.size() +1;
    }

}
