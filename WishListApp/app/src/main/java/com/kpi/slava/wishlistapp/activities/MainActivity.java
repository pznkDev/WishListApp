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
import android.widget.Spinner;

import com.kpi.slava.wishlistapp.R;
import com.kpi.slava.wishlistapp.fragments.BooksFragment;
import com.kpi.slava.wishlistapp.fragments.ControlBookFragment;
import com.kpi.slava.wishlistapp.fragments.ControlMovieFragment;
import com.kpi.slava.wishlistapp.fragments.ControlNoteFragment;
import com.kpi.slava.wishlistapp.fragments.HomeFragment;
import com.kpi.slava.wishlistapp.fragments.LibraryFragment;
import com.kpi.slava.wishlistapp.fragments.MoviesFragment;
import com.kpi.slava.wishlistapp.fragments.NotesFragment;
import com.kpi.slava.wishlistapp.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    Spinner spinner;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private ControlMovieFragment controlMovieFragment;
    private ControlBookFragment controlBookFragment;
    private ControlNoteFragment controlNoteFragment;
    private HomeFragment homeFragment;
    private MoviesFragment moviesFragment;
    private BooksFragment booksFragment;
    private LibraryFragment libraryFragment;
    private NotesFragment notesFragment;

    private SettingsFragment settingsFragment;

    private final int CONTAINER = R.id.main_container;

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

        controlMovieFragment = new ControlMovieFragment();
        controlBookFragment = new ControlBookFragment();
        controlNoteFragment = new ControlNoteFragment();
        homeFragment = new HomeFragment();
        moviesFragment = new MoviesFragment();
        booksFragment = new BooksFragment();
        libraryFragment = new LibraryFragment();
        notesFragment = new NotesFragment();

        settingsFragment = new SettingsFragment();

        fragmentManager.beginTransaction().add(CONTAINER, homeFragment).commit();
    }

    private void initToolbar() {
        spinner = (Spinner) findViewById(R.id.spinner_navigation);
        spinner.setVisibility(View.GONE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
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

                toolbar.setTitle("Home");
                spinner.setVisibility(View.GONE);

                break;

            case (R.id.nav_movies) :
                transaction.replace(CONTAINER, moviesFragment);

                toolbar.setTitle("Movies");
                spinner.setVisibility(View.GONE);

                break;

            case (R.id.nav_books) :
                transaction.replace(CONTAINER, booksFragment);

                toolbar.setTitle("Books");
                spinner.setVisibility(View.GONE);

                break;

            case (R.id.nav_my_list) :
                transaction.replace(CONTAINER, libraryFragment);

                toolbar.setTitle("");

                break;

            case (R.id.nav_notes) :
                transaction.replace(CONTAINER, notesFragment);

                toolbar.setTitle("Notes");
                spinner.setVisibility(View.GONE);

                break;

            case (R.id.nav_settings) :

                transaction.replace(CONTAINER, settingsFragment);

                toolbar.setTitle("Settings");
                spinner.setVisibility(View.GONE);

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
                controlMovieFragment.show(fragmentManager, ControlMovieFragment.TAG);
            }
        });

        findViewById(R.id.fab_add_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlBookFragment.show(fragmentManager, ControlBookFragment.TAG);
            }
        });

        findViewById(R.id.fab_add_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlNoteFragment.show(fragmentManager, ControlNoteFragment.TAG);
            }
        });

    }

}
