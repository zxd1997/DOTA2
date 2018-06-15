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
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.Beans.Item;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ABILITY = 0;
    private final int ITEM = 1;
    private final int PURCHASE = 2;
    private final int HEADER = -1;
    private final int HERO = 3;
    private final int HERO1 = 7;
    private final int ARROW = 4;
    private final int ENTER = 6;
    private final List<Cast> d_taken = new ArrayList<>();
    private final Context context;

    public CastAdapter(Context context, List<Match.Objective> purchases, Map<String, Integer> map, Map<String, Integer> map1) {
        this.context = context;
        d_taken.add(new Cast(context.getResources().getColor(R.color.win), context.getResources().getString(R.string.item_purchase), HEADER));
        for (Match.Objective purchase : purchases) {
            if (purchase.getKey().equals("tpscroll") || purchase.getKey().equals("ward_observer") || purchase.getKey().equals("ward_sentry"))
                continue;
            Item item = MainActivity.items.get(purchase.getKey());
            d_taken.add(new Cast(purchase.getTime(), item.getId() + "", PURCHASE));
        }
        d_taken.add(new Cast(context.getResources().getColor(R.color.lose), context.getResources().getString(R.string.cast), HEADER));
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                if (entry1.getValue().equals(entry.getKey())) {
                    d_taken.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                    break;
                }
            }
        }
        for (Map.Entry<String, Integer> entry : map1.entrySet()) {
            Item item = MainActivity.items.get(entry.getKey());
            d_taken.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
        }

    }

    public CastAdapter(Context context, Map<String, Integer> kills, Map<String, Integer> map, Map<String, Integer> map1) {
        this.context = context;
        if (kills.size() > 0) {
            d_taken.add(new Cast(context.getResources().getColor(R.color.win), context.getResources().getString(R.string.kills), HEADER));
            for (Map.Entry<String, Integer> entry : kills.entrySet()) {
                Hero hero = MainActivity.heroes.get(entry.getKey());
                for (int i = 0; i < entry.getValue(); i++)
                    d_taken.add(new Cast(entry.getValue(), "hero_" + hero.getId() + "_icon", HERO));
            }
        }
        if (map.size() + map1.size() > 0) {
            d_taken.add(new Cast(context.getResources().getColor(R.color.lose), context.getResources().getString(R.string.cast), HEADER));
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                    if (entry1.getValue().equals(entry.getKey())) {
                        d_taken.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                        break;
                    }
                }
            }
            for (Map.Entry<String, Integer> entry : map1.entrySet()) {
                Item item = MainActivity.items.get(entry.getKey());
                d_taken.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
            }
        }
    }

    public CastAdapter(Context context, Match.PPlayer p) {
        this.context = context;
        d_taken.add(new Cast(context.getResources().getColor(R.color.win), context.getResources().getString(R.string.damage_dealt), HEADER));
        for (Map.Entry<String, Integer> entry : p.getDamage_inflictor().entrySet()) {
            if (entry.getKey().equals("null")) {
                d_taken.add(new Cast(entry.getValue(), "default_attack", ABILITY));
                d_taken.add(new Cast(0, "", ARROW));
                int i = 0;
                for (Map.Entry<String, Integer> entry1 : p.getDamage_targets().get(entry.getKey()).entrySet()) {
                    if (i == 8) d_taken.add(new Cast(0, "", ENTER));
                    d_taken.add(new Cast(entry1.getValue(), "hero_" + MainActivity.heroes.get(entry1.getKey()).getId() + "_icon", HERO1));
                    i++;
                }
                d_taken.add(new Cast(0, "", ENTER));
            } else {
                boolean f = false;
                for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                    if (entry1.getValue().equals(entry.getKey())) {
                        d_taken.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                        d_taken.add(new Cast(0, "", ARROW));
                        int i = 0;
                        for (Map.Entry<String, Integer> entry2 : p.getDamage_targets().get(entry.getKey()).entrySet()) {
                            if (i == 8) d_taken.add(new Cast(0, "", ENTER));
                            d_taken.add(new Cast(entry2.getValue(), "hero_" + MainActivity.heroes.get(entry2.getKey()).getId() + "_icon", HERO1));
                            i++;
                        }
                        d_taken.add(new Cast(0, "", ENTER));
                        f = true;
                        break;
                    }
                }
                if (!f) {
                    Item item = MainActivity.items.get(entry.getKey());
                    d_taken.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
                    d_taken.add(new Cast(0, "", ARROW));
                    int i = 0;
                    for (Map.Entry<String, Integer> entry1 : p.getDamage_targets().get(entry.getKey()).entrySet()) {
                        if (i == 8) d_taken.add(new Cast(0, "", ENTER));
                        d_taken.add(new Cast(entry1.getValue(), "hero_" + MainActivity.heroes.get(entry1.getKey()).getId() + "_icon", HERO1));
                        i++;
                    }
                    d_taken.add(new Cast(0, "", ENTER));
                }
            }
        }
        d_taken.add(new Cast(context.getResources().getColor(R.color.lose), context.getResources().getString(R.string.damage_taken), HEADER));
        for (Map.Entry<String, Integer> entry : p.getDamage_inflictor_received().entrySet()) {
            if (entry.getKey().equals("null")) {
                d_taken.add(new Cast(entry.getValue(), "default_attack", ABILITY));
            } else {
                boolean f = false;
                for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                    if (entry1.getValue().equals(entry.getKey())) {
                        d_taken.add(new Cast(entry.getValue(), entry1.getKey(), ABILITY));
                        f = true;
                        break;
                    }
                }
                if (!f) {
                    Item item = MainActivity.items.get(entry.getKey());
                    d_taken.add(new Cast(entry.getValue(), item.getId() + "", ITEM));
                }
            }
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.d_taken_list, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return d_taken.get(position).type;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HEADER) {
            Header header = (Header) holder;
            header.header_text.setTextColor(d_taken.get(position).time);
            header.header_text.setText(d_taken.get(position).id);
        } else if (getItemViewType(position) == ARROW || getItemViewType(position) == ENTER) {
        } else {
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
                else if (getItemViewType(position) == HERO) {
                    viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                            context.getResources().getIdentifier(d_taken.get(position).id, "drawable", context.getPackageName())))
                            .build());
                    viewHolder.damage_taken.setVisibility(View.GONE);
                } else if (getItemViewType(position) == HERO1) {
                    viewHolder.icon.setImageURI(new Uri.Builder().scheme("res").path(String.valueOf(
                            context.getResources().getIdentifier(d_taken.get(position).id, "drawable", context.getPackageName())))
                            .build());
                }
                viewHolder.damage_taken.setText(String.valueOf(d_taken.get(position).time));
            }
        }
    }

    @Override
    public int getItemCount() {
        return d_taken.size();
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
        public Holder(View itemView) {
            super(itemView);
        }
    }

    class Header extends RecyclerView.ViewHolder {
        TextView header_text;
        TextView header_info;
        View view;

        public Header(View itemView) {
            super(itemView);
            view = itemView;
            header_text = itemView.findViewById(R.id.header_text);
            header_info = itemView.findViewById(R.id.header_info);
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
