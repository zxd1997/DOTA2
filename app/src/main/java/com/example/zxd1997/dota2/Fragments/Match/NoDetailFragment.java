package com.example.zxd1997.dota2.Fragments.Match;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.example.zxd1997.dota2.Utils.OKhttp;

import java.util.Objects;

public class NoDetailFragment extends Fragment {

    public NoDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
    public static NoDetailFragment newInstance() {
        return new NoDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nodetail, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        Match match = Objects.requireNonNull(activity).getMatch();
        if (match == null || match.getPlayers() == null) {
//            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
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
                    OKhttp.post(getString(R.string.api) + getString(R.string.request) + id);
//                    Log.d("post", "onClick: " + getString(R.string.api) + getString(R.string.request) + id);
                    button.setVisibility(View.INVISIBLE);
                    textView.setText(getString(R.string.parsing));
                }
            });
        }
        return view;
    }

}
