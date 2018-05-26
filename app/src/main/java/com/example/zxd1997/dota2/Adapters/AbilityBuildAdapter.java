package com.example.zxd1997.dota2.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxd1997.dota2.Activities.MainActivity;
import com.example.zxd1997.dota2.Beans.Match;
import com.example.zxd1997.dota2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class AbilityBuildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int ABILITY = 1;
    final int HEADER = 0;
    List<Integer> abilities;
    Match match;
    Context context;

    public AbilityBuildAdapter(Context context, Match match) {
        this.context = context;
        this.match = match;
        this.abilities = new ArrayList<>();
        for (Match.PPlayer p : match.getPlayers()) {
            abilities.add(-1);
            for (int i : p.getAbility_upgrades_arr()) {
                abilities.add(i);
            }
            if (p.getAbility_upgrades_arr().size() < 19) {
                for (int i = 0; i < 19 - p.getAbility_upgrades_arr().size(); i++) {
                    abilities.add(0);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (abilities.get(position) != -1) {
            return ABILITY;
        } else return HEADER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ABILITY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ability_build, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ability_header, parent, false);
            HeaderHolder viewHolder = new HeaderHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (abilities.get(position) == -1) {
            Match.PPlayer p = match.getPlayers().get(position / 20);
            HeaderHolder viewHolder = (HeaderHolder) holder;
            viewHolder.header.setImageResource(context.getResources().getIdentifier("hero_" + p.getHero_id(), "drawable", context.getPackageName()));
            viewHolder.name.setText(p.getPersonaname() == null ? "Anonymous" : p.getPersonaname());
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            Log.d("ability", "onBindViewHolder: " + abilities.get(position));
            if (abilities.get(position) == 0) {
                viewHolder.icon.setImageResource(context.getResources().getIdentifier("ability_0", "drawable", context.getPackageName()));
            } else {
                int resid = context.getResources().getIdentifier("ability_" + abilities.get(position), "drawable", context.getPackageName());
                if (resid == 0) {
                    resid = context.getResources().getIdentifier("talent_tree", "drawable", context.getPackageName());
                    viewHolder.talent.setText(MainActivity.abilities.get(MainActivity.ability_ids.get(String.valueOf(abilities.get(position)))).getDname());
                }
                viewHolder.icon.setImageResource(resid);
            }
        }
    }

    @Override
    public int getItemCount() {
        Log.d("size", "getItemCount: " + abilities.size());
        return abilities.size();
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView header;
        TextView name;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.hheader);
            name = itemView.findViewById(R.id.playername);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView icon;
        TextView talent;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.ability);
            talent = itemView.findViewById(R.id.talent);
        }
    }
}
