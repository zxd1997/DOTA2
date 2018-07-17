package com.example.zxd1997.dota2.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.ItemActivity;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    SparseArray<String> items;

    public ItemAdapter(Context context, SparseArray<String> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        Tools.showImage(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + items.keyAt(position), R.drawable.class))).build(), itemHolder.header);
        itemHolder.name.setText(items.valueAt(position));
        itemHolder.itemView.setOnClickListener(v -> {
            ViewCompat.setTransitionName(itemHolder.header, "item_" + items.keyAt(position));
            Intent intent = new Intent(context, ItemActivity.class);
            intent.putExtra("id", items.keyAt(position));
            intent.putExtra("name", items.valueAt(position));
            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context, itemHolder.header, ViewCompat.getTransitionName(itemHolder.header)).toBundle());
        });
//        Log.d("item", "onBindViewHolder: "+items.valueAt(position)+" "+items.get(position-1));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView header;
        TextView name;

        ItemHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.item);
            name = itemView.findViewById(R.id.item_name);
        }
    }
}
