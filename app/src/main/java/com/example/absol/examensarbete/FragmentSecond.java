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
import android.widget.AdapterView;
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

public class FragmentSecond extends Fragment implements AdapterView.OnItemSelectedListener {

    private final static String TAG = "FragmentSecond";
    InceptionFragmentFirst pojkarFragment = new InceptionFragmentFirst();
    InceptionFragmentSecond flickorFragment = new InceptionFragmentSecond();

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
        vpAdapter.addFragment(pojkarFragment, "Pojkar");
        vpAdapter.addFragment(flickorFragment, "Flickor");

        spinner = rootView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource
                (getContext(), R.array.years, R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);

        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(vpAdapter);
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int year = Integer.parseInt((String)parent.getItemAtPosition(position));
        pojkarFragment.loadYearFromSpinner(year);
        flickorFragment.loadYearFromSpinner(year);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
