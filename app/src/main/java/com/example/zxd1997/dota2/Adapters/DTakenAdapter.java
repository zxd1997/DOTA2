package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DTakenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Taken> d_taken = new ArrayList<>();
    Context context;

    public DTakenAdapter(Context context, Map<String, Integer> map) {
        this.context = context;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            for (Map.Entry<String, String> entry1 : MainActivity.ability_ids.entrySet()) {
                if (entry1.getValue().equals(entry.getKey())) {
                    d_taken.add(new Taken(entry.getValue(), entry1.getKey()));
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
        viewHolder.icon.setImageResource(context.getResources().getIdentifier("ability_" + d_taken.get(position).id, "drawable", context.getPackageName()));
        DecimalFormat df = new DecimalFormat("0.0");
        viewHolder.damage_taken.setText((double) d_taken.get(position).damage < 1000 ? d_taken.get(position).damage + "" : df.format((double) d_taken.get(position).damage / 1000) + "k");
    }

    @Override
    public int getItemCount() {
        return d_taken.size();
    }

    class Taken {
        int damage;
        String id;

        public Taken(int damage, String id) {
            this.id = id;
            this.damage = damage;
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
