package com.example.absol.examensarbete;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class NameInfoBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private static final String TAG = "NameInfoBottomSheetDial";
    TextView namn, namnsdag, infotext;

    static String mName;
    static boolean isFemale;
    static String mNameday;
    static String mInfo;
    static boolean noGender = false;

    public static NameInfoBottomSheetDialogFragment newInstance(String name, boolean female, String nameday, String info) {
        mName = name;
        isFemale = female;
        mNameday = nameday;
        mInfo = info;

        return new NameInfoBottomSheetDialogFragment();
    }

    public static NameInfoBottomSheetDialogFragment newInstance(String name, String nameday, String info) {
        mName = name;
        mNameday = nameday;
        mInfo = info;
        noGender = true;

        return new NameInfoBottomSheetDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        Log.d(TAG, "onCreateView: ");
        namn = view.findViewById(R.id.tv_namn);
        namnsdag = view.findViewById(R.id.tv_namnsdag);
        infotext = view.findViewById(R.id.tv_info);

        setupStuff();
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        noGender = false;
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


}
