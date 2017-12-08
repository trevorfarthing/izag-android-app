package com.example.tfarthing.izagradio;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by jtwheadon on 11/29/17.
 */

public class ListenFragment extends Fragment {

    View myView;
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.listen_layout, container, false);

        // Set Title
        getActivity().setTitle("Live Stream");

        return myView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView playBtn = (ImageView) myView.findViewById(R.id.play_btn);

        // Set image
        if (((MainActivity)this.getActivity()).isPlaying) {
            playBtn.setImageResource(R.drawable.ic_pause_circle_outline_white);

        }
        else {
            playBtn.setImageResource(R.drawable.ic_play_circle_outline_white);
        }

        // Handle click event for calculate button
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTogglePlay();
            }
        });
    }

    public void onTogglePlay() {

        ImageView playBtn = (ImageView) myView.findViewById(R.id.play_btn);

        if (((MainActivity)this.getActivity()).isPlaying) {
            playBtn.setImageResource(R.drawable.ic_play_circle_outline_white);
            ((MainActivity)this.getActivity()).mediaPlayer.pause();
            ((MainActivity)this.getActivity()).isPlaying = false;
        }
        else {
            playBtn.setImageResource(R.drawable.ic_pause_circle_outline_white);
            ((MainActivity)this.getActivity()).mediaPlayer.start();
            ((MainActivity)this.getActivity()).isPlaying = true;
        }

    }
}
