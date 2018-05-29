package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Beans.Hero;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DTargetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Target> targets = new ArrayList<>();
    Context context;

    public DTargetAdapter(Context context, Map<String, Integer> map) {
        this.context = context;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            for (Map.Entry<String, Hero> entry1 : MainActivity.heroes.entrySet()) {
                if (entry1.getKey().equals(entry.getKey())) {
                    targets.add(new Target(entry1.getValue().getId(), entry.getValue()));
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DecimalFormat df = new DecimalFormat("0.0");
        viewHolder.damage.setText(targets.get(position).damage < 1000 ? targets.get(position).damage + "" : df.format((double) targets.get(position).damage / 1000) + "k");
        viewHolder.icon.setImageResource(context.getResources().getIdentifier("hero_" + targets.get(position).id + "_icon", "drawable", context.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return targets.size();
    }

    class Target {
        int id;
        int damage;

        public Target(int id, int damage) {
            this.id = id;
            this.damage = damage;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView icon;
        TextView damage;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.d_skill);
            damage = itemView.findViewById(R.id.d_damage_taken);
        }
    }
}
