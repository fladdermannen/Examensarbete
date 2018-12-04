package com.example.absol.examensarbete;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;


public class BotNavActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    private static final String TAG = "BotNavActivity";
    private FloatingActionButton fab;

    FragmentFirst fragmentFirst;
    FragmentSecond fragmentSecond;
    FragmentThird fragmentThird;

    NonSwipeableViewPager viewPager;
    NestedScrollView scrollView;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    ImageButton toolbarInfo;

    public HashMap<String, String> nameDays = new HashMap<>();
    public NamedaySupplier namedaySupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_nav);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        viewPager = findViewById(R.id.viewPager);
        scrollView = findViewById(R.id.scrollview);
        appBarLayout = findViewById(R.id.appBarLayout2);
        toolbar = findViewById(R.id.toolbar);
        toolbarInfo = findViewById(R.id.toolbarInfo);

        setupStuff();

        namedaySupplier = new NamedaySupplier();
        getWiki();
    }

    private void setupStuff() {
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentFirst.displayAddNameScreen();
            }
        });

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putSerializable("Hashmap", nameDays);
        fragmentFirst = new FragmentFirst();
        fragmentFirst.setArguments(bundle);
        fragmentSecond = new FragmentSecond();
        fragmentSecond.setArguments(bundle);
        fragmentThird = new FragmentThird();
        mAdapter.addFragment(fragmentFirst, "first");
        mAdapter.addFragment(fragmentSecond, "second");
        mAdapter.addFragment(fragmentThird,"third");
        viewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.navigation_first :
                viewPager.setCurrentItem(0);

                toolbar.setTitle(R.string.fragment_first_title);
                toolbarInfo.setVisibility(View.GONE);
                appBarLayout.setExpanded(true);
                fab.show();
                fragmentFirst.populateView();
                return true;

            case R.id.navigation_second :
                viewPager.setCurrentItem(1);

                toolbar.setTitle(R.string.fragment_second_title);
                toolbarInfo.setVisibility(View.VISIBLE);
                fab.hide();
                appBarLayout.setExpanded(true);
                return true;

            case R.id.navigation_third :
                viewPager.setCurrentItem(2);

                toolbarInfo.setVisibility(View.GONE);
                appBarLayout.setExpanded(true);
                fab.hide();
                return true;

        }
        return false;
    }

    void getWiki() {

        String mURL = "https://sv.wikipedia.org/w/api.php?action=parse&page=Lista_%C3%B6ver_namnsdagar_i_Sverige_i_datumordning&prop=wikitext&format=json";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        nameDays = namedaySupplier.parseWikitext(response);
                        Log.d(TAG, "onResponse: "  +nameDays.get("Patrik"));
                        setupViewPager(viewPager);
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

}
