package com.example.absol.examensarbete;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FragmentFirst extends Fragment implements MyNamesListAdapter.MyNamesAdapterListener, CardViewAdapter.CardViewAdapterListener {

    private static final String TAG = "FragmentFirst";

    private String currentTable = "table_1";

    HashMap<String, String> namedays = new HashMap<>();
    DatabaseHelper mDatabaseHelper;

    private ArrayList<String> mArrayList = new ArrayList<>();
    private ArrayList<String> mLists = new ArrayList<>();
    private CardViewAdapter mCardsAdapter;
    private MyNamesListAdapter mAdapter;

    RecyclerView mRecyclerView, mCardsRecyclerView;
    LinearLayoutManager mLayoutManager, cardsLayoutManager;
    PopupWindow mPopupWindow;
    EditText popupEditText;
    Button popupButton;

    private String mName;
    boolean keyboardActive = false;
    private Bundle savedState = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDatabaseHelper = new DatabaseHelper(context, currentTable);
    }

    @Override
    public void onPause() {
        if(keyboardActive)
            closeKeyboard();
        super.onPause();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        namedays = (HashMap<String, String>)getArguments().getSerializable("Hashmap");

        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        mCardsRecyclerView = rootView.findViewById(R.id.cardviewsRecyclerView);
        mRecyclerView = rootView.findViewById(R.id.myNamesRecyclerView);

        mCardsAdapter = new CardViewAdapter(mLists, this);
        mAdapter = new MyNamesListAdapter(mArrayList, this);

        cardsLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mCardsRecyclerView.setLayoutManager(cardsLayoutManager);
        mCardsRecyclerView.setAdapter(mCardsAdapter);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        //Restore selected card from bundle
        if(savedInstanceState != null && savedState == null) {
            savedState = savedInstanceState.getBundle("mKey");
            Log.d(TAG, "onCreateView: hello");
        }
        if(savedState != null) {
            int cardPos = savedState.getInt("mKey");
            mCardsAdapter.setSelectedPos(cardPos);
        }
        savedState = null;

        //Handling swipe and drag&drop
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                moveNamesInDB(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.onItemRemove(viewHolder, mRecyclerView);
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    // Get RecyclerView item from the ViewHolder
                    View itemView = viewHolder.itemView;

                    new RecyclerViewSwipeDecorator.Builder(getContext(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.red))
                            .addActionIcon(R.drawable.ic_delete)
                            .create()
                            .decorate();


                    //Fade out name
                    final float alpha = 1.0f - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }

        };


        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);

        populateCardView();
        populateView();

        return rootView;
    }



    public boolean addData(String newEntryName, String newEntryGender) {
        Cursor data = mDatabaseHelper.getData();
        int index = data.getCount() + 1;
        boolean insertData = mDatabaseHelper.addData(newEntryName, newEntryGender, index);

        if (insertData) {
            toastMessage("Data successfully inserted");
        }
        else {
            toastMessage("Something went wrong");
        }
        return insertData;
    }


    private void toastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    public void populateView(){
        mArrayList.clear();
        Cursor data = mDatabaseHelper.getData();
        while(data.moveToNext()) {
            mArrayList.add(data.getString(2));
        }

        Log.d(TAG, "populateView: " + mArrayList.toString());
        mAdapter.notifyDataSetChanged();
    }

    public void populateCardView() {
        mLists.clear();

        mLists.add("table_1");

        if(mDatabaseHelper.checkIfTableExists("table_2")) {
            mLists.add("table_2");
        }
        if(mDatabaseHelper.checkIfTableExists("table_3")) {
            mLists.add("table_3");
        }
        if(mDatabaseHelper.checkIfTableExists("table_4")) {
            mLists.add("table_4");
        }

        mCardsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNameSelected(String name){
        getWikiNameInfo(name);

        Cursor data = mDatabaseHelper.getItemID(name); //The ID associated with that name
        int itemID = -1;
        while(data.moveToNext()) {
            itemID = data.getInt(0);
        }
        if(itemID > -1){
            Log.d(TAG, "onNameSelected: The ID of name " + name + " is " + itemID);
        } else {
            toastMessage("No ID associated with that name");
            return;
        }

        data = mDatabaseHelper.getItemIndex(name);
        itemID = -1;
        while(data.moveToNext()) {
            itemID = data.getInt(0);
        }
        if(itemID > -1){
            Log.d(TAG, "onNameSelected: The Index of name " + name + " is " + itemID);
        } else {
            toastMessage("No index associated with that name");
        }

    }

    @Override
    public void onNewListSelected() {

        if(!mDatabaseHelper.checkIfTableExists("table_2")) {
            mDatabaseHelper.createTable("table_2");
            mLists.add("table_2");
            populateCardView();
            return;
        }
        if(!mDatabaseHelper.checkIfTableExists("table_3")) {
            mDatabaseHelper.createTable("table_3");
            mLists.add("table_3");
            populateCardView();
            return;
        }
        if(!mDatabaseHelper.checkIfTableExists("table_4")) {
            mDatabaseHelper.createTable("table_4");
            mLists.add("table_4");
            populateCardView();
        }

    }

    @Override
    public void onCardSelected(int position) {
        mCardsAdapter.notifyItemChanged(mCardsAdapter.getSelectedPos());
        mCardsAdapter.setSelectedPos(position);
        mCardsAdapter.notifyItemChanged(position);

        String table = mLists.get(position);
        currentTable = table;
        Log.d(TAG, "onCardSelected: " + table);
        mDatabaseHelper.changeTable(table);

        populateView();
    }

    @Override
    public void onCardLongClick(final int position) {

        if(position != 0) {
            Snackbar snackbar = Snackbar.make(mRecyclerView, "RADERA LISTA?", Snackbar.LENGTH_LONG)
                    .setAction("RADERA", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            String table = mLists.get(position);
                            mDatabaseHelper.deleteTable(table);

                            if (currentTable.equals(table)) {
                                currentTable = "table_1";
                                mDatabaseHelper.changeTable("table_1");
                                mCardsAdapter.setSelectedPos(0);
                                mCardsAdapter.notifyItemChanged(0);
                            }

                            mCardsAdapter.notifyItemRemoved(position);
                            populateCardView();
                            populateView();


                        }
                    });
            snackbar.show();
        }

    }

    public String getCurrentTable() {
        return currentTable;
    }

    public void deleteNamesFromDb(String name) {
            Cursor data = mDatabaseHelper.getItemID(name);
            int itemID = -1;
            while (data.moveToNext()) {
                itemID = data.getInt(0);
            }
            if (itemID > -1) {
                Cursor indexData = mDatabaseHelper.getItemIndex(name);
                int itemIndex = -1;
                while (indexData.moveToNext()) {
                    itemIndex = indexData.getInt(0);
                }
                if (itemIndex > -1) {
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
    public void deleteName(String name) {
        deleteNamesFromDb(name);
    }


    public void moveNamesInDB(int index, int target) {

        String thisName = mArrayList.get(index);
        String otherName = mArrayList.get(target);
        int thisNameIndex = -1;
        int otherNameIndex = -1;

        Cursor data = mDatabaseHelper.getItemIndex(thisName);
        while (data.moveToNext()) {
            thisNameIndex = data.getInt(0);
        }
        data = mDatabaseHelper.getItemIndex(otherName);
        while (data.moveToNext()) {
            otherNameIndex = data.getInt(0);
        }

        if(thisNameIndex > -1 && otherNameIndex > -1)
            mDatabaseHelper.moveItems(thisNameIndex, otherNameIndex);
        //mLayoutManager.scrollToPositionWithOffset(mArrayList.indexOf(thisName), mRecyclerView.getHeight()/2);
    }


    public void displayAddNameScreen() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popup_layout,null);
        popupEditText = customView.findViewById(R.id.et);
        popupButton = customView.findViewById(R.id.btn);

        //Force open keyboard :)
        popupEditText.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        keyboardActive = true;


        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(popupEditText.length() != 0){
                    String newEntry = popupEditText.getText().toString();
                    String newEntryName = newEntry.substring(0,1).toUpperCase() + newEntry.substring(1).toLowerCase();
                    if(addData(newEntryName, "boy")) {
                        popupEditText.setText("");
                        mArrayList.add(newEntryName);
                        mAdapter.notifyDataSetChanged();
                        mPopupWindow.dismiss();
                    }
                } else
                    toastMessage("Du måste fylla i textfältet.");
            }
        });

        mPopupWindow = new PopupWindow(
                customView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(20.0f);
        }

        mPopupWindow.setAnimationStyle(R.style.popup_window_animation_addname);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(mRecyclerView, Gravity.CENTER,0,0);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                closeKeyboard();
            }
        });
    }

    private void closeKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        keyboardActive = false;
    }

    public void filterNameList(String query) {
        mAdapter.getFilter().filter(query);
    }

    private void getWikiNameInfo(String name) {
        mName = name;

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
            case ("Leo") :
                name = name + "_(förnamn)";
                break;
            case ("Charlie") :
                name = "Karl";
                break;
            case ("Sam") :
                name = "Samuel_(namn)";
                break;
            case ("Samuel") :
                name = name + "_(namn)";
                break;
            case ("Eddie") :
                name = name + "_(namn)";
                break;
            case ("Björn") :
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
                        String feedback = convertJson(response, mName);

                        if(mName.contains("(namn)")) {
                            mName = mName.replace("_(namn)", "");
                        } else if(mName.contains("(förnamn)")) {
                            mName = mName.replace("_(förnamn)", "");
                        }

                        if(feedback != null) {
                            openBotSheet(mName, feedback);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: ", error);
                        openBotSheet(mName, null);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private String convertJson(JSONObject json, String name) {

        String type = "";
        //Grab data from response
        try{
            type = json.getString("type");
        } catch (Exception e){
            e.printStackTrace();
        }

        Log.d(TAG, "convertJson: " + type);


        //Some names are listed as disambiguation when they shouldn't be. Return their info anyway
        if(name.equals("Samuel") || name.equals("Daniel") || name.equals("Robert") || name.equals("Eddie") || name.equals("Magnus")
                || name.equals("Sandra") || name.equals("Lisa") || name.equals("Sanna") || name.equals("Angelica")) {

            String extract = "";
            try{
                extract = json.getString("extract");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return extract;
        }
        else if(type.equals("disambiguation")) {
            //Disambiguation found, force name page
            getWikiNameInfo(name + "_(namn)");
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

    private void openBotSheet(String name, String info) {
        if(info != null) {
            if (namedays.get(name) != null) {
                NameInfoBottomSheetDialogFragment bottomSheet =
                        NameInfoBottomSheetDialogFragment.newInstance(name, "Namnsdag:  " + namedays.get(name), info);
                bottomSheet.show(getFragmentManager(), "name_info_fragment");
            } else {
                NameInfoBottomSheetDialogFragment bottomSheet =
                        NameInfoBottomSheetDialogFragment.newInstance(name, "Ingen namnsdag", info);
                bottomSheet.show(getFragmentManager(), "name_info_fragment");
            }
        } else {
            if (namedays.get(name) != null) {
                NameInfoBottomSheetDialogFragment bottomSheet =
                        NameInfoBottomSheetDialogFragment.newInstance(name, "Namnsdag:  " + namedays.get(name), "Ingen information tillgänglig");
                bottomSheet.show(getFragmentManager(), "name_info_fragment");
            } else {
                NameInfoBottomSheetDialogFragment bottomSheet =
                        NameInfoBottomSheetDialogFragment.newInstance(name, "Ingen namnsdag", "Ingen information tillgänglig.");
                bottomSheet.show(getFragmentManager(), "name_info_fragment");
            }
        }
    }

    //Save stuff in bundle (selected card)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        savedState = saveState();
    }

    private Bundle saveState() {
        Bundle state = new Bundle();
        state.putInt("mKey", mCardsAdapter.getSelectedPos());
        return state;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("mKey", (savedState != null) ? savedState : saveState());
    }
}
