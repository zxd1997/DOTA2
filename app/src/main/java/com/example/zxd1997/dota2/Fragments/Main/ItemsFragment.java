package com.example.zxd1997.dota2.Fragments.Main;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Adapters.ItemAdapter;
import com.example.zxd1997.dota2.Beans.Item;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Map;
import java.util.Objects;

public class ItemsFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
    public ItemsFragment() {
    }

    public static ItemsFragment newInstance() {
        return new ItemsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.items);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        SparseArray<String> items = new SparseArray<>();
        TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.items);
        int i = 0;
        for (Map.Entry<String, Item> entry : MainActivity.items.entrySet()) {
            i++;
            if (entry.getValue().getId() < 1000 && entry.getValue().getId() != 71 && entry.getValue().getId() != 238 && entry.getValue().getId() != 239 && entry.getValue().getId() != 35) {

                try {
                    items.append(entry.getValue().getId(), typedArray.getString(i) != null ? typedArray.getString(i) : "");
                } catch (Exception e) {

                }
                Log.d("item", "onCreateView: " + entry.getValue().getId() + " " + entry.getValue().getDname());
            }
        }
        typedArray.recycle();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    Fresco.getImagePipeline().resume();
                else Fresco.getImagePipeline().pause();
            }
        });
        recyclerView.setAdapter(new ItemAdapter(getContext(), items));
        return view;
    }


}
