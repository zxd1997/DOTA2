package com.example.zxd1997.dota2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.Item;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ABILITY = 0;
    private final int ITEM = 1;
    private final int PURCHASE = 2;
    private final int HEADER = -1;
    private final int HERO = 3;
    private final int HERO1 = 7;
    private final int ARROW = 4;
    private final int ENTER = 6;
    private final int BUFF = 8;
    private final List<Cast> contents = new ArrayList<>();
    private final Context context;

    public CastAdapter(Context context, List<Match.Objective> purchases, Map<String, Integer> map, Map<String, Integer> map1) {
        this.context = context;
        contents.add(new Cast(context.getResources().getColor(R.color.win), context.getResources().getString(R.string.item_purchase), HEADER));
        for (Match.Objective purchase : purchases) {
            if (purchase.getKey().equals("tpscroll") || purchase.getKey().equals("ward_observer") || purchase.getKey().equals("ward_sentry"))
                continue;
            Item item = MainActivity.items.get(purchase.getKey());
            contents.add(new Cast(purchase.getTime(), item.getId() + "", PURCHASE));
        }
        contents.add(new Cast(context.getResources().getColor(R.color.lose), context.getResources().getString(R.string.cast), HEADER));
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                if (entry1.getValue().equals(entry.getKey())) {
                    contents.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                    break;
                }
            }
        }
        for (Map.Entry<String, Integer> entry : map1.entrySet()) {
            Item item = MainActivity.items.get(entry.getKey());
            contents.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
        }

    }

    public CastAdapter(Context context, Map<String, Integer> kills, Map<String, Integer> map, Map<String, Integer> map1) {
        this.context = context;
        if (kills.size() > 0) {
            contents.add(new Cast(context.getResources().getColor(R.color.win), context.getResources().getString(R.string.kills), HEADER));
            for (Map.Entry<String, Integer> entry : kills.entrySet()) {
                Hero hero = MainActivity.heroes.get(entry.getKey());
                for (int i = 0; i < entry.getValue(); i++)
                    contents.add(new Cast(entry.getValue(), "hero_" + hero.getId() + "_icon", HERO));
            }
        }
        if (map.size() + map1.size() > 0) {
            contents.add(new Cast(context.getResources().getColor(R.color.lose), context.getResources().getString(R.string.cast), HEADER));
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                    if (entry1.getValue().equals(entry.getKey())) {
                        contents.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                        break;
                    }
                }
            }
            for (Map.Entry<String, Integer> entry : map1.entrySet()) {
                Item item = MainActivity.items.get(entry.getKey());
                contents.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
            }
        }
    }

    public CastAdapter(Context context, Match.PPlayer p) {
        this.context = context;
        contents.add(new Cast(context.getResources().getColor(R.color.win), context.getResources().getString(R.string.damage_dealt), HEADER));
        for (Map.Entry<String, Integer> entry : p.getDamage_inflictor().entrySet()) {
            if (entry.getKey().equals("null")) {
                contents.add(new Cast(entry.getValue(), "default_attack", ABILITY));
                contents.add(new Cast(0, "", ARROW));
                int i = 0;
                for (Map.Entry<String, Integer> entry1 : p.getDamage_targets().get(entry.getKey()).entrySet()) {
                    if (i == 8) contents.add(new Cast(0, "", ENTER));
                    contents.add(new Cast(entry1.getValue(), "hero_" + MainActivity.heroes.get(entry1.getKey()).getId() + "_icon", HERO1));
                    i++;
                }
                contents.add(new Cast(0, "", ENTER));
            } else {
                boolean f = false;
                for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                    if (entry1.getValue().equals(entry.getKey())) {
                        contents.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                        contents.add(new Cast(0, "", ARROW));
                        int i = 0;
                        for (Map.Entry<String, Integer> entry2 : p.getDamage_targets().get(entry.getKey()).entrySet()) {
                            if (i == 8) contents.add(new Cast(0, "", ENTER));
                            contents.add(new Cast(entry2.getValue(), "hero_" + MainActivity.heroes.get(entry2.getKey()).getId() + "_icon", HERO1));
                            i++;
                        }
                        contents.add(new Cast(0, "", ENTER));
                        f = true;
                        break;
                    }
                }
                if (!f) {
                    Item item = MainActivity.items.get(entry.getKey());
                    contents.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
                    contents.add(new Cast(0, "", ARROW));
                    int i = 0;
                    for (Map.Entry<String, Integer> entry1 : p.getDamage_targets().get(entry.getKey()).entrySet()) {
                        if (i == 8) contents.add(new Cast(0, "", ENTER));
                        contents.add(new Cast(entry1.getValue(), "hero_" + MainActivity.heroes.get(entry1.getKey()).getId() + "_icon", HERO1));
                        i++;
                    }
                    contents.add(new Cast(0, "", ENTER));
                }
            }
        }
        contents.add(new Cast(context.getResources().getColor(R.color.lose), context.getResources().getString(R.string.damage_taken), HEADER));
        for (Map.Entry<String, Integer> entry : p.getDamage_inflictor_received().entrySet()) {
            if (entry.getKey().equals("null")) {
                contents.add(new Cast(entry.getValue(), "default_attack", ABILITY));
            } else {
                boolean f = false;
                for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                    if (entry1.getValue().equals(entry.getKey())) {
                        contents.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                        f = true;
                        break;
                    }
                }
                if (!f) {
                    Item item = MainActivity.items.get(entry.getKey());
                    contents.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
                }
            }
        }
    }

    public CastAdapter(Context context, List<Match.PPlayer.Buff> buffs) {
        this.context = context;
        contents.add(new Cast(Color.BLACK, context.getString(R.string.buffs), HEADER));
        @SuppressLint("Recycle") TypedArray typedArray = Objects.requireNonNull(context).getResources().obtainTypedArray(R.array.buff);
        for (Match.PPlayer.Buff buff : buffs) {
            if (buff.getPermanent_buff() < 6)
                contents.add(new Cast(buff.getStack_count(), String.valueOf(typedArray.getResourceId(buff.getPermanent_buff(), 0)), BUFF));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER)
            return new Header(LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false));
        if (viewType == ARROW)
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.arrow, parent, false));
        if (viewType == ENTER)
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.space, parent, false));
        if (viewType == BUFF)
            return new Buff(LayoutInflater.from(parent.getContext()).inflate(R.layout.buff, parent, false));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.d_taken_list, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return contents.get(position).type;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case HEADER:
                Header header = (Header) holder;
                header.header_text.setTextColor(contents.get(position).time);
                header.header_text.setText(contents.get(position).id);
                break;
            case ARROW:
            case ENTER:
                break;
            case BUFF:
                Buff buff = (Buff) holder;
                buff.buff.setImageURI(new Uri.Builder().scheme("res").path(contents.get(position).id).build());
                buff.count.setText(String.valueOf(contents.get(position).time));
                break;
            default:
                ViewHolder viewHolder = (ViewHolder) holder;
                if (getItemViewType(position) == PURCHASE) {
                    viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                            context.getResources().getIdentifier("item_" + contents.get(position).id, "drawable", context.getPackageName())))
                            .build());
                    int time = Math.abs(contents.get(position).time);
                    int h = time / 3600;
                    int m = time % 3600 / 60;
                    int s = time % 3600 % 60;
                    StringBuilder t = new StringBuilder();
                    if (contents.get(position).time < 0) t.append("-");
                    if (h > 0) {
                        t.append((h < 10) ? "0" + h + ":" : h + ":");
                    }
                    t.append((m < 10) ? "0" + m + ":" : m + ":");
                    t.append((s < 10) ? "0" + s : s);
                    viewHolder.damage_taken.setText(t);
                } else {
                    switch (getItemViewType(position)) {
                        case ABILITY:
                            viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                                    context.getResources().getIdentifier("ability_" + contents.get(position).id, "drawable", context.getPackageName())))
                                    .build());
                            break;
                        case ITEM:
                            viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                                    context.getResources().getIdentifier("item_" + contents.get(position).id, "drawable", context.getPackageName())))
                                    .build());
                            break;
                        case HERO:
                            viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                                    context.getResources().getIdentifier(contents.get(position).id, "drawable", context.getPackageName())))
                                    .build());
                            viewHolder.damage_taken.setVisibility(View.GONE);
                            break;
                        case HERO1:
                            viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                                    context.getResources().getIdentifier(contents.get(position).id, "drawable", context.getPackageName())))
                                    .build());
                            break;
                    }
                    viewHolder.damage_taken.setText(String.valueOf(contents.get(position).time));
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    class Cast {
        final int time;
        final String id;
        final int type;

        Cast(int time, String id, int type) {
            this.type = type;
            this.id = id;
            this.time = time;
        }
    }

    class Holder extends RecyclerView.ViewHolder {
        Holder(View itemView) {
            super(itemView);
        }
    }

    class Buff extends RecyclerView.ViewHolder {
        final SimpleDraweeView buff;
        final TextView count;

        Buff(View itemView) {
            super(itemView);
            buff = itemView.findViewById(R.id.buff);
            count = itemView.findViewById(R.id.buff_count);
        }
    }

    class Header extends RecyclerView.ViewHolder {
        final TextView header_text;

        Header(View itemView) {
            super(itemView);
            header_text = itemView.findViewById(R.id.header_text);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final SimpleDraweeView icon;
        final TextView damage_taken;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.d_skill);
            damage_taken = itemView.findViewById(R.id.d_damage_taken);
        }
    }
}
