package com.example.absol.examensarbete;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class FragmentSecond extends Fragment {

    private final static String TAG = "FragmentSecond";

    Spinner spinner;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        TabLayout mTabLayout = rootView.findViewById(R.id.tabLayout);
        ViewPager mViewPager = rootView.findViewById(R.id.viewPager);

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getChildFragmentManager());
        vpAdapter.addFragment(new InceptionFragmentFirst(), "Pojkar");
        vpAdapter.addFragment(new InceptionFragmentSecond(), "Flickor");

        spinner = rootView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource
                (getContext(), R.array.years, R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(spinnerAdapter);


        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(vpAdapter);
        return rootView;
    }


}
