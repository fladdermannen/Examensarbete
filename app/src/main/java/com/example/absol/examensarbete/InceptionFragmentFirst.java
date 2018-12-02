package com.example.absol.examensarbete;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.HashMap;

public class InceptionFragmentFirst extends Fragment implements PopularNamesListAdapter.PopularNamesAdapterListener {

    private static final String TAG = "InceptionFragmentFirst";

    private ArrayList<PopularNamePOJO> mArrayList = new ArrayList<>();
    private PopularNamesListAdapter mAdapter;

    RecyclerView mRecyclerView;
    VolleyFetcher mVolley = new VolleyFetcher();

    HashMap<String, String> namedays = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        namedays = (HashMap<String, String>)getArguments().getSerializable("Hashmap");
        View rootView = inflater.inflate(R.layout.inception_fragment_first, container, false);
        mRecyclerView = rootView.findViewById(R.id.popNamesRecyclerViewBoys);

        mAdapter = new PopularNamesListAdapter(mArrayList, this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }


    public void loadYearFromSpinner(int year) {
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();
        String requestString = mVolley.makeRequestString(year, "boys");
        checkAPI(requestString);
    }

    private void checkAPI(String requestString){

        String mURL = "http://api.scb.se/OV0104/v1/doris/sv/ssd/START/BE/BE0001/BE0001D/BE0001T05AR";

        JSONObject request = new JSONObject();
        try{
            request = new JSONObject(requestString);
        } catch (Exception e){
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                mURL,
                request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        convertJson(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: ", error);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void convertJson(JSONObject jsonObject){
        JSONArray jArray = new JSONArray();
        //Grab data from response
        try{
            jArray = jsonObject.getJSONArray("data");

        } catch (Exception e){
            e.printStackTrace();
        }

        //Grab each object from array, strip unnecessary stuff and create java objects
        for(int i = 0; i < jArray.length(); i ++) {
            try{
                JSONObject oneObject = jArray.getJSONObject(i);
                String name = oneObject.getString("key");
                String amount = oneObject.getString("values");

                if(!amount.equals("[\"..\"]")) {

                    name = name.replace("[", "");
                    name = name.replace("]", "");
                    name = name.replace("\"", "");
                    name = name.replace(",", "");
                    for(int x = 0; x < 10; x++) {
                        String remove = ""+x;
                        name = name.replace(remove, "");
                    }
                    amount = amount.replace("[", "");
                    amount = amount.replace("\"", "");
                    amount = amount.replace("]", "");
                    int parsedAmount = Integer.parseInt(amount);

                    PopularNamePOJO newName = new PopularNamePOJO(name, parsedAmount, false, "test");
                    mArrayList.add(newName);
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        Collections.sort(mArrayList);
        for (int i = 1; i <= mArrayList.size(); i++) {
            mArrayList.get(i-1).setRank(i);
        }
        Log.d(TAG, "convertJson: " + mArrayList.size());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNameSelected(PopularNamePOJO name) {
        if(namedays.get(name.getName()) != null) {
            NameInfoBottomSheetDialogFragment bottomSheet =
                    NameInfoBottomSheetDialogFragment.newInstance(name.getName(), name.getFemale(), "Namnsdag:  " + namedays.get(name.getName()), "lorem");
            bottomSheet.show(getFragmentManager(), "name_info_fragment");
        } else {
            NameInfoBottomSheetDialogFragment bottomSheet =
                    NameInfoBottomSheetDialogFragment.newInstance(name.getName(), name.getFemale(), "Ingen namnsdag", "lorem");
            bottomSheet.show(getFragmentManager(), "name_info_fragment");
        }
    }

}
