package com.example.zxd1997.dota2.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Okhttp;

public class NoDetailFragment extends Fragment {

    public NoDetailFragment() {
        // Required empty public constructor
    }


    public static NoDetailFragment newInstance() {
        NoDetailFragment fragment = new NoDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nodetail, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        final long id = activity.getMatch().getMatch_id();
        final long time = activity.getMatch().getStart_time();
        final Button button = view.findViewById(R.id.reuqest_salt);
        final TextView textView = view.findViewById(R.id.no_salt);
        if (System.currentTimeMillis() / 1000 - time > 30 * 24 * 3600) {
            button.setVisibility(View.INVISIBLE);
            textView.setText(getString(R.string.expired));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Okhttp.post(getString(R.string.api) + getString(R.string.request) + id);
                Log.d("post", "onClick: " + getString(R.string.api) + getString(R.string.request) + id);
                button.setVisibility(View.INVISIBLE);
                textView.setText(getString(R.string.parsing));
            }
        });
        return view;
    }

}
