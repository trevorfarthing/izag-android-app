package com.example.tfarthing.izagradio;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jtwheadon on 11/29/17.
 */

public class ShareFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.share_layout, container, false);
        // Set Title
        getActivity().setTitle("Share");
        return myView;
    }
}
