package com.example.zxd1997.dota2.Fragments.Match;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Activities.MatchActivity;
import com.example.zxd1997.dota2.Adapters.CastAdapter;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.MyApplication;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PurchaseAndCastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchaseAndCastFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
    public PurchaseAndCastFragment() {
        // Required empty public constructor
    }

    public static PurchaseAndCastFragment newInstance() {
        return new PurchaseAndCastFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_and_cast, container, false);
        MatchActivity activity = (MatchActivity) getActivity();
        Match match = Objects.requireNonNull(activity).getMatch();
        if (match == null || match.getPlayers() == null) {
            Log.d("null", "onCreateView: " + 111111);
            Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Objects.requireNonNull(getActivity()).startActivity(intent);
            getActivity().finish();
        } else {
            RecyclerView recyclerView = view.findViewById(R.id.p_c);
            recyclerView.setNestedScrollingEnabled(false);
            final CastAdapter castAdapter = new CastAdapter(getContext(), match.getPlayers(), "");
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 10);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (castAdapter.getItemViewType(position) == -1 || castAdapter.getItemViewType(position) == 9) ? 10 : 1;
                }
            });
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(castAdapter);
        }
        return view;
    }

}
