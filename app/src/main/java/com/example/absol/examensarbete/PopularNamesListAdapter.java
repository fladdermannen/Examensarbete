package com.example.absol.examensarbete;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PopularNamesListAdapter extends RecyclerView.Adapter<PopularNamesListAdapter.MyViewHolder> implements Filterable {

    private static final String TAG = "PopularNamesListAdapter";

    private ArrayList<PopularNamePOJO> nameList;
    private ArrayList<PopularNamePOJO> nameListFull;
    private PopularNamesAdapterListener listener;

    public interface PopularNamesAdapterListener {
        void onNameSelected(PopularNamePOJO name);
    }


    public PopularNamesListAdapter(ArrayList<PopularNamePOJO> arrayList, PopularNamesAdapterListener listener){
        this.nameList = arrayList;
        this.listener = listener;
        this.nameListFull = arrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, amount, rank;

        public MyViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            amount = itemView.findViewById(R.id.textViewAmount);
            rank = itemView.findViewById(R.id.textViewRank);

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


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                Log.d(TAG, "performFiltering: " + charString);
                if (charString.isEmpty()) {
                    nameList = nameListFull;
                } else {
                    ArrayList<PopularNamePOJO> filteredList = new ArrayList<>();
                    for (PopularNamePOJO row : nameListFull) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    nameList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = nameList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                nameList = (ArrayList<PopularNamePOJO>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}

