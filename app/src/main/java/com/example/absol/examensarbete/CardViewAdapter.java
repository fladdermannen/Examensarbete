package com.example.absol.examensarbete;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.ArrayList;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.DumbViewHolder> {

    private static final String TAG = "CardViewAdapter";

    private ArrayList<String> mLists;
    private CardViewAdapterListener listener;

    public int selectedPos = 0;

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

        CardView cardView;
        ImageButton imageButton;
        FrameLayout f1,f2,f3,f4;

        DumbViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_layout);
            imageButton = itemView.findViewById(R.id.imageButton);
            f1 = itemView.findViewById(R.id.fl1);
            f2 = itemView.findViewById(R.id.fl2);
            f3 = itemView.findViewById(R.id.fl3);
            f4 = itemView.findViewById(R.id.fl4);
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
            int notSelected = Color.parseColor("#ffffff");
            int selected = Color.parseColor("#d50000");

            holder.f1.setBackgroundColor(selectedPos == position ? selected : notSelected);
            holder.f2.setBackgroundColor(selectedPos == position ? selected : notSelected);
            holder.f3.setBackgroundColor(selectedPos == position ? selected : notSelected);
            holder.f4.setBackgroundColor(selectedPos == position ? selected : notSelected);

            final int pos = position;
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCardSelected(pos);
                }
            });
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
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

    public void setSelectedPos(int pos) {
        selectedPos = pos;
    }

    public int getSelectedPos() {
        return selectedPos;
    }

}
