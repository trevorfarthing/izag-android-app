package com.example.tfarthing.izagradio;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class ShowInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        // Find the views
        TextView showTitleTextView = (TextView)findViewById(R.id.showTitle);
        TextView descriptionTextView = (TextView)findViewById(R.id.showDescription);
        ImageView showImage = (ImageView)findViewById(R.id.showLogo);
        ImageView soundCloudButton = findViewById(R.id.soundcloud_button);

        // Get the data from the intent
        showTitleTextView.setText(getIntent().getStringExtra("showTitle"));
        descriptionTextView.setText(getIntent().getStringExtra("showDescription"));
        Glide.with(this).load(getIntent().getStringExtra("showImageURL")).into(showImage);

        // Set up the SoundCloud button
        final String soundCloudURL = getIntent().getStringExtra("soundCloudURL");
        soundCloudButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(soundCloudURL));
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                ShowInfoActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
