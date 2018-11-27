package com.example.absol.examensarbete;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MyNamesListAdapter extends RecyclerView.Adapter<MyNamesListAdapter.MyViewHolder> {

    private static final String TAG = "MyNamesListAdapter";
    private ArrayList<String> nameList;
    private ArrayList<String> namesToDelete = new ArrayList<>();
    private MyNamesAdapterListener listener;

    public interface MyNamesAdapterListener {
        void onNameSelected(String name);
        void onMove(int index, int direction);
        void addNameToDelete(String name);
        void removeNameFromDelete(String name);
    }

    MyNamesListAdapter(ArrayList<String> arrayList, MyNamesAdapterListener listener){
        this.nameList = arrayList;
        this.listener = listener;
        }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageButton upBtn, downBtn;

        MyViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            upBtn = itemView.findViewById(R.id.upButton);
            downBtn = itemView.findViewById(R.id.downButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNameSelected(nameList.get(getAdapterPosition()));
                }
            });
            upBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMove(getAdapterPosition(), -1);
                }
            });
            downBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMove(getAdapterPosition(), 1);
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


    public void onItemRemove(RecyclerView.ViewHolder viewHolder, final RecyclerView recyclerView) {
        final int adapterPosition = viewHolder.getAdapterPosition();
        final String name = nameList.get(adapterPosition);

        Snackbar snackbar = Snackbar.make(recyclerView, "NAMN BORTTAGET", Snackbar.LENGTH_SHORT)
                .setAction("ÅNGRA", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: " + name);

                        nameList.add(adapterPosition, name);
                        notifyItemInserted(adapterPosition);
                        recyclerView.scrollToPosition(adapterPosition);
                        namesToDelete.remove(name);
                        listener.removeNameFromDelete(name);
                    }
                });

        snackbar.show();
        nameList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        namesToDelete.add(name);

        listener.addNameToDelete(name);
    }


}