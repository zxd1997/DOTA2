package com.example.zxd1997.dota2.Fragments.Player;


import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.PlayerActivity;
import com.example.zxd1997.dota2.Beans.Player;
import com.example.zxd1997.dota2.Beans.WL;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Objects;


public class PlayerOverviewFragment extends Fragment {


    public PlayerOverviewFragment() {

    }

    public static PlayerOverviewFragment newInstance() {
        return new PlayerOverviewFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_overview, container, false);
        PlayerActivity playerActivity = (PlayerActivity) getActivity();
        Player player = Objects.requireNonNull(playerActivity).getPlayer();
        WL wl = playerActivity.getWl();
        if (player == null || wl == null) {
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            SimpleDraweeView player_header = view.findViewById(R.id.player_header);
            TextView player_name = view.findViewById(R.id.player_nickname);
            TextView player_id = view.findViewById(R.id.player_id);
            TextView steam_profile = view.findViewById(R.id.steam_profile);
            SimpleDraweeView tier = view.findViewById(R.id.tier);
            TextView ranks = view.findViewById(R.id.rank);
            SimpleDraweeView stars = view.findViewById(R.id.star);
            TextView tier_name = view.findViewById(R.id.tier_name);
            player_header.setImageURI(player.getAvatarfull());
            player_name.setText(null == player.getPersonaname() ? getString(R.string.anonymous) : player.getPersonaname());
            player_id.setText(getString(R.string.id, player.getAccount_id()));
            SpannableString s = new SpannableString(player.getProfileurl());
            s.setSpan(new URLSpan(player.getProfileurl()), 0, player.getProfileurl().length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            steam_profile.setText(s);
            steam_profile.setMovementMethod(LinkMovementMethod.getInstance());
            if (player.getRank_tier() != 0) {
                int t = player.getRank_tier() / 10;
                int star = player.getRank_tier() % 10;
                int rank = player.getLeaderboard_rank();
                Log.d("rank", "handleMessage: " + rank);
                if (rank > 0) {
                    star = 0;
                    t = 8;
                    if (rank == 1) {
                        t = 12;
                    } else if (rank <= 10) {
                        t = 11;
                    } else if (rank <= 100) {
                        t = 10;
                    } else if (rank <= 1000) {
                        t = 9;
                    }
                    ranks.setText(String.valueOf(rank));
                }
                if (star > 0) {
                    TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.stars);
                    stars.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(typedArray.getResourceId(star - 1, 0))).build());
                }
                Log.d("rank", "handleMessage: " + t + " " + star);
                TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.tiers);
                tier.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(typedArray.getResourceId(t, 0))).build());
                typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.tier_names);
                tier_name.setText(String.valueOf(typedArray.getString(t)));
                typedArray.recycle();
            }
        }
        return view;
    }

}
