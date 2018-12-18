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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class InceptionFragmentSecond extends Fragment implements PopularNamesListAdapter.PopularNamesAdapterListener {

    private static final String TAG = "InceptionFragmentSecond";

    private ArrayList<PopularNamePOJO> mArrayList = new ArrayList<>();
    private PopularNamesListAdapter mAdapter;

    RecyclerView mRecyclerView;
    VolleyFetcher mVolley = new VolleyFetcher();

    HashMap<String, String> namedays = new HashMap<>();

    String mName;
    String currentTable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        namedays = (HashMap<String, String>)getArguments().getSerializable("Hashmap");

        View rootView = inflater.inflate(R.layout.inception_fragment_second, container, false);
        mRecyclerView = rootView.findViewById(R.id.popNamesRecyclerViewGirls);

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
        String requestString = mVolley.makeRequestString(year, "girls");
        checkAPI(requestString);
    }

    private void checkAPI(String requestString){

        mArrayList.clear();
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


                    PopularNamePOJO newName = new PopularNamePOJO(name, parsedAmount, true, "test");
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
        getWikiNameInfo(name, name.getName());
    }

    public void filterList(String searchText) {
        mAdapter.getFilter().filter(searchText);
    }

    public void setCurrentTable(String tableName) {
        currentTable = tableName;
    }


    private void getWikiNameInfo(final PopularNamePOJO pojo, String name) {
        mName = pojo.getName();

        //Handle known exceptions
        switch(name) {
            case ("Sofia") :
                name = name + "_(namn)";
                break;
            case ("Saga") :
                name = name + "_(namn)";
                break;
            case ("Freja") :
                name = name + "_(namn)";
                break;
            case ("Vera") :
                name = name + "_(förnamn)";
                break;
            case ("Lea") :
                name = name + "_(namn)";
                break;
            case ("Linnéa") :
                name = "Linnea_(namn)";
                break;
            case ("Nora") :
                name = name + "_(namn)";
                break;
            case ("Celine") :
                name = name + "_(namn)";
                break;
            case ("Liv") :
                name = name + "_(namn)";
                break;
            case ("Livia") :
                name = name + "_(namn)";
                break;
            case ("Meja") :
                name = name + "_(namn)";
                break;
            case ("Tuva") :
                name = name + "_(namn)";
                break;
            case ("Bianca") :
                name = name + "_(namn)";
                break;
            case ("My") :
                name = name + "_(namn)";
                break;
            case ("Lisa") :
                name = name + "_(namn)";
                break;
            case ("Sanna") :
                name = name + "_(namn)";
                break;
            case ("Angelica") :
                name = "Angelika";
                break;
        }

        String mURL = "https://sv.wikipedia.org/api/rest_v1/page/summary/" + name;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String feedback = convertWikiJson(response, mName, pojo);

                        if(mName.contains("_")) {
                            mName = mName.replace("_(namn)", "");
                        }

                        if(feedback != null) {
                            openBotSheet(pojo, feedback);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: ", error);
                        openBotSheet(pojo, null);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private String convertWikiJson(JSONObject json, String name, PopularNamePOJO pojo) {

        String type = "";
        //Grab data from response
        try{
            type = json.getString("type");
        } catch (Exception e){
            e.printStackTrace();
        }

        Log.d(TAG, "convertJson: " + type);


        //Some names are listed as disambiguation when they shouldn't be. Return their info anyway
        if(name.equals("Sandra") || name.equals("Lisa") || name.equals("Sanna") || name.equals("Angelica")) {
            String extract = "";
            try{
                extract = json.getString("extract");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return extract;
        } else if(type.equals("disambiguation")) {
            //Disambiguation found, force name page
            getWikiNameInfo(pojo,name + "_(namn)");
            return null;
        } else if(type.equals("https://mediawiki.org/wiki/HyperSwitch/errors/not_found")) {
            //Name not found
            return null;
        } else if(type.equals("standard")) {
            //Name found, return info
            String extract = "";
            try{
                extract = json.getString("extract");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return extract;
        }


        return null;
    }

    private void openBotSheet(PopularNamePOJO name, String info) {
        if(info != null) {
            if(namedays.get(name.getName()) != null) {
                NameInfoBottomSheetDialogFragment bottomSheet =
                        NameInfoBottomSheetDialogFragment.newInstance(name.getName(), name.getFemale(), "Namnsdag:  " + namedays.get(name.getName()), info, true, currentTable);
                bottomSheet.show(getFragmentManager(), "name_info_fragment");
            } else {
                NameInfoBottomSheetDialogFragment bottomSheet =
                        NameInfoBottomSheetDialogFragment.newInstance(name.getName(), name.getFemale(), "Ingen namnsdag", info, true, currentTable);
                bottomSheet.show(getFragmentManager(), "name_info_fragment");
            }
        } else {
            if(namedays.get(name.getName()) != null) {
                NameInfoBottomSheetDialogFragment bottomSheet =
                        NameInfoBottomSheetDialogFragment.newInstance(name.getName(), name.getFemale(), "Namnsdag:  " + namedays.get(name.getName()), "Ingen information tillgänglig.", true, currentTable);
                bottomSheet.show(getFragmentManager(), "name_info_fragment");
            } else {
                NameInfoBottomSheetDialogFragment bottomSheet =
                        NameInfoBottomSheetDialogFragment.newInstance(name.getName(), name.getFemale(), "Ingen namnsdag", "Ingen information tillgänglig.", true, currentTable);
                bottomSheet.show(getFragmentManager(), "name_info_fragment");
            }
        }
    }
}
