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
    Boolean isPlaying;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.listen_layout, container, false);
        return myView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.zags_on_three);
        isPlaying = false;

        // Handle click event for calculate button
        ImageView playBtn = (ImageView) myView.findViewById(R.id.play_btn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTogglePlay();
            }
        });
    }

    public void onTogglePlay() {

        ImageView playBtn = (ImageView) myView.findViewById(R.id.play_btn);

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
}
