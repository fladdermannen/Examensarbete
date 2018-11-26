package com.example.absol.examensarbete;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentFirst extends Fragment implements MyNamesListAdapter.MyNamesAdapterListener {

    private static final String TAG = "FragmentFirst";

    DatabaseHelper mDatabaseHelper;

    private ArrayList<String> mArrayList = new ArrayList<>();
    private MyNamesListAdapter mAdapter;
    RecyclerView mRecyclerView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDatabaseHelper = new DatabaseHelper(context);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        mRecyclerView = rootView.findViewById(R.id.myNamesRecyclerView);

        mAdapter = new MyNamesListAdapter(mArrayList, this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        /*
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntryName = editText.getText().toString();
                if(editText.length() != 0){
                    if(addData(newEntryName, "boy")) {
                        editText.setText("");
                        mArrayList.add(newEntryName);
                        mAdapter.notifyDataSetChanged();
                    }
                } else
                    toastMessage("You must put something in the text field");
            }
        });
        */

        //Handling swipe
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.onItemRemove(viewHolder);
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);


        populateView();
        return rootView;
    }



    public boolean addData(String newEntryName, String newEntryGender) {
        Cursor data = mDatabaseHelper.getData();
        int index = data.getCount() + 1;
        boolean insertData = mDatabaseHelper.addData(newEntryName, newEntryGender, index);

        if (insertData) {
            toastMessage("Data successfully inserted!");
        }
        else {
            toastMessage("Something went wrong");
        }
        return insertData;
    }



    private void toastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }



    private void populateView(){
        mArrayList.clear();
        Cursor data = mDatabaseHelper.getData();
        while(data.moveToNext()) {
            mArrayList.add(data.getString(2));
        }

        Log.d(TAG, "populateView: " + mArrayList.toString());
        mRecyclerView.setAdapter(mAdapter);
    }



    @Override
    public void onNameSelected(String name){
        Cursor data = mDatabaseHelper.getItemID(name); //The ID associated with that name
        int itemID = -1;
        while(data.moveToNext()) {
            itemID = data.getInt(0);
        }
        if(itemID > -1){
            Log.d(TAG, "onNameSelected: The ID of name " + name + " is " + itemID);
        } else {
            toastMessage("No ID associated with that name");
        }

        data = mDatabaseHelper.getItemIndex(name);
        itemID = -1;
        while(data.moveToNext()) {
            itemID = data.getInt(0);
        }
        if(itemID > -1){
            Log.d(TAG, "onNameSelected: The Index of name " + name + " is " + itemID);
        } else
            toastMessage("No index associated with that name");
    }

    @Override
    public void onNameDeleted(String name) {
        Cursor data = mDatabaseHelper.getItemID(name);
        int itemID = -1;
        while(data.moveToNext()) {
            itemID = data.getInt(0);
        }
        if(itemID > -1){
            Cursor indexData = mDatabaseHelper.getItemIndex(name);
            int itemIndex = -1;
            while(indexData.moveToNext()){
                itemIndex = indexData.getInt(0);
            }
            if(itemIndex > -1) {
                mDatabaseHelper.deleteName(itemID, name, itemIndex);
                Log.d(TAG, "onNameDeleted: Deleted " + name + " from database.");
            } else {
                toastMessage("Could not find item index");
            }
        } else {
            toastMessage("Something went wrong when deleting name");
        }
    }

    @Override
    public void onMove(int index, int direction) {
        String thisName = mArrayList.get(index);
        String otherName = "";
        int thisNameIndex = -1;
        int otherNameIndex = -1;

        //Moving UP
        if(direction<0) {
            if (index == 0) {
                return;
            }
            otherName = mArrayList.get(index - 1);
            String item = mArrayList.remove(index);
            mArrayList.add(index - 1, item);

        //Moving DOWN
        }else if(direction>0) {
            if(index == mArrayList.size()-1) {
                return;
            }
            otherName = mArrayList.get(index + 1);
            String item = mArrayList.remove(index);
            mArrayList.add(index + 1, item);
        }

        Cursor data = mDatabaseHelper.getItemIndex(thisName);
        while (data.moveToNext()) {
            thisNameIndex = data.getInt(0);
        }
        data = mDatabaseHelper.getItemIndex(otherName);
        while (data.moveToNext()) {
            otherNameIndex = data.getInt(0);
        }
        mDatabaseHelper.moveItems(otherNameIndex, thisNameIndex);
        mAdapter.notifyItemMoved(index, index+direction);
    }

}
