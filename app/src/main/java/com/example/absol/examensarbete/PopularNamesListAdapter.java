package com.example.absol.examensarbete;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PopularNamesListAdapter extends RecyclerView.Adapter<PopularNamesListAdapter.MyViewHolder> {

    private ArrayList<PopularNamePOJO> nameList;

    public PopularNamesListAdapter(ArrayList<PopularNamePOJO> arrayList){
        this.nameList = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, amount, rank;

        public MyViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            amount = itemView.findViewById(R.id.textViewAmount);
            rank = itemView.findViewById(R.id.textViewRank);
        }
    }

    @NonNull
    @Override
    public PopularNamesListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_name_recycler_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularNamesListAdapter.MyViewHolder holder, int position) {
        PopularNamePOJO popName = nameList.get(position);
        holder.name.setText(popName.getName());
        holder.amount.setText("Antal: " + popName.getAmount());
        holder.rank.setText("" + popName.getRank());
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }
}

