package com.example.zxd1997.dota2.Fragments.Main;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    Fresco.getImagePipeline().resume();
                else Fresco.getImagePipeline().pause();
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        new Thread(() -> {
            SparseIntArray items = new SparseIntArray();
            TypedArray typedArray = Objects.requireNonNull(getContext()).getResources().obtainTypedArray(R.array.items);
            int i = 0;
            for (Map.Entry<String, Item> entry : MainActivity.items.entrySet()) {
                i++;
                Item item = entry.getValue();
                if (item.getId() < 1000 && item.getId() != 71 && item.getId() != 238 && item.getId() != 239 && item.getId() != 35 && item.getId() != 196 && item.getId() != 275 && item.getId() != 276) {
                    try {
                        if (!item.getDname().contains("Recipe"))
                            items.append(entry.getValue().getId(), i);
                    } catch (Exception ignored) {
                    }
//                Log.d("item", "onCreateView: " + entry.getValue().getId() + " " + entry.getValue().getDname());
                }
            }
            typedArray.recycle();
            view.post(() -> recyclerView.setAdapter(new ItemAdapter(getContext(), items)));
        }).start();
        return view;
    }

}
