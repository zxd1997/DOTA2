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
    final int HEADER = -1;
    final int PLAYER = -2;
    final int LEVEL = -3;
    List<Integer> abilities;
    Match match;
    Context context;

    public AbilityBuildAdapter(Context context, Match match) {
        this.context = context;
        this.match = match;
        this.abilities = new ArrayList<>();
        abilities.add(PLAYER);
        for (int i = 0; i < 25; i++) {
            abilities.add(LEVEL);
        }
        for (Match.PPlayer p : match.getPlayers()) {
            abilities.add(-1);
            if (p.getAbility_upgrades_arr().size() < 17) {
                for (int i : p.getAbility_upgrades_arr()) {
                    abilities.add(i);
                }
                for (int i = 0; i < 25 - p.getAbility_upgrades_arr().size(); i++) {
                    abilities.add(0);
                }
            } else if (p.getAbility_upgrades_arr().size() < 18) {
                for (int i = 0; i < p.getAbility_upgrades_arr().size() - 1; i++) {
                    abilities.add(p.getAbility_upgrades_arr().get(i));
                }
                abilities.add(0);
                abilities.add(p.getAbility_upgrades_arr().get(p.getAbility_upgrades_arr().size() - 1));
                for (int i = 0; i < 25 - 18; i++) {
                    abilities.add(0);
                }
            } else if (p.getAbility_upgrades_arr().size() < 19) {
                for (int i = 0; i < p.getAbility_upgrades_arr().size() - 2; i++) {
                    abilities.add(p.getAbility_upgrades_arr().get(i));
                }
                abilities.add(0);
                abilities.add(p.getAbility_upgrades_arr().get(p.getAbility_upgrades_arr().size() - 2));
                abilities.add(0);
                abilities.add(p.getAbility_upgrades_arr().get(p.getAbility_upgrades_arr().size() - 1));
                for (int i = 0; i < 25 - 20; i++) {
                    abilities.add(0);
                }
            } else if (p.getAbility_upgrades_arr().size() == 19) {
                for (int i = 0; i < p.getAbility_upgrades_arr().size() - 3; i++) {
                    abilities.add(p.getAbility_upgrades_arr().get(i));
                }
                abilities.add(0);
                abilities.add(p.getAbility_upgrades_arr().get(p.getAbility_upgrades_arr().size() - 3));
                abilities.add(0);
                abilities.add(p.getAbility_upgrades_arr().get(p.getAbility_upgrades_arr().size() - 2));
                for (int i = 0; i < 24 - 20; i++) {
                    abilities.add(0);
                }
                abilities.add(p.getAbility_upgrades_arr().get(p.getAbility_upgrades_arr().size() - 1));
            } else if (p.getAbility_upgrades_arr().size() < 25) {
                for (int i : p.getAbility_upgrades_arr()) {
                    abilities.add(i);
                }
                for (int i = 0; i < 25 - p.getAbility_upgrades_arr().size(); i++) {
                    abilities.add(0);
                }
            } else for (int i : p.getAbility_upgrades_arr()) {
                abilities.add(i);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (abilities.get(position) >= 0) {
            return ABILITY;
        } else return abilities.get(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ABILITY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ability_build, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        } else if (viewType == HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ability_header, parent, false);
            HeaderHolder viewHolder = new HeaderHolder(view);
            return viewHolder;
        } else if (viewType == PLAYER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ability_player, parent, false);
            PlayerHolder viewHolder = new PlayerHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ablity_level, parent, false);
            LevelHolder viewHolder = new LevelHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (abilities.get(position) == -1) {
            Match.PPlayer p = match.getPlayers().get((position / 26) - 1);
            HeaderHolder viewHolder = (HeaderHolder) holder;
            viewHolder.header.setImageResource(context.getResources().getIdentifier("hero_" + p.getHero_id(), "drawable", context.getPackageName()));
            viewHolder.name.setText(p.getPersonaname() == null ? "Anonymous" : p.getPersonaname());
            viewHolder.color.setBackgroundColor(context.getResources().getColor(context.getResources().getIdentifier("slot_" + p.getPlayer_slot(), "color", context.getPackageName())));
        } else if (abilities.get(position) == -2) {
        } else if ((abilities.get(position) == -3)) {
            LevelHolder viewHolder = (LevelHolder) holder;
            viewHolder.level.setText(position + "");
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
        View color;

        public HeaderHolder(View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.color_skill);
            header = itemView.findViewById(R.id.hheader);
            name = itemView.findViewById(R.id.playername);
        }
    }

    class PlayerHolder extends RecyclerView.ViewHolder {
        public PlayerHolder(View itemView) {
            super(itemView);
        }
    }

    class LevelHolder extends RecyclerView.ViewHolder {
        TextView level;

        public LevelHolder(View itemView) {
            super(itemView);
            level = itemView.findViewById(R.id.level);
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
