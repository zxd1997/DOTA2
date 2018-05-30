package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Beans.Item;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DOutputAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Damage> damage = new ArrayList<>();

    public DOutputAdapter(Context context, Match.PPlayer p) {
        this.context = context;
        for (Map.Entry<String, Integer> entry : p.getDamage_inflictor().entrySet()) {
            Log.d("null", "DOutputAdapter: " + entry.getKey() + " " + entry.getValue() + " " + entry.getKey() == null ? "1" : "0");
            if (entry.getKey().equals("null")) {
                Log.d("null1", "DOutputAdapter: " + entry.getKey() + " " + entry.getValue());
                damage.add(new Damage("default_attack", entry.getValue(), p.getDamage_targets().get("null")));
            } else {
                boolean f = false;
                for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                    if (entry1.getValue().equals(entry.getKey())) {
                        damage.add(new Damage("ability_" + entry1.getKey(), entry.getValue(), p.getDamage_targets().get(entry.getKey())));
                        f = true;
                        break;
                    }
                }
                if (!f) {
                    for (Map.Entry<String, Item> entry1 : MainActivity.items.entrySet()) {
                        if (entry1.getKey().equals(entry.getKey())) {
                            damage.add(new Damage("item_" + entry1.getValue().getId(), entry.getValue(), p.getDamage_targets().get(entry.getKey())));
                            break;
                        }
                    }
                }
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.d_output_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.d_skill_out.setImageResource(context.getResources().getIdentifier(damage.get(position).id, "drawable", context.getPackageName()));
        DecimalFormat df = new DecimalFormat("0.0");
        viewHolder.d_damage_out.setText(damage.get(position).damage_out < 1000 ? damage.get(position).damage_out + "" : df.format((double) damage.get(position).damage_out / 1000) + "k");
        viewHolder.d_out_target.setLayoutManager(new GridLayoutManager(context, 8));
        viewHolder.d_out_target.setAdapter(new DTargetAdapter(context, damage.get(position).target));
    }

    @Override
    public int getItemCount() {
        return damage.size();
    }

    class Damage {
        String id;
        int damage_out;
        Map<String, Integer> target;

        public Damage(String id, int damage_out, Map<String, Integer> target) {
            this.id = id;
            this.damage_out = damage_out;
            this.target = target;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView d_skill_out;
        TextView d_damage_out;
        RecyclerView d_out_target;

        public ViewHolder(View itemView) {
            super(itemView);
            d_skill_out = itemView.findViewById(R.id.d_skill_out);
            d_damage_out = itemView.findViewById(R.id.d_damage_out);
            d_out_target = itemView.findViewById(R.id.d_out_target);
            d_out_target.setNestedScrollingEnabled(false);
        }
    }
}
