package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Beans.Item;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int ABILITY = 0;
    final int ITEM = 1;
    final int PURCHASE = 2;
    List<Cast> d_taken = new ArrayList<>();
    Context context;

    public CastAdapter(Context context, List<Match.PPlayer.Purchase> purchases) {
        this.context = context;
        for (Match.PPlayer.Purchase purchase : purchases) {
            for (Map.Entry<String, Item> entry1 : MainActivity.items.entrySet()) {
                if (purchase.getKey().equals("tpscroll") || purchase.getKey().equals("ward_observer") || purchase.getKey().equals("ward_sentry"))
                    continue;
                if (entry1.getKey().equals(purchase.getKey())) {
                    d_taken.add(new Cast(purchase.getTime(), entry1.getValue().getId() + "", PURCHASE));
                    break;
                }
            }
        }
    }

    public CastAdapter(Context context, Map<String, Integer> map, Map<String, Integer> map1) {
        this.context = context;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                if (entry1.getValue().equals(entry.getKey())) {
                    d_taken.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                    break;
                }
            }
        }
        for (Map.Entry<String, Integer> entry : map1.entrySet()) {
            for (Map.Entry<String, Item> entry1 : MainActivity.items.entrySet()) {
                if (entry1.getKey().equals(entry.getKey())) {
                    d_taken.add(new Cast(entry.getValue(), entry1.getValue().getId() + "", ITEM));
                    break;
                }
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.d_taken_list, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return d_taken.get(position).type;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (getItemViewType(position) == PURCHASE) {
            viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                    context.getResources().getIdentifier("item_" + d_taken.get(position).id, "drawable", context.getPackageName())))
                    .build());
            int time = Math.abs(d_taken.get(position).time);
            int h = time / 3600;
            int m = time % 3600 / 60;
            int s = time % 3600 % 60;
            StringBuilder t = new StringBuilder();
            if (d_taken.get(position).time < 0) t.append("-");
            if (h > 0) {
                t.append((h < 10) ? "0" + h + ":" : h + ":");
            }
            t.append((m < 10) ? "0" + m + ":" : m + ":");
            t.append((s < 10) ? "0" + s : s);
            viewHolder.damage_taken.setText(t);
        } else {
            if (getItemViewType(position) == ABILITY)
                viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                        context.getResources().getIdentifier("ability_" + d_taken.get(position).id, "drawable", context.getPackageName())))
                        .build());
            else if (getItemViewType(position) == ITEM)
                viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                        context.getResources().getIdentifier("item_" + d_taken.get(position).id, "drawable", context.getPackageName())))
                        .build());
            viewHolder.damage_taken.setText(d_taken.get(position).time + "");
        }
    }

    @Override
    public int getItemCount() {
        return d_taken.size();
    }

    class Cast {
        int time;
        String id;
        int type;

        public Cast(int time, String id, int type) {
            this.type = type;
            this.id = id;
            this.time = time;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView icon;
        TextView damage_taken;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.d_skill);
            damage_taken = itemView.findViewById(R.id.d_damage_taken);
        }
    }
}
