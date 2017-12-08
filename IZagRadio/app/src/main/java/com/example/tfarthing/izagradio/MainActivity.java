package com.example.tfarthing.izagradio;

import android.app.FragmentManager;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MediaPlayer mediaPlayer;
    Boolean isPlaying;
    private String streamingURL;

    final static String DEFAULT_STREAMING_URL = "https://cdnapisec.kaltura.com/p/1153021/sp/115302100/playManifest/entryId/0_06thv5og/format/applehttp/protocol/http/uiConfId/12279542/a.m3u8?referrer=aHR0cHM6Ly93d3cuZ29uemFnYS5lZHU=&playSessionId=90aa834a-c5d3-be83-8c82-f2cd6d657469";
    final static String MAIN_PAGE_URL = "https://www.gonzaga.edu/Student-Development/Student-Involvement-and-Leadership/Events-and-Initiatives/IZAG-Internet-Radio.asp";

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

        mediaPlayer = MediaPlayer.create(this, Uri.parse(DEFAULT_STREAMING_URL));

        //TODO: Get the URL using an API or other method instead of hard coding
        //MainActivity.getStreamingURLTask asyncTask = new MainActivity.getStreamingURLTask();
        //asyncTask.execute(MAIN_PAGE_URL);
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
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    "Listen to iZag! (:");

            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    "Have you heard about iZag Radio!? It's an awesome student run radio station here at GU! \n \n" +
                            "Check them out at izagradio.com");
            startActivity(Intent.createChooser(emailIntent, "Send your email in:"));

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

    // TODO: Change the method of retrieving the streaming URL
    // The streaming player is likely loaded with Javascript (not static HTML)
    // with a different playSessionId each time

    private class getStreamingURLTask extends AsyncTask<String, Integer, String> {

        final String TAG = "getStreamingURLTask";
        @Override
        protected String doInBackground(String... strings) {
            // The url of the stream to be returned
            String url = "";

            // Parse the webpage to get the URL
            try {
                Document doc = Jsoup.connect(strings[0]).get();

                Elements videoHolders = doc.getElementsByClass("persistentNativePlayer nativeEmbedPlayerPid");
                if(videoHolders != null && videoHolders.size() != 0) {
                    Elements videos = videoHolders.get(0).getElementsByTag("video");
                    if(videos != null && videos.size() != 0) {
                        url = videos.get(0).attr("src");
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return url;
        }

        @Override
        protected void onPostExecute(String url) {
            super.onPostExecute(url);
            // Update the streaming URL and create the stream
            streamingURL = url;
            mediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(streamingURL));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
