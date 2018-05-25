package com.example.zxd1997.dota2.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Okhttp;

public class NoDetailFragment extends Fragment {

    public NoDetailFragment() {
        // Required empty public constructor
    }


    public static NoDetailFragment newInstance(long id) {
        NoDetailFragment fragment = new NoDetailFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nodetail, container, false);
        final long id = getArguments().getLong("id");
        final Button button = view.findViewById(R.id.reuqest_salt);
        final TextView textView = view.findViewById(R.id.no_salt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Okhttp.post(getString(R.string.request) + id);
                button.setVisibility(View.INVISIBLE);
                textView.setText(getString(R.string.parsing));
            }
        });
        return view;
    }

}
