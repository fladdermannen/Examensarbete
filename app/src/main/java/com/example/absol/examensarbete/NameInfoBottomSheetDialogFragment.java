package com.example.absol.examensarbete;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NameInfoBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private static final String TAG = "NameInfoBottomSheetDial";
    DatabaseHelper mDatabaseHelper;
    TextView namn, namnsdag, infotext;

    View rootView;

    static String mName;
    static boolean isFemale;
    static String mNameday;
    static String mInfo;
    static boolean noGender = false;
    static boolean showFab = false;
    static String currentTable;

    FloatingActionButton mFab;



    public static NameInfoBottomSheetDialogFragment newInstance(String name, boolean female, String nameday, String info, boolean fab, String table) {
        mName = name;
        isFemale = female;
        mNameday = nameday;
        mInfo = info;
        showFab = fab;
        currentTable = table;

        return new NameInfoBottomSheetDialogFragment();
    }

    public static NameInfoBottomSheetDialogFragment newInstance(String name, String nameday, String info) {
        mName = name;
        mNameday = nameday;
        mInfo = info;
        noGender = true;

        return new NameInfoBottomSheetDialogFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDatabaseHelper = new DatabaseHelper(getActivity(), "table_1");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(!showFab) {
            rootView = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        } else {
            rootView = inflater.inflate(R.layout.bottom_sheet_layout_populars, container, false);
            mFab = rootView.findViewById(R.id.fabAddName);
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addData(mName, "*");
                }
            });
        }

        Log.d(TAG, "onCreateView: ");
        namn = rootView.findViewById(R.id.tv_namn);
        namnsdag = rootView.findViewById(R.id.tv_namnsdag);
        infotext = rootView.findViewById(R.id.tv_info);

        setupStuff();
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        noGender = false;
        showFab = false;
    }

    public void setupStuff() {
        namn.setText(mName);
        namnsdag.setText(mNameday);
        //infotext.setText(mInfo);
        if(isFemale)
            namn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_female,0,0 ,0 );

        if(noGender)
            namn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite, 0, 0, 0);
    }

    public boolean addData(String newEntryName, String newEntryGender) {
        mDatabaseHelper.changeTable(currentTable);
        Cursor data = mDatabaseHelper.getData();
        int index = data.getCount() + 1;
        boolean insertData = mDatabaseHelper.addData(newEntryName, newEntryGender, index);

        if (insertData) {
            Log.d(TAG, "addData: data successfully inserted");
            Snackbar snackbar = Snackbar.make(rootView, newEntryName + " sparat.", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else {
            Log.d(TAG, "addData: Something went wrong");
            Snackbar snackbar = Snackbar.make(rootView, newEntryName + " finns redan sparat.", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        return insertData;
    }


}
