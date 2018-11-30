package com.example.absol.examensarbete;


import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MyNamesListAdapter extends RecyclerView.Adapter<MyNamesListAdapter.MyViewHolder> {

    private static final String TAG = "MyNamesListAdapter";
    private ArrayList<String> nameList;
    private MyNamesAdapterListener listener;

    public interface MyNamesAdapterListener {
        void onNameSelected(String name);
        void deleteName(String name);
    }

    MyNamesListAdapter(ArrayList<String> arrayList, MyNamesAdapterListener listener){
        this.nameList = arrayList;
        this.listener = listener;
        }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        MyViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNameSelected(nameList.get(getAdapterPosition()));
                }
            });
        }
    }

    @NonNull
    @Override
    public MyNamesListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_name_recycler_layout,parent,false);
        return new MyNamesListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNamesListAdapter.MyViewHolder holder, int position) {
        String mName = nameList.get(position);
        holder.name.setText(mName);

    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }


    void onItemRemove(RecyclerView.ViewHolder viewHolder, final RecyclerView recyclerView) {
        final int adapterPosition = viewHolder.getAdapterPosition();
        final String name = nameList.get(adapterPosition);

        Snackbar snackbar = Snackbar.make(recyclerView, name + " raderat.", Snackbar.LENGTH_SHORT);
        snackbar.show();

        nameList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        listener.deleteName(name);
    }


    boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(nameList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(nameList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }
}