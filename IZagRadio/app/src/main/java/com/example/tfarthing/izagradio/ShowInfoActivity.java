package com.example.tfarthing.izagradio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class ShowInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        // Find the views
        TextView showTitleTextView = (TextView)findViewById(R.id.showTitle);
        TextView descriptionTextView = (TextView)findViewById(R.id.showDescription);
        ImageView showImage = (ImageView)findViewById(R.id.showLogo);

        // Get the data from the intent
        showTitleTextView.setText(getIntent().getStringExtra("showTitle"));
        descriptionTextView.setText(getIntent().getStringExtra("showDescription"));
        Glide.with(this).load(getIntent().getStringExtra("showImageURL")).into(showImage);

    }
}
