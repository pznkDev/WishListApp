package com.kpi.slava.wishlistapp.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.fragments.AddMovieFragment;
import com.kpi.slava.wishlistapp.fragments.HomeFragment;
import com.kpi.slava.wishlistapp.fragments.MoviesFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private AddMovieFragment addMovieFragment;
    private HomeFragment homeFragment;
    private MoviesFragment moviesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initNavigationDrawer();
        initFragments();

        initFabs();
    }

    private void initFragments() {

        fragmentManager = getSupportFragmentManager();

        addMovieFragment = new AddMovieFragment();
        homeFragment = new HomeFragment();
        moviesFragment = new MoviesFragment();


        fragmentManager.beginTransaction().add(R.id.main_container, homeFragment).commit();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initNavigationDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        transaction = fragmentManager.beginTransaction();

        switch(item.getItemId()){
            case (R.id.nav_home) :
                transaction.replace(R.id.main_container, homeFragment);
                break;

            case (R.id.nav_movies) :
                transaction.replace(R.id.main_container, moviesFragment);
                break;

            case (R.id.nav_books) :

                break;

            case (R.id.nav_my_list) :

                break;

            case (R.id.nav_notes) :

                break;

            case (R.id.nav_settings) :

                break;

            case (R.id.nav_close) :
                finish();
                break;
        }
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initFabs() {
        findViewById(R.id.fab_add_movie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovieFragment.show(getSupportFragmentManager(), AddMovieFragment.TAG);
            }
        });

        findViewById(R.id.fab_add_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "add book", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.fab_add_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "add note", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
