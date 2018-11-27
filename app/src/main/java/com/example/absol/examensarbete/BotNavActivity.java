package com.example.absol.examensarbete;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;


public class BotNavActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    private static final String TAG = "BotNavActivity";
    private FloatingActionButton fab;

    FragmentFirst fragmentFirst;
    FragmentSecond fragmentSecond;
    FragmentThird fragmentThird;

    NonSwipeableViewPager viewPager;
    NestedScrollView scrollView;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_nav);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        viewPager = findViewById(R.id.viewPager);
        scrollView = findViewById(R.id.scrollview);
        appBarLayout = findViewById(R.id.appBarLayout2);

        setupViewPager(viewPager);
        setupStuff();
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
        fragmentFirst = new FragmentFirst();
        fragmentSecond = new FragmentSecond();
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

                appBarLayout.setExpanded(true);
                fab.show();
                return true;

            case R.id.navigation_second :
                viewPager.setCurrentItem(1);

                fab.setVisibility(View.GONE);
                appBarLayout.setExpanded(true);
                return true;

            case R.id.navigation_third :
                viewPager.setCurrentItem(2);

                appBarLayout.setExpanded(true);
                fab.setVisibility(View.GONE);
                return true;

        }
        return false;
    }
}
