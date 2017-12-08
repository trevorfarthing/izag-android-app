package com.example.tfarthing.izagradio;

import android.app.FragmentManager;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MediaPlayer mediaPlayer;
    Boolean isPlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set Initial fragment to Listen activity
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ListenFragment())
                .commit();

        mediaPlayer = MediaPlayer.create(this, R.raw.zags_on_three);
        isPlaying = false;

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_listen) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ListenFragment())
                    .commit();
        } else if (id == R.id.nav_about_us) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new AboutUsFragment())
                    .commit();
        } else if (id == R.id.nav_shows) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ShowsFragment())
                    .commit();
        } else if (id == R.id.nav_schedule) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ScheduleFragment())
                    .commit();
        } else if (id == R.id.nav_share) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ShareFragment())
                    .commit();
        } else if (id == R.id.nav_contact_us) {
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            String emailList[] = { "izagRadio@gmail.com" };
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailList);
            startActivity(Intent.createChooser(emailIntent, "Send your email in:"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    public void onTogglePlay(View view) {

        ImageView playBtn = (ImageView) findViewById(R.id.play_btn);

        if (isPlaying) {
            playBtn.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            mediaPlayer.pause();
            isPlaying = false;
        }
        else {
            playBtn.setImageResource(R.drawable.ic_pause_black_24dp);
            mediaPlayer.start();
            isPlaying = true;
        }

    }
    */
}
