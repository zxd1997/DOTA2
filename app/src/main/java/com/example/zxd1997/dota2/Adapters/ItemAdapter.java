package com.example.zxd1997.dota2.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.ItemActivity;
import com.example.zxd1997.dota2.Beans.Item;
import com.example.zxd1997.dota2.R;
import com.example.zxd1997.dota2.Utils.Tools;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Objects;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private List<Item> items;

    public ItemAdapter(Context context, List<Item> items) {
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
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.items);
        Item item = items.get(position);
        Tools.showImage(new Uri.Builder().scheme("res").path(String.valueOf(Tools.getResId("item_" + item.getId(), R.drawable.class))).build(), itemHolder.header);
        itemHolder.name.setText(typedArray.getText(item.getPos()));
        typedArray.recycle();
        final SpannableString gold = new SpannableString(" ");
        Drawable drawable = context.getDrawable(R.drawable.gold);
        Objects.requireNonNull(drawable).setBounds(0, 0, 48, 32);
        gold.setSpan(new ImageSpan(drawable), 0, gold.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        itemHolder.gold.setText(new SpannableStringBuilder().append(gold).append(String.valueOf(item.getCost())).append(" "));
        if (!item.getDname().contains("Recipe"))
            itemHolder.itemView.setOnClickListener(v -> {
                ViewCompat.setTransitionName(itemHolder.header, "item_" + item.getId());
                Intent intent = new Intent(context, ItemActivity.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("name", item.getPos());
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context, itemHolder.header, ViewCompat.getTransitionName(itemHolder.header)).toBundle());
            });
//        Log.d("item", "onBindViewHolder: "+items.valueAt(position)+" "+items.get(position-1));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView header;
        TextView name;
        TextView gold;
        ItemHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.item);
            name = itemView.findViewById(R.id.item_name);
            gold = itemView.findViewById(R.id.gold_item);
        }
    }
}
